package com.example.FinSync.service;

import com.example.FinSync.entity.*;
import com.example.FinSync.entity.mongoWealth.BankDetails;
import com.example.FinSync.entity.mongoWealth.MutualFundPrice;
import com.example.FinSync.entity.mongoWealth.StockPrice;
import com.example.FinSync.exception.ResourceNotFoundException;
import com.example.FinSync.exception.ValidationErrorException;
import com.example.FinSync.utils.FinSyncResponseUtils;
import com.example.FinSync.utils.LogConnectPoolStatus;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.*;
import java.util.logging.Logger;

@Service
public class UserWealthService {

    @Autowired
    WealthService wealthService;

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

    @Autowired
    JwtService jwtService;

    private static final Logger logger = Logger.getLogger(UserWealthService.class.getName());

    Double nav = 63.0;
    Double stockPrice = 200.0;
    Long userId = 1L;
    Boolean getWealthTriggerFlag = false;
    String updateFlag = "";

    //save user wealth data
    @Transactional
    public UserWealthResponse saveWealthData(UserWealth userWealthRequest,String token) throws Exception {
        //logConnectioPool.logConnectionPoolSttaus();
        Double availableSavings = 0.0, availableDeposits = 0.0, loanDebt = 0.0, investedStocksAmount = 0.0, currentStocksAmount = 0.0, investedMFAmount = 0.0, currentMFAmount = 0.0;
        Map<String, Double> stockGain = new HashMap<>();
        Map<String, Double> mfGain = new HashMap<>();
        Boolean saveFlag = true;

        //set userId based on Token passd
        User user = setUserIdBasedOnToken(token);
        try {
            saveFlag = (getWealthTriggerFlag != true) ? true : false;
            availableSavings = saveUserAccounts(userWealthRequest, saveFlag);
            availableDeposits = saveUserDeposits(userWealthRequest, saveFlag);
            loanDebt = saveUserLoan(userWealthRequest, saveFlag);
            mfGain = saveUserMfs(userWealthRequest, saveFlag);
            stockGain = saveUserStocks(userWealthRequest, saveFlag);
        }catch (ResourceNotFoundException ex){ throw new ResourceNotFoundException(ex.getMessage());
        }catch (ValidationErrorException ex){ throw new ValidationErrorException(ex.getMessage());
        }catch (Exception ex){
           logger.info(" | Exception | Save wealth Service | " +ex.getMessage() + " | ");
           throw new Exception(ex.getMessage());
       }
        return new UserWealthResponse(availableSavings,availableDeposits,loanDebt,stockGain.get("investedStocksAmount") == null ? 0.0 :stockGain.get("investedStocksAmount"),stockGain.get("currentStocksAmount") == null ? 0.0 : stockGain.get("currentStocksAmount"), stockGain.get("stockTotalGain") == null ? 0.0 :stockGain.get("stockTotalGain"),mfGain.get("investedMFAmount") == null ? 0.0 : mfGain.get("investedMFAmount"),mfGain.get("currentMFAmount") == null ? 0.0 : mfGain.get("currentMFAmount"),mfGain.get("totalGain")== null ? 0.0 : mfGain.get("totalGain"));
    }


    private User setUserIdBasedOnToken(String token) throws ValidationErrorException {
        String userName = jwtService.extractUsername(token);
        User user = userRepo.findByUserName(userName);
        if(user == null){ throw new ResourceNotFoundException("User not found");}
        Boolean isValidToken = jwtService.isTokenValid(token,user);
        if(isValidToken){ userId = user.getId();}else { throw  new ValidationErrorException("Token is not valid ");}return user;
    }

    private Map<String, Double> saveUserStocks(UserWealth userWealthRequest, Boolean saveFlag) {
        Map<String,Double> stockInvestmentsAmount = new HashMap<>();
        if(saveFlag && userWealthRequest.getStocks() != null) {
            for (StocksDetails stock : userWealthRequest.getStocks()) {
                Stocks stockEntity = mappingStockEntity(stock, userWealthRequest);
                if(updateFlag.equalsIgnoreCase("update")){
                    stockEntity = stockRepo.findByDematAccountNumber(stock.getDematAccountNumber());
                    if(stockEntity == null){
                        throw new ResourceNotFoundException("stock demat account number id not found to update");
                    }
                }
                stockRepo.save(stockEntity);
            }
        }
        List<Stocks> stockList = stockRepo.findByUserId(userId);
        if(stockList == null){
            stockInvestmentsAmount.put("currentStocksAmount",0.0);
            stockInvestmentsAmount.put("investedStocksAmount",0.0);
            stockInvestmentsAmount.put("stockTotalGain",0.0);
        }else { stockInvestmentsAmount = getTotalInvestmentAmounts(stockList); }
        return stockInvestmentsAmount;
    }

    private Map<String, Double> getTotalInvestmentAmounts(List<Stocks> stockList) {
        Map<String,Double> stockInvestmentsAmount = new HashMap<>();
        Double currentStocksAmount = 0.0, investedStocksAmount = 0.0, totalGain = 0.0;
        for(Stocks stock : stockList){
            //get the list of stocks to get stock price from mongoDB
            List<StockPrice> list = wealthService.getAllStockPrices();

            double currentStockPrice = getCurrentStockPrice(list,stock.getStockName());
            double investedPrice = (stock.getStockPurchesdPrice());
            currentStocksAmount = currentStocksAmount +
                    (stock.getQuantity() * currentStockPrice);
            investedStocksAmount = investedStocksAmount + (stock.getQuantity() * investedPrice);
            totalGain = stock.getQuantity() * stockPrice;
        }
        stockInvestmentsAmount.put("currentStocksAmount",currentStocksAmount);
        stockInvestmentsAmount.put("investedStocksAmount",investedStocksAmount);
        stockInvestmentsAmount.put("stockTotalGain",totalGain);
        return stockInvestmentsAmount;
    }

    private double getCurrentStockPrice(List<StockPrice> list, String stockName) {
        Double currentStockPrice= 0.0;
        for (StockPrice stockPrice : list){
            if(stockPrice.getName().equalsIgnoreCase(stockName)){
                currentStockPrice = stockPrice.getPrice();
                break;
            }
        }
        return currentStockPrice;
    }

    private Map<String, Double> saveUserMfs(UserWealth userWealthRequest, Boolean saveFlag) {
        if(saveFlag && userWealthRequest.getMutualFunds() != null) {
            for (MutualFundDetails mf : userWealthRequest.getMutualFunds()) {
                MutualFunds mfEntity = mappingMfEntity(mf, userWealthRequest);
                if(updateFlag.equalsIgnoreCase("update")){
                    mfEntity = mutualFundRepo.findByDematAccountNumber(mf.getDematAccountNumber());
                    if(mfEntity == null){
                        throw new ResourceNotFoundException("mf demat account number id not found to update");
                    }
                }
                mutualFundRepo.save(mfEntity);
            }
        }
        List<MutualFunds> mutualFundList = mutualFundRepo.findByUserId(userId);
        Map<String,Double> mfAmounts = new HashMap<>();
        if(mutualFundList == null){
            mfAmounts.put("currentMFAmount",0.0);
            mfAmounts.put("investedMFAmount",0.0);
        } else{ mfAmounts = getTotalMfInvestments(mutualFundList); }
        return mfAmounts;
    }

    private Map<String, Double> getTotalMfInvestments(List<MutualFunds> mutualFundList) {
        Map<String,Double> mfAmounts = new HashMap<>();
        Double currentMFAmount = 0.0, investedMFAmount = 0.0;
        for(MutualFunds mf: mutualFundList){
            Double totalInvested = mf.getAvgNav() * mf.getUnits();
            investedMFAmount = investedMFAmount + totalInvested;
            //get the list of mutual fund and current nav
            List<MutualFundPrice> list = wealthService.getAllMutualFundPrices();
            Double currentNav = getCurrentNav(list,mf.getMfName());
            Double currentValue = currentNav * mf.getUnits();
            currentMFAmount = currentMFAmount + currentValue;
        }
        mfAmounts.put("investedMFAmount",investedMFAmount);
        mfAmounts.put("currentMFAmount",currentMFAmount);
        mfAmounts.put("totalGain",currentMFAmount - investedMFAmount);
        return mfAmounts;
    }

    private Double getCurrentNav(List<MutualFundPrice> list, String mfName) {
        Double currentNav = 0.0;
        for (MutualFundPrice mfprice : list){
            if(mfprice.getName().equalsIgnoreCase(mfName)){
                currentNav = mfprice.getNav();
                break;
            }
        }
        return currentNav;
    }

    private Double saveUserLoan(UserWealth userWealthRequest, Boolean saveFlag) throws Exception {
        try {
            Double loanDebt = 0.0;
            Boolean exsitsLoanAccountNumber = false;
            if (saveFlag && userWealthRequest.getLoans() != null) {
                for (LoanDetails loan : userWealthRequest.getLoans()) {
                    Loan loanEntity = mappingLoanEntity(loan, userWealthRequest);
                    if (!updateFlag.equalsIgnoreCase("update")) {
                        exsitsLoanAccountNumber = loanRepo.existsByLoanAccountNumber(loan.getLoanAccountNumber());
                    }
                    if (exsitsLoanAccountNumber) {
                        throw new ResourceNotFoundException("Loan account number you have entered is not found");
                    }
                    loanRepo.save(loanEntity);
                }
            }
            List<Loan> loanList = loanRepo.findByUserId(userId);
            if (loanList == null) {
                loanDebt = 0.0;
            } else {
                loanDebt = totalOutStandingAmount(loanList);
            }
            return loanDebt;
        }catch (ResourceNotFoundException ex) { throw new ResourceNotFoundException(ex.getMessage());
        }catch (ValidationException ex){ throw new ValidationErrorException(ex.getMessage());
        }catch (Exception ex){ throw new Exception(ex.getMessage());}
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
        Boolean exsitsDepositAccountNumber = false;
        if(saveFlag && userWealthRequest.getDeposits() != null) {
            for (DepositDetails deposit : userWealthRequest.getDeposits()) {
                Deposit depositEntity = mappingDepositEntity(deposit, userWealthRequest);
                if(!updateFlag.equalsIgnoreCase("update")){
                    exsitsDepositAccountNumber = depositRepo.existsByDepositAccountNumber(deposit.getDepositAccountNumber());}
                if(exsitsDepositAccountNumber){
                    throw new ResourceNotFoundException("Account number you have entered is not found");
                }
                depositRepo.save(depositEntity);
            }
        }
        List<Deposit> depositList = depositRepo.findByUserId(userId);
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

    private Double saveUserAccounts(UserWealth userWealthRequest,Boolean saveFlag) throws Exception {
        Double availableSavings = 0.0;
        Boolean exsitsAccountAnumber = false;
        if(saveFlag && userWealthRequest.getAccounts() != null) {
            for (AccountDetails account : userWealthRequest.getAccounts()) {
                    Account accountEntity = mappingAccountEntity(account, userWealthRequest);
                    if(!updateFlag.equalsIgnoreCase("update")){
                        exsitsAccountAnumber = accountRepo.existsByAccountNumber(account.getAccountNumber());}
                    if(exsitsAccountAnumber){
                        throw new ResourceNotFoundException("Account number you have entered" + account.getAccountNumber() + " alredy is present");
                    }
                    accountRepo.save(accountEntity);
            }
        }
        List<Account> accountsList = accountRepo.findByUserId(userId);
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
        if(updateFlag.equalsIgnoreCase("update")){
            stockEntity = stockRepo.findByDematAccountNumber(stock.getDematAccountNumber());
            if(stockEntity == null){
                throw new ResourceNotFoundException("Demat account number of stock is not found to update");
            }
            Boolean isStockExsits = isStockExsistBasedOnNameAndUserId(stock.getStockName(),userId);
            if(isStockExsits){
                Integer totalQty = stockEntity.getQuantity() + stock.getQuantity();
                Double avgPurchasedPrice = ((stock.getStockPurchesdPrice()*stock.getQuantity()) + (stockEntity.getStockPurchesdPrice()*stockEntity.getQuantity()))/totalQty;
                stock.setQuantity(totalQty);
                stock.setStockPurchesdPrice(avgPurchasedPrice);
            }
        }
        stockEntity.setStockName(stock.getStockName());
        stockEntity.setQuantity(stock.getQuantity());
        stockEntity.setUser(getUser(userId));
        stockEntity.setDematAccountNumber(stock.getDematAccountNumber());
        stockEntity.setStockPurchesdPrice(stock.getStockPurchesdPrice());
        return stockEntity;
    }

    private MutualFunds mappingMfEntity(MutualFundDetails mf, UserWealth userWealth) {
        MutualFunds mfEntity = new MutualFunds();
        if(updateFlag.equalsIgnoreCase("update")){
            mfEntity = mutualFundRepo.findByDematAccountNumber(mf.getDematAccountNumber());
            if(mfEntity == null){
                throw new ResourceNotFoundException("Demat account number of mf is not found to update");
            }
            Boolean isFundExsits = isFundExsistBasedOnNameAndUserId(mf.getMfName(),userId);
            if(isFundExsits){
                Double avgUnit = mf.getUnits()+mf.getUnits();
                Double avgPurchasedNav = (mf.getAvgNav() + mfEntity.getAvgNav());
                mf.setUnits(avgUnit);
                mf.setAvgNav(avgPurchasedNav);
            }
        }
        mfEntity.setAvgNav(mf.getAvgNav());
        mfEntity.setUser(getUser(userId));
        mfEntity.setUnits(mf.getUnits());
        mfEntity.setMfName(mf.getMfName());
        mfEntity.setDematAccountNumber(mf.getDematAccountNumber());
        return mfEntity;
    }

    private Boolean isFundExsistBasedOnNameAndUserId(String mfName, Long userId) {
        List<MutualFunds> mutualFunds = mutualFundRepo.findByUserId(userId);
        for(MutualFunds mf: mutualFunds){
            if(mf.getMfName().equalsIgnoreCase(mfName)){
                return true;
            }
        }
        return false;
    }
    private Boolean isStockExsistBasedOnNameAndUserId(String stockName, Long userId) {
        List<Stocks> stocks = stockRepo.findByUserId(userId);
        for(Stocks s: stocks){
            if(s.getStockName().equalsIgnoreCase(stockName)){
                return true;
            }
        }
        return false;
    }

    private Loan mappingLoanEntity(LoanDetails loan, UserWealth userWealth) {
        Loan loanEntity = new Loan();
        if(loan.getOutstandingAmount() >= loan.getPrincipleAmount()){
            throw new ValidationException("Principal amount must be greater than outstanding amount");
        }
        if(updateFlag.equalsIgnoreCase("update")){
            loanEntity = loanRepo.findByLoanAccountNumber(loan.getLoanAccountNumber());
            if(loanEntity == null){
                throw new ResourceNotFoundException("Loan account number id not found to update");
            }
        }
        loanEntity.setLoanType(loan.getLoanType());
        loanEntity.setLoanAccountNumber(loan.getLoanAccountNumber());
        loanEntity.setPrincipleAmount(loan.getPrincipleAmount());
        loanEntity.setOutstandingAmount(loan.getOutstandingAmount());
        loanEntity.setUser(getUser(userId));
        return loanEntity;
    }

    private Deposit mappingDepositEntity(DepositDetails deposit, UserWealth userWealth) {
        Deposit depositEntity = new Deposit();
        if(updateFlag.equalsIgnoreCase("update")){
            depositEntity = depositRepo.findByDepositAccountNumber(deposit.getDepositAccountNumber());
            if(depositEntity == null){
                throw new ResourceNotFoundException("Deposit Account number you have entered is not found to update");
            }
        }
        depositEntity.setDepositAccountNumber(deposit.getDepositAccountNumber());
        depositEntity.setDepositType(deposit.getDepositType());
        depositEntity.setAmount(deposit.getAmount());
        depositEntity.setUser(getUser(userId));
        return depositEntity;
    }

    private Account mappingAccountEntity(AccountDetails account,UserWealth userWealth) throws Exception {
        try {
            Account accEntity = new Account();
            Map<String ,String> validBranch = findValidBranch(wealthService.getAllBankDetails(),account.getBranch());
            if(validBranch.size() == 0){
                throw new ResourceNotFoundException("please pick the official banks shotName");
            }
            if (updateFlag.equalsIgnoreCase("update")) {
                accEntity = accountRepo.findByAccountNumber(account.getAccountNumber());
                if (accEntity == null) {
                    throw new ResourceNotFoundException("Account number you have entered is not found to update");
                }
            }
            accEntity.setAccountNumber(account.getAccountNumber());
            accEntity.setBalance(account.getBalance());
            accEntity.setBranch(validBranch.get(account.getBranch()));
            accEntity.setUser(getUser(userId));
            return accEntity;
        }catch (ResourceNotFoundException ex){
            throw new ResourceNotFoundException(ex.getMessage());
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    private Map<String ,String> findValidBranch(List<BankDetails> allBankDetails, String branch) {
        Set<BankDetails> bankBranch = new HashSet<>(allBankDetails);
        Map<String ,String> pickedBank  = containsBankWithShortName(allBankDetails,branch);
        return pickedBank;
    }

    private Map<String ,String> containsBankWithShortName(List<BankDetails> allBankDetails, String branch) {
        Map<String ,String> pickedBank = new HashMap<>();
        for(BankDetails bank : allBankDetails){
            if(bank.getShortName().equalsIgnoreCase(branch)){
                pickedBank.put(bank.getShortName(),bank.getFullName());
                break;
            }
        }
        return pickedBank;
    }


    public User getUser(Long userId){
        return userRepo.findById(userId).orElse(null);
    }

    public UserWealth getWealthData(Long userId) {
        UserWealth user = new UserWealth();
        return user;
    }

    public UserWealthResponse getUserWealth(String token) throws Exception {
        User user= setUserIdBasedOnToken(token);
        UserWealth detailedUserWealthData = new UserWealth();
            detailedUserWealthData = mappingEntityToDtoForWealth(user);
            UserWealthResponse userWealth =  getTotalUserWealth(detailedUserWealthData,token);
            return userWealth;
    }

    private UserWealthResponse getTotalUserWealth(UserWealth detailedUserWealthData,String token) throws Exception {
        getWealthTriggerFlag = true;
        UserWealthResponse userWealth = saveWealthData(detailedUserWealthData,token);
        getWealthTriggerFlag = false;
        return userWealth;
    }

    private UserWealth mappingEntityToDtoForWealth(User user) {
        UserWealth userWealth = new UserWealth();
        userWealth.setAccounts(mapAccountDto(user.getAccount()));
        userWealth.setDeposits(mapDepositDto(user.getDeposits()));
        userWealth.setLoans(mapLoanDto(user.getLoans()));
        userWealth.setMutualFunds(mapMutualFundDto(user.getMutualFunds()));
        userWealth.setStocks(mapStockDto(user.getStocks()));
        return  userWealth;
    }

    private List<StocksDetails> mapStockDto(List<Stocks> stocks) {
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

    private List<MutualFundDetails> mapMutualFundDto(List<MutualFunds> mutualFunds) {
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

    private List<LoanDetails> mapLoanDto(List<Loan> loans) {
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

    private List<DepositDetails> mapDepositDto(List<Deposit> deposits) {
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

    private List<AccountDetails> mapAccountDto(List<Account> account) {
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

    public UserWealthResponse updateUserWealth(UserWealth userWealthRequest,String token) throws Exception {
        updateFlag = "update";
        UserWealthResponse userWealth = saveWealthData(userWealthRequest, token);updateFlag = "";
        return userWealth;
    }
}
