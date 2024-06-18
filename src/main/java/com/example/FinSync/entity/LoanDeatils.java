package com.example.FinSync.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

@Data
public class LoanDeatils {
    @NotNull(message = "Account number cannot be empty")
    @Pattern(regexp = "^[0-9]+$", message = "Account number must contain only numbers")
    private String loanAccountNumber;

    @Size(max = 500, message = "Description must be of 500 characters")
    private String description;

    @NotNull(message = "Loan type(personal/agriculture/home ....etc) canot be empty")
    @Pattern(regexp = "^(personal|home|vehicle|agriculture)$", message = "Loan type must be either personal/home/vehicle/agriculture")
    private String loanType;

    @NotNull(message = "Principle Amount amount cannot be empty")
    @Positive(message = "Principle Amount should be positive number")
    private double principleAmount;

    @NotNull(message = "outstanding amount cannot be empty")
    @Positive(message = "outstanding should be positive number")
    private double outstandingAmount;
}
