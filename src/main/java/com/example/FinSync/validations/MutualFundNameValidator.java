package com.example.FinSync.validations;

import com.example.FinSync.entity.mongoWealth.MutualFundPrice;
import com.example.FinSync.service.WealthService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MutualFundNameValidator implements ConstraintValidator<ValidMutualFundName,String> {

    @Autowired
    WealthService wealthService;


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        List<String> mfNames = new ArrayList<>();
        List<MutualFundPrice> list = wealthService.getAllMutualFundPrices();
        for(MutualFundPrice mf : list){
            mfNames.add(mf.getName());
        }
        if(value == null){
            return true; // Let @NotNull handle null values
        }
        return mfNames.contains(value);
    }
}
