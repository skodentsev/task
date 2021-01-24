package com.kodentsev.task.domain.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class Account {

    @Id
    private String id;
    private String userId;
    private BigDecimal amount;
    private List<Operation> operations;
    @Builder.Default
    private Boolean locked = false;
}
