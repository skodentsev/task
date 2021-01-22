package com.kodentsev.task.domain.service;

import com.kodentsev.task.domain.exception.NotFoundException;
import com.kodentsev.task.domain.dto.FullName;
import com.kodentsev.task.domain.entity.User;
import com.kodentsev.task.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

//    private final UserRepository userRepository;
    private final List<User> users = Arrays.asList(
            User.builder().id("1").firstName("Aaa").lastName("Qqq").build(),
            User.builder().id("2").firstName("Bbb").lastName("Ggg").build(),
            User.builder().id("3").firstName("Ccc").lastName("Ddd").build(),
            User.builder().id("4").firstName("Ddd").lastName("Eee").build()
            );

    public User getUserById(String id) {
//        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User #" + id + " is not exist."));

        User user = users.get(Integer.parseInt(id));
        if (user == null) {
            throw new NotFoundException("User #" + id + " is not exist.");
        }
        return user;
    }

    public List<User> getUsersByFirstNameAndLastName(String firstName, String lastName) {
//        return userRepository.getUsersByFirstNameAndLastName(firstName, lastName);

        return users;
    }

    public User createUser(FullName fullName) {
        User user = User.builder()
                .firstName(fullName.getFirstName())
                .lastName(fullName.getLastName())
                .build();

//        return userRepository.save(user);

        users.add(user);

        return user;
    }

    public void deleteUser(String userId) {
//        userRepository.deleteById(userId);
    }
}
