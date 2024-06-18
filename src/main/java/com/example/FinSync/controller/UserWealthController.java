package com.example.FinSync.controller;

import com.example.FinSync.entity.UserWealth;
import com.example.FinSync.entity.UserWealthResponse;
import com.example.FinSync.service.UserWealthService;
import com.example.FinSync.utils.FinSyncResponseUtils;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/v1/")
public class UserWealthController {

    @Autowired
    UserWealthService userWealthService;

    private static final Logger logger = Logger.getLogger(UserWealthController.class.getName());

    @PostMapping("/userWealth")
    public ResponseEntity<?> saveUserWealth(@Valid @RequestBody  UserWealth userWealthRequest){
        logger.info("Received POST Request for User Wealth API {}"+userWealthRequest);
        try {
            UserWealthResponse wealthResponse = userWealthService.saveWealthData(userWealthRequest);
            return FinSyncResponseUtils.generateSuccessResponse(wealthResponse);
        }catch (Exception e){
            logger.info("| Exception : save wealth api {} " + e.getMessage());
        }
        return ResponseEntity.ok("Success");
    }
}
