package com.kodentsev.task.domain.service;

import com.kodentsev.task.domain.dto.Transmission;
import com.kodentsev.task.domain.entity.Account;
import com.kodentsev.task.domain.entity.Operation;
import com.kodentsev.task.domain.entity.OperationType;
import com.kodentsev.task.domain.exception.BadRequestException;
import com.kodentsev.task.domain.exception.ConcurrentAccessException;
import com.kodentsev.task.domain.repository.AccountRepository;
import com.kodentsev.task.infrastructure.properties.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final ApplicationProperties properties;

    public Optional<Account> findAccountById(String accountId) {
        return findAccountById(accountId);
    }

    public Account getAccount(String accountId) {
        return accountRepository.findById(accountId).orElseThrow(() -> new BadRequestException("Account #" + accountId + " is not exist."));
    }

    private Account lockAndGetAccount(String accountId) {
        return accountRepository.findAndLock(accountId);
    }

    public List<Account> getAccountsByUserId(String userId) {
        return accountRepository.findByUserId(userId);
    }

    public Account createAccount(String userId) {
        Account account = Account.builder()
                .userId(userId)
                .amount(BigDecimal.ZERO)
                .operations(new LinkedList<>())
                .build();

        return accountRepository.save(account);
    }

    public void deleteAccount(String accountId) {
        accountRepository.deleteById(accountId);
    }

    public Account deposit(Transmission transmission) {

        validateAmount(transmission.getAmount());
        BigDecimal addend = transmission.getAmount().setScale(2, RoundingMode.DOWN);
        Operation operation = Operation.builder()
                .type(OperationType.DEPOSIT)
                .amount(addend)
                .build();

        String accountId = transmission.getAccountId();

        //make sure an account exists
        getAccount(accountId);

        Account account = lockAndGetAccount(accountId);
        if (account == null) {
            log.info("Account #" + accountId + "is locked by another operation");
            account = tryToAcquireAccountLock(accountId);
        }
        BigDecimal amount = account.getAmount();
        account.setAmount(amount.add(addend));
        account.getOperations().add(operation);
        account.setLocked(false);
        return accountRepository.save(account);
    }

    public Account withdraw(Transmission transmission) {

        validateAmount(transmission.getAmount());
        BigDecimal deduction = transmission.getAmount().setScale(2, RoundingMode.DOWN);
        Operation operation = Operation.builder()
                .type(OperationType.WITHDRAW)
                .amount(deduction)
                .build();

        String accountId = transmission.getAccountId();

        //make sure an account exists
        getAccount(accountId);

        Account account = lockAndGetAccount(accountId);
        if (account == null) {
            account = tryToAcquireAccountLock(accountId);
        }
        if (account.getAmount().compareTo(deduction) < 0) {
            throw new BadRequestException("Insufficient funds. Account #" + accountId);
        }
        BigDecimal amount = account.getAmount();
        account.setAmount(amount.subtract(deduction));
        account.getOperations().add(operation);
        account.setLocked(false);
        return accountRepository.save(account);
    }

    private void validateAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Amount should be more than 0");
        }
    }

    private Account tryToAcquireAccountLock(String accountId) {
        Account account;

        int attempts = Integer.parseInt(properties.getAttempts());
        for (int attempt = 0; attempt < attempts; attempt++) {
            account = lockAndGetAccount(accountId);
            if (account != null) {
                return account;
            }
        }
        log.error("Account #" + accountId + " is still locked after " + attempts + " attempts");
        throw new ConcurrentAccessException("An account #" + accountId + " is unavailable now.");
    }


}
