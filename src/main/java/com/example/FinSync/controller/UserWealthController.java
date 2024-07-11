package com.example.FinSync.controller;

import com.example.FinSync.entity.UserWealth;
import com.example.FinSync.entity.UserWealthResponse;
import com.example.FinSync.exception.ResourceNotFoundException;
import com.example.FinSync.service.UserWealthService;
import com.example.FinSync.utils.FinSyncResponseUtils;
import jakarta.validation.Valid;
import jakarta.validation.executable.ValidateOnExecution;
import org.apache.coyote.BadRequestException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/v1/")
public class UserWealthController {

    @Autowired
    UserWealthService userWealthService;

    private static final Logger logger = Logger.getLogger(UserWealthController.class.getName());

    /**
     * The User Wealth data which includes Accounts, deposit, Loan, Mutual funds, stocks details. this method will adds all to database.
     *
     * @param userWealthRequest all user data(Accounts, deposit, Loan, Mutual funds, stocks).
     * @return UserWealthResponse which return the total wealth
     */
    @PostMapping("/userWealth")
    public ResponseEntity<?> saveUserWealth(@RequestHeader("Authorization") String authorizationHeader,@Valid @RequestBody  UserWealth userWealthRequest) throws Exception {
        logger.info("Received POST Request for User Wealth API {}"+userWealthRequest);
        // Extract token
        String token = authorizationHeader.replace("Bearer ", "");
        return FinSyncResponseUtils.generateSuccessResponse(userWealthService.handleUserWealth(userWealthRequest,token));
    }

    /**
     * To get all user Wealth data as result(Accounts, deposit, Loan, Mutual funds, stocks).
     *
     * @param authorizationHeader : user specific token need to pass
     * @return UserWealthResponse which return the total wealth
     */
    @GetMapping("/userWealth")
    public ResponseEntity<?> getUserWealth(@RequestHeader("Authorization") String authorizationHeader) throws Exception {
        String token = authorizationHeader.replace("Bearer ", "");
        return FinSyncResponseUtils.generateSuccessResponse(userWealthService.getUserWealth(token));
    }

    /**
     * To Update User Wealth data that is Accounts, deposit, Loan, Mutual funds, stocks details can be updated based on the account number.
     *
     * @param userWealthRequest all user data(Accounts, deposit, Loan, Mutual funds, stocks).
     * @return UserWealthResponse which return the total wealth
     */
    @PutMapping("/userWealth")
    public ResponseEntity<?> updateUserWealth(@RequestHeader("Authorization") String authorizationHeader,@Valid @RequestBody UserWealth userWealthRequest) throws Exception {
        String token = authorizationHeader.replace("Bearer ", "");
        return FinSyncResponseUtils.generateSuccessResponse(userWealthService.updateUserWealth(userWealthRequest,token));
    }
}
