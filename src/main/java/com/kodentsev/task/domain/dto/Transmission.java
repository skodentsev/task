package com.kodentsev.task.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Transmission {

    private final String accountId;
    private final BigDecimal amount;
}
