package com.example.FinSync.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(name="user_name",nullable = false)
    private String userName;

    @Column(name="email",nullable = false)
    private String email;


    @Column(name="password",nullable = false)
    @Convert(converter = com.example.FinSync.util.PasswordEncryptor.class)
    private String password;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Account> account;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Deposit> deposits;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Loan> Loans;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Stocks> stocks;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<MutualFunds> mutualFunds;

    public User(String userName, String emailId, String password) {
        this.userName = userName;
        this.email = emailId;
        this.password = password;
    }
}
