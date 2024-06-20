package com.example.FinSync.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "loan_id")
    private UUID loanId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    @JsonBackReference
    private User user;

    @Column(name = "loan_account_number" ,nullable = false,unique = true)
    private String loanAccountNumber;

    @Column(name = "loan_type")
    private String loanType;

    @Column(name = "principle_amount")
    private double principleAmount;

    @Column(name = "outstanding_amount")
    private double outstandingAmount;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private boolean deletedFlag = false;
}
