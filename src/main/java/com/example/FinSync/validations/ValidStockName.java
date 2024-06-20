package com.example.FinSync.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StockNameValidator.class)
public @interface ValidStockName {
    String message() default "invalid stock name ";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default{};
}
