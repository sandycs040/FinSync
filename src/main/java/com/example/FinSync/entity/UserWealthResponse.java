package com.example.FinSync.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserWealthResponse {
    private Double availableSavings;
    private double deposits;
    private double loanDebt;
    private double investedStocksAmount;
    private double currentStocksAmount;
    private double stockTotalGain;
    private double investedMFAmount;
    private double currentMFAmount;
    private double totalGain;

}
