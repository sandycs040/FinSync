package com.example.FinSync.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserWealthResponse {
    private Double availableSavings;
    private double deposits;
    private double LoanDebt;
    private double InvestedStocksAmount;
    private double CurrentStocksAmount;
    private double InvestedMFAmount;
    private double CurrentMFAmount;

}
