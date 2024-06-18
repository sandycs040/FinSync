package com.example.FinSync.entity;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UserWealth {

        @NotNull(message = "User Id cannot be empty")
        private Long userId;

        @NotEmpty(message = "Mode  new/modify values , mode cannot be empty" )
        private String mode;

        @Valid
        private List<AccountDetails> accounts;

        @Valid
        private List<DepositDetails> deposits;

        @Valid
        private List<LoanDeatils> loans;

        @Valid
        private List<MutualFundDeatils> mutualFunds;

        @Valid
        private List<StocksDetails> stocks;
}
