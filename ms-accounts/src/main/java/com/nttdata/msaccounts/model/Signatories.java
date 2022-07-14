package com.nttdata.msaccounts.model;

import lombok.Data;

@Data
public class Signatories {
    private String id;
    private String firstName;
    private String lastName;
    private String docNumber;
    private String accountId;
}
