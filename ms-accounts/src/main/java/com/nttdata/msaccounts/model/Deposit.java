package com.nttdata.msaccounts.model;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Deposit {
    private String id;

    private LocalDate depositDate;

    private BigDecimal depositAmount;

    private String currency;

    private String accountId;
}
