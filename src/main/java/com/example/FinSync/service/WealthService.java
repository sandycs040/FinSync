package com.example.FinSync.service;

import com.example.FinSync.entity.mongoWealth.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WealthService {
    @Autowired
    BankDetailsRepository bankDetailsRepository;
    @Autowired
    MutualFundPriceRepository mutualFundPriceRepository;
    @Autowired
    StockPriceRepository stockPriceRepository;
    @Cacheable("bankDetails")
    public List<BankDetails> getAllBankDetails() {
        return bankDetailsRepository.findAll();
    }

    @Cacheable("mutualFund")
    public List<MutualFundPrice> getAllMutualFundPrices() {
        return mutualFundPriceRepository.findAll();
    }
    @Cacheable("stock")
    public List<StockPrice> getAllStockPrices() {
        return stockPriceRepository.findAll();
    }


}
