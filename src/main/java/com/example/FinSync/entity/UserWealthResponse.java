package com.example.FinSync.entity;

import com.example.FinSync.validations.CurrencyInrSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserWealthResponse {
    @JsonSerialize(using = CurrencyInrSerializer.class)
    private Double availableSavings;

    @JsonSerialize(using = CurrencyInrSerializer.class)
    private double deposits;

    @JsonSerialize(using = CurrencyInrSerializer.class)
    private double loanDebt;

    @JsonSerialize(using = CurrencyInrSerializer.class)
    private double investedStocksAmount;

    @JsonSerialize(using = CurrencyInrSerializer.class)
    private double currentStocksAmount;

    @JsonSerialize(using = CurrencyInrSerializer.class)
    private double stockTotalGain;

    @JsonSerialize(using = CurrencyInrSerializer.class)
    private double investedMFAmount;

    @JsonSerialize(using = CurrencyInrSerializer.class)
    private double currentMFAmount;

    @JsonSerialize(using = CurrencyInrSerializer.class)
    private double totalGain;

}
