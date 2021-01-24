package com.kodentsev.task.controller;

import com.kodentsev.task.domain.dto.Transmission;
import com.kodentsev.task.domain.entity.Account;
import com.kodentsev.task.domain.entity.Operation;
import com.kodentsev.task.domain.exception.NoContentException;
import com.kodentsev.task.domain.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "/account", tags = "Accounts")
@RequestMapping(value = "/account", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{accountId}")
    @ApiOperation(value = "Get account by id")
    public Account getAccount(@PathVariable String accountId) {
        return accountService.findAccountById(accountId).orElseThrow(NoContentException::new);
    }

    @GetMapping("/user/{userId}")
    @ApiOperation(value = "Get user's accounts")
    public List<Account> getAccountsByUserId(@PathVariable String userId) {
        return accountService.getAccountsByUserId(userId);
    }

    @DeleteMapping("/{accountId}")
    @ApiOperation(value = "Delete account")
    public ResponseEntity deleteAccount(@PathVariable String accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/deposit")
    @ApiOperation(value = "Deposit")
    public Account depositBigDecimalToAccount(@RequestBody Transmission transmission) {
        return accountService.deposit(transmission);
    }

    @PatchMapping("/withdraw")
    @ApiOperation(value = "Withdraw")
    public Account withdrawBigDecimalFromAccount(@RequestBody Transmission transmission) {
        return accountService.withdraw(transmission);
    }

    @GetMapping("{accountId}/balance")
    @ApiOperation(value = "Get account balance")
    public String getAccountBalance(@PathVariable String accountId) {
        return accountService.getAccount(accountId).getAmount().toString();
    }

    @GetMapping("{accountId}/operations")
    @ApiOperation(value = "Get account operations")
    public List<Operation> getAccountOperations(@PathVariable String accountId) {
        return accountService.getAccount(accountId).getOperations();
    }

}
