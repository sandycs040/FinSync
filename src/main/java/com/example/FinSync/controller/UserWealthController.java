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

    @PostMapping("/userWealth")
    public ResponseEntity<?> saveUserWealth(@RequestHeader("Authorization") String authorizationHeader,@Valid @RequestBody  UserWealth userWealthRequest) throws Exception {
        logger.info("Received POST Request for User Wealth API {}"+userWealthRequest);
        // Extract token
        String token = authorizationHeader.replace("Bearer ", "");
        return FinSyncResponseUtils.generateSuccessResponse(userWealthService.saveWealthData(userWealthRequest,token));
    }

    @GetMapping("/userWealth")
    public ResponseEntity<?> getUserWealth(@RequestHeader("Authorization") String authorizationHeader,@RequestParam("userId") Long userId) throws Exception {
        String token = authorizationHeader.replace("Bearer ", "");
        return FinSyncResponseUtils.generateSuccessResponse(userWealthService.getUserWealth(token));
    }

    @PutMapping("/userWealth")
    public ResponseEntity<?> updateUserWealth(@RequestHeader("Authorization") String authorizationHeader,@Valid @RequestBody UserWealth userWealthRequest) throws Exception {
        String token = authorizationHeader.replace("Bearer ", "");
        return FinSyncResponseUtils.generateSuccessResponse(userWealthService.updateUserWealth(userWealthRequest,token));
    }
}
