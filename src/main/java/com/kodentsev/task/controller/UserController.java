package com.kodentsev.task.controller;

import com.kodentsev.task.domain.service.UserService;
import com.kodentsev.task.domain.dto.FullName;
import com.kodentsev.task.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public User getUser(@RequestParam String userId) {
        return userService.getUserById(userId);
    }

    @GetMapping()
    public List<User> getUsersByName(@RequestBody FullName fullName) {
        return userService.getUsersByFirstNameAndLastName(fullName.getFirstName(), fullName.getLastName());
    }

    @PostMapping()
    public User createUser(@RequestBody FullName fullName) {
        return userService.createUser(fullName);
    }
}
