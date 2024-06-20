package com.example.FinSync.entity;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class LoanDetails {
    @NotNull(message = "Account number cannot be empty")
    @Pattern(regexp = "^[0-9]+$", message = "Account number must contain only numbers")
    private String loanAccountNumber;

    @NotNull(message = "Loan type(personal/agriculture/home ....etc) canot be empty")
    @Pattern(regexp = "^(personal|home|vehicle|agriculture|Personal|Home|Vehicle|Agriculture)$", message = "Loan type must be either personal/home/vehicle/agriculture")
    private String loanType;

    @NotNull(message = "Principal amount cannot be null")
    @Positive(message = "Principle amount must be positive")
    @DecimalMin(value = "0.0", inclusive = false, message = "Principal amount must be greater than zero")
    private double principleAmount;

    @NotNull(message = "Outstanding amount cannot be null")
    @Positive(message = "Outstanding amount must be positive")
    private double outstandingAmount;
}
