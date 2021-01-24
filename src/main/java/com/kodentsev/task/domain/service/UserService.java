package com.kodentsev.task.domain.service;

import com.kodentsev.task.domain.dto.FullName;
import com.kodentsev.task.domain.entity.Account;
import com.kodentsev.task.domain.entity.User;
import com.kodentsev.task.domain.exception.NoContentException;
import com.kodentsev.task.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final  AccountService accountService;

    public User getUser(String id) {
        return userRepository.findById(id).orElseThrow(NoContentException::new);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User createUser(FullName fullName) {
        User user = User.builder()
                .firstName(fullName.getFirstName())
                .lastName(fullName.getLastName())
                .build();
        log.info("User " + user + " is created");

        return userRepository.save(user);
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public Account createAccount(String userId) {
        getUser(userId);
        return accountService.createAccount(userId);
    }

    public String getUserBalance(String userId) {
        getUser(userId);
        return accountService.getAccountsByUserId(userId).stream()
                .map(Account::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .toString();
    }
}
