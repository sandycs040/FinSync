package com.example.FinSync.utils;

import com.example.FinSync.entity.*;

import java.util.ArrayList;
import java.util.List;

public class ModelMapper {

    public static List<AccountDetails> mapEntityToAccountDto(List<Account> account) {
        List<AccountDetails> accountsList = new ArrayList<>();
        for(Account acc : account){
            AccountDetails accountDto = new AccountDetails();
            accountDto.setAccountNumber(acc.getAccountNumber());
            accountDto.setBalance(acc.getBalance());
            accountDto.setBranch(acc.getBranch());
            accountsList.add(accountDto);
        }
        return accountsList;
    }

    public static List<StocksDetails> mapEntityToStockDto(List<Stocks> stocks) {
        List<StocksDetails> stockList = new ArrayList<>();
        for(Stocks s : stocks){
            StocksDetails stockDto = new StocksDetails();
            stockDto.setDematAccountNumber(s.getDematAccountNumber());
            stockDto.setStockName(s.getStockName());
            stockDto.setQuantity(s.getQuantity());
            stockDto.setStockPurchesdPrice(s.getStockPurchesdPrice());
            stockList.add(stockDto);
        }
        return stockList;
    }

    public static List<MutualFundDetails> mapEntityToMutualFundDto(List<MutualFunds> mutualFunds) {
        List<MutualFundDetails> mutualFundList = new ArrayList<>();
        for(MutualFunds mf : mutualFunds){
            MutualFundDetails mfDto =new MutualFundDetails();
            mfDto.setDematAccountNumber(mf.getDematAccountNumber());
            mfDto.setMfName(mf.getMfName());
            mfDto.setUnits(mf.getUnits());
            mfDto.setAvgNav(mf.getAvgNav());
            mutualFundList.add(mfDto);
        }
        return mutualFundList;
    }

    public static UserWealth mapEntityToWealthDto(User user) {
        UserWealth userWealth = new UserWealth();
        userWealth.setAccounts(ModelMapper.mapEntityToAccountDto(user.getAccount()));
        userWealth.setDeposits(mapDepositEntityListToDtoList(user.getDeposits()));
        userWealth.setLoans(mapLoanEntityListToDtoList(user.getLoans()));
        userWealth.setMutualFunds(mapEntityToMutualFundDto(user.getMutualFunds()));
        userWealth.setStocks(ModelMapper.mapEntityToStockDto(user.getStocks()));
        return  userWealth;
    }

    public static List<LoanDetails> mapLoanEntityListToDtoList(List<Loan> loans) {
        List<LoanDetails> loanList = new ArrayList<>();
        for(Loan l : loans){
            LoanDetails loanDto = new LoanDetails();
            loanDto.setLoanAccountNumber(l.getLoanAccountNumber());
            loanDto.setLoanType(l.getLoanType());
            loanDto.setPrincipleAmount(l.getPrincipleAmount());
            loanDto.setOutstandingAmount(l.getOutstandingAmount());
            loanList.add(loanDto);
        }
        return loanList;
    }

    public static List<DepositDetails> mapDepositEntityListToDtoList(List<Deposit> deposits) {
        List<DepositDetails> depositList = new ArrayList<>();
        for(Deposit dep : deposits){
            DepositDetails depositDto = new DepositDetails();
            depositDto.setDepositAccountNumber(dep.getDepositAccountNumber());
            depositDto.setAmount(dep.getAmount());
            depositDto.setDepositType(dep.getDepositType());
            depositList.add(depositDto);
        }
        return depositList;
    }

}
