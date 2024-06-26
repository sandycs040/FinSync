package com.example.FinSync.controller;

import com.example.FinSync.entity.User;
import com.example.FinSync.entity.UserRepository;
import com.example.FinSync.entity.authentication.SignIn;
import com.example.FinSync.entity.authentication.Signup;
import com.example.FinSync.service.JwtService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.lang.Assert;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepo;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JwtService jwtService;

    @BeforeEach
    @Transactional
    public void setup(){
        User user = new User("sandeepa","abc@gmail.com","123456789");
        userRepo.save(user);
    }

    @Test
    public void handleSignupTest() throws Exception {
        Signup signUp = new Signup("sandy", "abc1@gmail.com", "123456789", "123456789");
        SignIn signIn = new SignIn("sandy", "123456789");
        User user = new User("sandy","abc1@gmail.com","123456789");
        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUp)))
                .andExpect(status().isOk());
        MvcResult perform = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signIn))).andReturn();
        String contentAsString = perform.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(contentAsString);

        // Extract token value
        String token = jsonNode.get("token").asText();
        Assert.isTrue(jwtService.isTokenValid(token,user));
    }
}
