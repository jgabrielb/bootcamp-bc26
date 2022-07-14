package com.nttdata.msaccounts.model;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Payment {
    private String id;

    private LocalDate paymentDate;
    private BigDecimal paymentAmount;
    private String description;

    private String currency;

    private String accountId;
}
