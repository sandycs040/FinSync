package com.example.FinSync.service;

import com.example.FinSync.entity.authentication.SignIn;
import com.example.FinSync.entity.User;
import com.example.FinSync.entity.UserRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public void registerUser(User user) {
        if(userRepository.findByUserName(user.getUserName())!=null){
            throw new ValidationException("User Name "+user.getUserName()+" which is already in use please try different " +
                    "userName");
        }
        userRepository.save(user);
    }
    public boolean isItValidUser(SignIn signIn){
        User user = userRepository.findByUserName(signIn.getUserName());
        if(user==null){
            return false;
        }
        return user.getUserName().equals(signIn.getUserName()) && user.getPassword().equals(signIn.getPassword());
    }
    public User getUserDetails(SignIn signIn){
        return userRepository.findByUserName(signIn.getUserName());
    }
}
