//package com.example.FinSync.validations;
//
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//import jakarta.validation.Valid;
//
//import java.util.List;
//
//public class ConditionalListValidator implements ConstraintValidator<ValidateIfNotEmpty, List<?>> {
//
//    @Override
//    public void initialize(ValidateIfNotEmpty constraintAnnotation) {
//    }
//
//    @Override
//    public boolean isValid(List<?> list, ConstraintValidatorContext context) {
//        if (list == null || list.isEmpty()) {
//            return true; // Ignore validation if the list is null or empty
//        }
//
//        for (Object obj : list) {
//            if (!validateNested(obj, context)) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    private boolean validateNested(Object obj, ConstraintValidatorContext context) {
//        var validator = jakarta.validation.Validation.buildDefaultValidatorFactory().getValidator();
//        var violations = validator.validate(obj);
//        if (!violations.isEmpty()) {
//            context.disableDefaultConstraintViolation();
//            violations.forEach(violation -> context.buildConstraintViolationWithTemplate(violation.getMessage())
//                    .addConstraintViolation());
//            return false;
//        }
//        return true;
//    }
//}
