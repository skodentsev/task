package com.kodentsev.task.domain.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Account {

//    @Id
    private String id;

    private BigDecimal amount;

    private List<Operation> operations;
}
