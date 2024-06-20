package com.example.FinSync.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class MutualFundDetails {

    @NotNull(message = "Demat Account number cannot be empty")
    private String dematAccountNumber;

    @NotNull(message = "Mutual fund name cannot be empty")
    private String mfName;

    @NotNull(message = "MF units cannot be empty")
    //@Pattern(regexp = "^[0-9]+$", message = "Units number must contain only numbers")
    @Positive(message = "Units number must contain only numbers")
    private double units;

    @NotNull(message = "NAV can not be empty")
    //@Pattern(regexp = "^[0-9]+$", message = "NAV must contain only numbers")
    @Positive(message = "Nav number must contain only numbers")
    private double avgNav;
}
