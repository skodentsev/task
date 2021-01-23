package com.kodentsev.task.controller;

import com.kodentsev.task.domain.service.UserService;
import com.kodentsev.task.domain.dto.FullName;
import com.kodentsev.task.domain.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@Api(value = "/user", tags = "Users")
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final ApplicationContext applicationContext;

    @GetMapping("/{userId}")
    @ApiOperation(value = "Get user by id", tags = "Users")
    public User getUser(@PathVariable String userId) {
        System.out.println("---getUser---");

        Arrays.stream(applicationContext.getBeanDefinitionNames()).forEach(System.out::println);

        return userService.getUserById(userId);
    }

    @GetMapping("/full-name")
    public List<User> getUsersByName(@RequestBody FullName fullName) {
        return userService.getUsersByFirstNameAndLastName(fullName.getFirstName(), fullName.getLastName());
    }

    @PostMapping()
    public User createUser(@RequestBody FullName fullName) {
        return userService.createUser(fullName);
    }
}
