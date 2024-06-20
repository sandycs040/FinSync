package com.example.FinSync.controller;

import com.example.FinSync.entity.mongoWealth.BankDetails;
import com.example.FinSync.entity.mongoWealth.MutualFundPrice;
import com.example.FinSync.entity.mongoWealth.StockPrice;
import com.example.FinSync.service.WealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController

public class WealthController {

    @Autowired
    WealthService wealthService;

    @GetMapping("/bankNames")
    public List<BankDetails> getBankDetails() {
        return wealthService.getAllBankDetails();
    }
    @GetMapping("/mutualFundNames")
    public List<String> getMutualFundDetails() {
        List<String> mfNames = new ArrayList<>();
        List<MutualFundPrice> list = wealthService.getAllMutualFundPrices();
        for(MutualFundPrice mf : list){
            mfNames.add(mf.getName());
        }
        return mfNames;
    }
    @GetMapping("/stockNames")
    public List<String> getStockPrices() {
        List<String> stockNames = new ArrayList<>();
        List<StockPrice> list = wealthService.getAllStockPrices();
        for(StockPrice price : list){
            stockNames.add(price.getName());
        }
        return stockNames;
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
