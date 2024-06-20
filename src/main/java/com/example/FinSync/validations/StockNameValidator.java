package com.example.FinSync.validations;

import com.example.FinSync.entity.mongoWealth.StockPrice;
import com.example.FinSync.service.WealthService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class StockNameValidator implements ConstraintValidator<ValidStockName,String> {

    @Autowired
    WealthService wealthService;

    public boolean isValid(String value, ConstraintValidatorContext context) {
        List<String> stockNames = new ArrayList<>();
        List<StockPrice> list = wealthService.getAllStockPrices();
        for (StockPrice price : list) {
            stockNames.add(price.getName());
        }
        if (value == null) {
            return true; // Let @NotNull handle null values
        }
        return stockNames.contains(value);
    }
}
