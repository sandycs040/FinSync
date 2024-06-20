package com.example.FinSync.entity.authentication;

public class SignInResponse {
    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "{" +'\n'+
                "token = '" + token + '\'' +'\n'+
                '}';
    }
}
