package com.example.FinSync.entity.authentication;

import com.example.FinSync.util.PasswordEncryptor;
import jakarta.persistence.Convert;

public class SignIn {
    private String userName;
    @Convert(converter = PasswordEncryptor.class)
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
