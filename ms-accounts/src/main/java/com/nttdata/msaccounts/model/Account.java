package com.nttdata.msaccounts.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@Document(collection = "schema_suscribe.accounts")
public class Account {
    @Id
    private String id;

    private String customerId;

    private String productId;

    private String accountNumber;

    private String accountNumberInt;

    private int movementLimits;

    private int movementActually;

    private BigDecimal creditLimits;

    private BigDecimal creditActually;

    private BigDecimal commission;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate movementDate;

}
