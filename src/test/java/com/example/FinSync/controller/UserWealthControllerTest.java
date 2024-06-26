//package com.example.FinSync.controller;
//
//import com.example.FinSync.entity.User;
//import com.example.FinSync.entity.UserDetails;
//import com.example.FinSync.entity.UserRepository;
//import com.example.FinSync.entity.UserWealth;
//import com.example.FinSync.entity.authentication.SignIn;
//import com.example.FinSync.service.JwtService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.jupiter.api.extension.Extension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//
//import javax.xml.stream.Location;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@TestPropertySource(locations = "classpath:application-test.properties")
//public class UserWealthControllerTest {
//
//    @Autowired
//    JwtService jwtService;
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @Autowired
//    UserRepository userRepo;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @BeforeEach
//    @Transactional
//    public void setup(){
//        User user = new User("sandeepa","abc@gmail.com","123456789");
//        userRepo.save(user);
//    }
//
//    @Test
//    public void handleUserWealthPostTest() throws Exception {
//        User user = new User("sandeepa","abc@gmail.com","123456789");
//
//        //String wealthData = "{\"accounts\":[{\"accountNumber\":\"87876786869898\",\"branch\":\"SBI\",\"balance\":\"540000\"},{\"accountNumber\":\"87876786869899\",\"branch\":\"BOB\",\"balance\":\"40000\"}],\"deposits\":[{\"depositAccountNumber\":\"878767868698984\",\"depositType\":\"FD\",\"amount\":\"640000\"},{\"depositAccountNumber\":\"878767868698994\",\"depositType\":\"FD\",\"amount\":\"40000\"}],\"loans\":[{\"loanAccountNumber\":\"878767868698984\",\"loanType\":\"personal\",\"outstandingAmount\":\"640000\",\"principleAmount\":\"1000000\"},{\"loanAccountNumber\":\"878767868698994\",\"loanType\":\"agriculture\",\"outstandingAmount\":\"40000\",\"principleAmount\":\"1000000\"}],\"mutualFunds\":[{\"dematAccountNumber\":\"878767868698984\",\"mfName\":\"QuantFocusedFundFocused\",\"units\":\"64\",\"avgNav\":\"28\"},{\"dematAccountNumber\":\"878767868698985\",\"mfName\":\"AxisOvernightFundOvernight\",\"units\":\"64\",\"avgNav\":\"123\"}],\"stocks\":[{\"dematAccountNumber\":\"8787678698984\",\"stockName\":\"IndianToners\",\"quantity\":\"200\",\"stockPurchesdPrice\":\"123\"},{\"dematAccountNumber\":\"87876786869842\",\"stockName\":\"IDBIBank\",\"quantity\":\"1000\",\"stockPurchesdPrice\":\"34\"}]}";
//        String wealthData = "{\"accounts\":[{\"accountNumber\":\"87876786869898\",\"branch\":\"SBI\",\"balance\":\"540000\"},{\"accountNumber\":\"87876786869899\",\"branch\":\"BOB\",\"balance\":\"40000\"}]}";
//        UserWealth userWealth = objectMapper.readValue(wealthData, UserWealth.class);
//        String token = jwtService.generateToken(user);
//        mockMvc.perform(post("/userWealth1")
//                        .header("Authorization", token)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(userWealth.toString()))
//                .andExpect(status().isOk());
//    }
//
//
//}
//
//
//
////    @Test
////    public void handleUserWealthGetTest() throws Exception {
////        User user = new User("sandeepa","abc@gmail.com","123456789");
////        String token = jwtService.generateToken(user);
////        mockMvc.perform(get("/userWealth")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(objectMapper.writeValueAsString(token)))
////                .andExpect(status().isOk());
////    }