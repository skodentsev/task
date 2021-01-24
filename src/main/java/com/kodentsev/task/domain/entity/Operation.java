package com.kodentsev.task.domain.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Data
public class Operation {

    @Id
    @Builder.Default
    private final String id = UUID.randomUUID().toString();

    private final OperationType type;
    private final BigDecimal amount;
}
