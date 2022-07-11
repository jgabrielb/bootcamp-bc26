package com.nttdata.mscustomers.model;

import lombok.Data;
import lombok.NonNull;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "schema_persons.customers")
@Data
public class Customer {
    @Id
    @BsonIgnore
    private ObjectId id;
    //@NonNull
    private String firstName;
    //@NonNull
    private String lastName;
    //@NonNull
    private String docNumber;
    //@NonNull
    private String typeCustomer;
    //@BsonIgnore
    private String descTypeCustomer;
}
