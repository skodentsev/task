package com.kodentsev.task.domain.repository;

import com.kodentsev.task.domain.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

//    User getUserById(String id);
    List<User> getUsersByFirstNameAndLastName(String firstName, String lastName);

}
