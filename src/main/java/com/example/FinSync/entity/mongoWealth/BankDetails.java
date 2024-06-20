package com.example.FinSync.entity.mongoWealth;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "bankNames")
public class BankDetails {
    @Id
    private String id;

    private final String shortName;
    private final String fullName;


    public BankDetails( String shortName,
                        String fullName) {
        this.shortName = shortName;
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public String getFullName() {
        return fullName;
    }
}
