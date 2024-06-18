package com.example.FinSync.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class MutualFundDeatils {

    @NotNull(message = "Demat Account number cannot be empty")
    private String dematAccountNumber;

    @NotNull(message = "Mutual fund name cannot be empty")
    private String mfName;

    @NotNull(message = "MF units cannot be empty")
    @Pattern(regexp = "^[0-9]+$", message = "Units number must contain only numbers")
    private double units;

    @NotNull(message = "NAV can not be empty")
    @Pattern(regexp = "^[0-9]+$", message = "NAV must contain only numbers")
    private double nav;

    @NotNull(message = "Total investment cannot be empty")
    private double totalInvestments;
}
