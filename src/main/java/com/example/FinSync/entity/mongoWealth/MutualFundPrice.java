package com.example.FinSync.entity.mongoWealth;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "mutualFundPrices")
@TypeAlias("MutualFund")
public class MutualFundPrice {
    @Id
    private String id;

    private final String name;
    private final Double nav;
    private final String navDate;

    private final Date created;

    public MutualFundPrice(String name, Double nav,String navDate,Date created) {
        this.name = name;
        this.nav = nav;
        this.navDate=navDate;
        this.created=created;
    }

    public String getName() {
        return name;
    }

    public Double getNav() { return nav; }
}
