package com.example.FinSync.entity;


import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

@Data
public class UserWealth {

        @Valid
        private List<AccountDetails> accounts;

        @Valid
        private List<DepositDetails> deposits;

        @Valid
        private List<LoanDetails> loans;

        @Valid
        private List<MutualFundDetails> mutualFunds;

        @Valid
        private List<StocksDetails> stocks;
}
