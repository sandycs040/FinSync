package com.example.FinSync.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MutualFundNameValidator.class)
public @interface ValidMutualFundName {
    String message() default "Invaild fund name";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default{};
}
