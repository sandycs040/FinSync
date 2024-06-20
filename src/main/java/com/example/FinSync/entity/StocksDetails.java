package com.example.FinSync.entity;

import com.example.FinSync.validations.ValidStockName;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.MessageInterpolator;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDateTime;
import java.util.Stack;
import java.util.UUID;

@Data
public class StocksDetails {
    @NotNull(message = "Demat account number cannot be empty")
    @NotEmpty(message = "Account number cannot be empty")
    @Pattern(regexp = "^[0-9]+$", message = "Account number must contain only numbers")
    private String dematAccountNumber;

    @NotNull(message = "Staock Name cannot be null")
    @NotEmpty(message = "stock name cannot empty")
    @ValidStockName
    private String stockName;

    @NotNull(message = "stock quantity cannot be empty")
    private Integer quantity;

    @NotNull(message = "Stock purchased price can not empty")
    @Positive(message = "Deposit amount should be positive number")
    private double stockPurchesdPrice;
}
