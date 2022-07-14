package com.nttdata.msaccounts.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Purchase {
    private String id;

    private LocalDate purchaseDate;

    private BigDecimal purchaseAmount;
    private String description;
    private String currency;
    private String accountId;
}
