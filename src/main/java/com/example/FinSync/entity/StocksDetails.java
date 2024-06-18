package com.example.FinSync.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.MessageInterpolator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
    private String dematAccountNumber;

    @NotNull(message = "Staock Name cannot be empty")
    private String stockName;

    @NotNull(message = "stock quantity cannot be empty")
    private Integer quantity;

    @NotNull(message = "Stock purchased price can not empty")
    @Positive(message = "Deposit amount should be positive number")
    private double stockPurchesdPrice;

    @NotNull(message = "price of stock cannot be empty")
    @Positive(message = "price of stock should be positive number")
    private double stockPrice;

    @NotNull(message = "price of stock cannot be empty")
    @Positive(message = "price of stock should be positive number")
    private double stockSellingPrice;
}
