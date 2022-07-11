package com.nttdata.mscustomers.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "schema_persons.customers")
@Data
public class Customer {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String docNumber;
    private int typeCustomer;
    private String descTypeCustomer;
}