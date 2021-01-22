package com.kodentsev.task.domain.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@Builder
public class User {

//    @Id
    private String id;

    private String firstName;
    private String lastName;

    private List<Account> accounts;

}
