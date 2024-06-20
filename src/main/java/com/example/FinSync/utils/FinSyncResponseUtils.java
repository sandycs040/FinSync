package com.example.FinSync.utils;


import com.example.FinSync.entity.FinSyncResponseDetails;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.logging.Logger;

@NoArgsConstructor
public class FinSyncResponseUtils {

    static Logger logger = Logger.getLogger(FinSyncResponseUtils.class.getName());

    public static ResponseEntity<?> generateSuccessResponse(Object response) {
        System.out.println(" | 2 | " +response);
        return ResponseEntity.ok().body(getSucessResponse(response));
    }

    public static ResponseEntity<?> generateSuccessResponse(String response) {
        //logger.info(" | Sucess | " + response);
        System.out.println(" | 3 | " +response);
        return ResponseEntity.ok().body(getSucessResponse(response));
    }

    public static ResponseEntity<?> generateSuccessResponse(Object response, String message) {
        System.out.println(" | 4 | " +response);
        return ResponseEntity.ok().body(getSucessResponse(response, message));
        }

    public static ResponseEntity<?> generateErrorResponse(Map<String,String> response,String message) {
        System.out.println(" | 1 | " +response);
        return ResponseEntity.badRequest().body(getErrorResponse(response, message));
    }

    public static FinSyncResponseDetails getSucessResponse(Object response){
        System.out.println(" | 5 | " +response);
        FinSyncResponseDetails dto = new FinSyncResponseDetails("200", "success",response);
        logger.info("FinSync - Wealth : Success Response : " + dto);
        return dto;
    }

    public static FinSyncResponseDetails getErrorResponse(Map<String,String > response, String message){
        System.out.println(" | 1.1 | " +response);
        FinSyncResponseDetails dto = new FinSyncResponseDetails("400", message,response);
        logger.info("FinSync - Wealth : Error Response : " + dto);
        return dto;
    }

    public static FinSyncResponseDetails getSucessResponse(Object response, String message){
        FinSyncResponseDetails dto = new FinSyncResponseDetails("200",message,response);
        logger.info("FinSync - Wealth : Success Response : " + dto);
        return dto;
    }

    public static FinSyncResponseDetails getErrorResponse(String status, String message, Object response) {
        FinSyncResponseDetails dto = new FinSyncResponseDetails(status, message, response);
        logger.info("FinSync - Wealth : Error Response : " + dto);
        return dto;
    }
}
