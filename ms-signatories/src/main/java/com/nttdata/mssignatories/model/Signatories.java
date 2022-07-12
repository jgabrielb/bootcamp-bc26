package com.nttdata.mssignatories.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "schema_suscribe.signatories")
@Data
public class Signatories {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String docNumber;

    private String accountId;
}
