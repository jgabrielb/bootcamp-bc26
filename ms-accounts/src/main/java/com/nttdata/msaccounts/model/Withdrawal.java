package com.nttdata.msaccounts.model;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Withdrawal {
    private String id;

    private LocalDate withdrawalsDate;

    private BigDecimal withdrawalsAmount ;
    private String currency;
    private String accountId;
}
