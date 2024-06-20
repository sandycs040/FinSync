package com.example.FinSync.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jdk.jfr.Description;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class DepositDetails {

    @NotNull(message = "Account number cannot be null")
    @NotEmpty(message = "Account number cannot be empty")
    @Pattern(regexp = "^[0-9]+$", message = "Account number must contain only numbers")
    private String depositAccountNumber;

    @Size(min = 2,max = 2, message = "Deposit Type must be FD/RD")
    @Pattern(regexp = "^(FD|RD)$", message = "Deposit type must be either FD or RD")
    @NotNull(message = "Deposit type(FD/RD) cannot be Null")
    @NotEmpty(message = "Deposit type(FD/RD) cannot be empty")
    private String depositType;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Deposit amount should be positive number")
    private Double amount;

//    @NotNull(message = "Date can not be null")
//    @Future(message = "Event date must be in the future")
//    @DateTimeFormat(pattern = "dd-MM-yyyy")
//    private LocalDate matureDate;
}
