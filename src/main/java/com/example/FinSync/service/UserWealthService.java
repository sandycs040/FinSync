package com.example.FinSync.service;

import com.example.FinSync.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class UserWealthService {

    @Autowired
    AccountRepository accountRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    DepositRepository depositRepo;

    @Autowired
    LoanRepository loanRepo;

    @Autowired
    MutualFundRepository mutualFundRepo;

    @Autowired
    StockRepository stockRepo;

    private static final Logger logger = Logger.getLogger(UserWealthService.class.getName());

    public UserWealthResponse saveWealthData(UserWealth userWealthRequest) {
        Double availableSavings = 0.0, availableDeposits = 0.0, loanDebt = 0.0, investedStocksAmount = 0.0, currentStocksAmount = 0.0, investedMFAmount = 0.0, currentMFAmount = 0.0;
        Map<String, Double> stockGain = new HashMap<>();
        Map<String, Double> mfGain = new HashMap<>();
        try {
           Boolean saveFlag = true;
           availableSavings = saveUserAccounts(userWealthRequest, saveFlag);
           availableDeposits = saveUserDeposits(userWealthRequest, saveFlag);
           loanDebt = saveUserLoan(userWealthRequest, saveFlag);
           mfGain = saveUserMfs(userWealthRequest, saveFlag);
           stockGain = saveUserStocks(userWealthRequest, saveFlag);
       }catch (Exception ex){
           logger.info(" | Exception | Save wealth Service | " +ex.getMessage() + " | ");
       }
        return new UserWealthResponse(availableSavings,availableDeposits,loanDebt,stockGain.get("investedStocksAmount") == null ? 0.0 :stockGain.get("investedStocksAmount"),stockGain.get("currentStocksAmount") == null ? 0.0 : stockGain.get("currentStocksAmount"),mfGain.get("investedMFAmount") == null ? 0.0 : mfGain.get("investedMFAmount"),mfGain.get("currentMFAmount") == null ? 0.0 : mfGain.get("currentMFAmount"));
    }

    private Map<String, Double> saveUserStocks(UserWealth userWealthRequest, Boolean saveFlag) {
        Map<String,Double> stockInvestmentsAmount = new HashMap<>();
        if(saveFlag && userWealthRequest.getStocks() != null) {
            for (StocksDetails stock : userWealthRequest.getStocks()) {
                Stocks stockEntity = mappingStockEntity(stock, userWealthRequest);
                stockRepo.save(stockEntity);
            }
        }
        List<Stocks> stockList = stockRepo.findByUserId(userWealthRequest.getUserId());
        if(stockList == null){
            stockInvestmentsAmount.put("currentStocksAmount",0.0);
            stockInvestmentsAmount.put("investedStocksAmount",0.0);
        }else { stockInvestmentsAmount = getTotalInvestmentAmounts(stockList); }
        return stockInvestmentsAmount;
    }

    private Map<String, Double> getTotalInvestmentAmounts(List<Stocks> stockList) {
        Map<String,Double> stockInvestmentsAmount = new HashMap<>();
        Double currentStocksAmount = 0.0;
        Double investedStocksAmount = 0.0;
        for(Stocks stock : stockList){
            double currentPrice = stock.getStockSellingPrice() == 0.0 ? stock.getStockPrice() : stock.getStockSellingPrice();
            double investedPrice = stock.getStockPurchesdPrice();
            currentStocksAmount = currentStocksAmount +
                    (stock.getQuantity() * currentPrice);
            investedStocksAmount = investedStocksAmount + (stock.getQuantity() * investedPrice);
        }
        stockInvestmentsAmount.put("currentStocksAmount",currentStocksAmount);
        stockInvestmentsAmount.put("investedStocksAmount",investedStocksAmount);
        return stockInvestmentsAmount;
    }

    private Map<String, Double> saveUserMfs(UserWealth userWealthRequest, Boolean saveFlag) {
        if(saveFlag && userWealthRequest.getMutualFunds() != null) {
            for (MutualFundDeatils mf : userWealthRequest.getMutualFunds()) {
                MutualFunds mfEntity = mappingMfEntity(mf, userWealthRequest);
                mutualFundRepo.save(mfEntity);
            }
        }
        List<MutualFunds> mutualFundList = mutualFundRepo.findByUserId(userWealthRequest.getUserId());
        Map<String,Double> mfAmounts = new HashMap<>();
        if(mutualFundList == null){
            mfAmounts.put("currentMFAmount",0.0);
            mfAmounts.put("investedMFAmount",0.0);
        } else{ mfAmounts = getTotalMfInvestments(mutualFundList); }
        return mfAmounts;
    }

    private Map<String, Double> getTotalMfInvestments(List<MutualFunds> mutualFundList) {
        Map<String,Double> mfAmounts = new HashMap<>();
        Double currentMFAmount = 0.0;
        Double investedMFAmount = 0.0;
        for(MutualFunds mf: mutualFundList){
            Double units = mf.getNav() * mf.getUnits();
            currentMFAmount = currentMFAmount + units;
            Double totalInvestment = mf.getTotalInvestments();
            investedMFAmount = investedMFAmount + mf.getTotalInvestments();
        }
        mfAmounts.put("currentMFAmount",currentMFAmount);
        mfAmounts.put("investedMFAmount",investedMFAmount);
        return mfAmounts;
    }

    private Double saveUserLoan(UserWealth userWealthRequest, Boolean saveFlag) {
        Double loanDebt = 0.0;
        if(saveFlag && userWealthRequest.getLoans() != null) {
            for (LoanDeatils loan : userWealthRequest.getLoans()) {
                Loan loanEntity = mappingLoanEntity(loan, userWealthRequest);
                loanRepo.save(loanEntity);
            }
        }
        List<Loan> loanList = loanRepo.findByUserId(userWealthRequest.getUserId());
        if(loanList == null){
            loanDebt = 0.0;
        }else { loanDebt = totalOutStandingAmount(loanList); }
        return loanDebt;
    }

    private Double totalOutStandingAmount(List<Loan> loanList) {
        Double loanDebt = 0.0;
        for(Loan loan : loanList){
            loanDebt = loanDebt + loan.getOutstandingAmount();
        }
        return loanDebt;
    }

    private Double saveUserDeposits(UserWealth userWealthRequest, Boolean saveFlag) {
        Double availableDeposits = 0.0;
        if(saveFlag && userWealthRequest.getDeposits() != null) {
            for (DepositDetails deposit : userWealthRequest.getDeposits()) {
                Deposit depositEntity = mappingDepositEntity(deposit, userWealthRequest);
                depositRepo.save(depositEntity);
            }
        }
        List<Deposit> depositList = depositRepo.findByUserId(userWealthRequest.getUserId());
        if(depositList == null) {
            availableDeposits = 0.0;
        }else { availableDeposits = getTotalDeposits(depositList); }
        return availableDeposits;
    }

    private Double getTotalDeposits(List<Deposit> depositList) {
        Double availableDeposits = 0.0;
        for(Deposit deposit : depositList){
            availableDeposits = availableDeposits + deposit.getAmount();
        }
        return availableDeposits;
    }

    private Double saveUserAccounts(UserWealth userWealthRequest,Boolean saveFlag) {
        Double availableSavings = 0.0;
        if(saveFlag && userWealthRequest.getAccounts() != null) {
            for (AccountDetails account : userWealthRequest.getAccounts()) {
                try {
                    Account accountEntity = mappingAccountEntity(account, userWealthRequest);
                    accountRepo.save(accountEntity);
                } catch (Exception e) {
                    logger.info("Encountering an exception during the saving of account data, Exception Details {}" +e.getMessage());
                    throw new IllegalArgumentException("Verify that the input JSON data does not adhere to the expected JSON format.");
                }
            }
        }
        List<Account> accountsList = accountRepo.findByUserId(userWealthRequest.getUserId());
        if(accountsList == null){
            availableSavings = 0.0;
        }else {  availableSavings = getTotalSavings(accountsList); }
        return availableSavings;
    }

    private Double getTotalSavings(List<Account> accountsList) {
        Double availableSavings =0.0;
        for (Account account : accountsList){
            availableSavings = availableSavings + account.getBalance();
        }
        return availableSavings;
    }

    private Stocks mappingStockEntity(StocksDetails stock, UserWealth userWealth) {
        Stocks stockEntity = new Stocks();
        stockEntity.setStockName(stock.getStockName());
        stockEntity.setQuantity(stock.getQuantity());
        stockEntity.setUser(getUser(userWealth.getUserId()));
        stockEntity.setDematAccountNumber(stock.getDematAccountNumber());
        stockEntity.setStockPurchesdPrice(stock.getStockPurchesdPrice());
        stockEntity.setStockSellingPrice(stock.getStockSellingPrice());
        stockEntity.setStockPrice(stock.getStockPrice());
        return stockEntity;
    }

    private MutualFunds mappingMfEntity(MutualFundDeatils mf, UserWealth userWealth) {
        MutualFunds mfEntity = new MutualFunds();
        mfEntity.setNav(mf.getNav());
        mfEntity.setUser(getUser(userWealth.getUserId()));
        mfEntity.setUnits(mf.getUnits());
        mfEntity.setMfName(mf.getMfName());
        mfEntity.setDematAccountNumber(mf.getDematAccountNumber());
        mfEntity.setTotalInvestments((mf.getTotalInvestments()));
        return mfEntity;
    }

    private Loan mappingLoanEntity(LoanDeatils loan, UserWealth userWealth) {
        Loan loanEntity = new Loan();
        loanEntity.setDescription(loan.getDescription());
        loanEntity.setLoanType(loan.getLoanType());
        loanEntity.setLoanAccountNumber(loan.getLoanAccountNumber());
        loanEntity.setPrincipleAmount(loan.getPrincipleAmount());
        loanEntity.setOutstandingAmount(loan.getOutstandingAmount());
        loanEntity.setUser(getUser(userWealth.getUserId()));
        return loanEntity;
    }

    private Deposit mappingDepositEntity(DepositDetails deposit, UserWealth userWealth) {
        Deposit depositEntity = new Deposit();
        depositEntity.setDepositAccountNumber(deposit.getDepositAccountNumber());
        depositEntity.setDepositType(deposit.getDepositType());
        depositEntity.setAmount(deposit.getAmount());
        depositEntity.setUser(getUser(userWealth.getUserId()));
        return depositEntity;
    }

    private Account mappingAccountEntity(AccountDetails account,UserWealth userWealth) {
        Account accEntity = new Account();
        accEntity.setAccountNumber(account.getAccountNumber());
        accEntity.setBalance(account.getBalance());
        accEntity.setBranch(account.getBranch());
        accEntity.setUser(getUser(userWealth.getUserId()));
        return accEntity;
    }

    public User getUser(Long userId){
        return userRepo.findById(userId).orElse(null);
    }

    public UserWealth getWealthData(Long userId) {
        UserWealth user = new UserWealth();
        return user;
    }

    public UserWealth getUserWealth(Long UserId){
        User user = getUser(UserId);
        UserWealth userWealth = new UserWealth();
        return userWealth;
    }
}
