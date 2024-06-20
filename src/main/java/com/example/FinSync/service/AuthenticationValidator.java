package com.example.FinSync.service;

import com.example.FinSync.entity.authentication.SignIn;
import com.example.FinSync.entity.authentication.Signup;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthenticationValidator {

    private final Validator validator =  Validation.buildDefaultValidatorFactory().getValidator();

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationValidator.class);
    public Signup validateSignupDetails(String signupData){
        Signup signup;
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            signup = objectMapper.readValue(signupData, Signup.class);
        }catch (Exception e){
            logger.error("Encountering an exception during the parsing of Signup data, Exception Details : "+e);
            throw new IllegalArgumentException("Verify that the input JSON data does not adhere to the expected JSON format.");
        }
        validateViolations(signup);
        if(!signup.getPassword().equals(signup.getConfirmPassword())){
            throw new ValidationException("Password and ConfirmPassword is not matching, please verify it");
        }
        return signup;
    }
    public SignIn validateSignInDetails(String signInDetails){
        SignIn signIn;
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            signIn = objectMapper.readValue(signInDetails, SignIn.class);

        } catch (Exception e){
            logger.error("Encountering an exception during the parsing of Login data, Exception Details : ",e);
            throw new IllegalArgumentException("Verify that the input JSON data does not adhere to the expected JSON format.");
        }


        return signIn;

    }
    private void validateViolations(Signup signup){
        Set<ConstraintViolation<Signup>> violations = validator.validate(signup);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Signup> violation : violations) {
                logger.error("Details of Signup Validation Failure: "+violation.getPropertyPath() + ": " + violation.getMessage());
                throw new ValidationException(violation.getMessage());
            }

        }

    }
}
