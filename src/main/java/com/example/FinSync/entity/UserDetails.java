package com.example.FinSync.entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetails {

    @NotEmpty(message = "Username cannot be blank")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String userName;

    @NotEmpty(message = "Email cannot be blank")
    private String emailId;
    @NotEmpty(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotEmpty(message = "confirmPassword cannot be blank")
    @Size(min = 8, message = "confirmPassword must be at least 8 characters")
    private String confirmPassword;

}
