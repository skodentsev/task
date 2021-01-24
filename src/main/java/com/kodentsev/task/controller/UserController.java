package com.kodentsev.task.controller;

import com.kodentsev.task.domain.dto.FullName;
import com.kodentsev.task.domain.entity.Account;
import com.kodentsev.task.domain.entity.User;
import com.kodentsev.task.domain.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "/user", tags = "Users")
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    @ApiOperation(value = "Get user by id")
    public User getUser(@PathVariable String userId) {
        return userService.getUser(userId);
    }

    @GetMapping()
    @ApiOperation(value = "Get all users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping()
    @ApiOperation(value = "Add user")
    public User addUser(@RequestBody FullName fullName) {
        return userService.createUser(fullName);
    }

    @DeleteMapping("/{userId}")
    @ApiOperation(value = "Delete user")
    public ResponseEntity deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/account")
    @ApiOperation(value = "Add account")
    public Account createAccount(@RequestBody String userId) {
        return userService.createAccount(userId);
    }

    @GetMapping("/{userId}/balance")
    @ApiOperation(value = "Get user's balance")
    public String getBalance(@PathVariable String userId) {
        return userService.getUserBalance(userId);
    }

}
