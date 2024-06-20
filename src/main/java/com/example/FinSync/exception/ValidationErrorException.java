package com.example.FinSync.exception;

public class ValidationErrorException extends Exception{
    public ValidationErrorException() {
    }

    public ValidationErrorException(String message) {
        super(message);
    }
}
