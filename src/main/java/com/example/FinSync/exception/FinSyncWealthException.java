package com.example.FinSync.exception;

import com.example.FinSync.utils.FinSyncResponseUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.swing.text.BadLocationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@ControllerAdvice
public class FinSyncWealthException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        String message = "Validation error";
        return FinSyncResponseUtils.generateErrorResponse(errors,message);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourseNotFoundExcetion(ResourceNotFoundException ex, WebRequest request){
        Map<String,String> errors = new HashMap<>();
        errors.put("timestamp", LocalDateTime.now().toString());
        errors.put("cause",ex.getMessage());
        errors.put("request",request.toString());
        String message = "Please check the input data";
        return FinSyncResponseUtils.generateErrorResponse(errors, message);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadLocationException ex,WebRequest request){
        Map<String,String> errors = new HashMap<>();
        errors.put("timestamp" , LocalDateTime.now().toString());
        errors.put("cause",ex.getMessage());
        errors.put("request",request.toString());
        String message = "bad request";
        return FinSyncResponseUtils.generateErrorResponse(errors,message);
    }

    @ExceptionHandler(ValidationErrorException.class)
    public ResponseEntity<?> handleValidationException(ValidationErrorException ex, WebRequest request){
        Map<String,String> errors = new HashMap<>();
        errors.put("timestamp", LocalDateTime.now().toString());
        errors.put("cause",ex.getMessage());
        errors.put("request",request.toString());
        String message = "Validation error";
        return FinSyncResponseUtils.generateErrorResponse(errors,message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleFinSuncWealthGenricException(Exception ex, WebRequest request){
        Map<String,String> errors = new HashMap<>();
        errors.put("timestamp", LocalDateTime.now().toString());
        errors.put("cause",ex.getMessage());
        errors.put("request",request.toString());
        String message = "unexpected error occurred, please try again";
        return FinSyncResponseUtils.generateErrorResponse(errors,message);
    }
}
