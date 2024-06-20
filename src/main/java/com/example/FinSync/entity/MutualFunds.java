package com.example.FinSync.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "mutual_funds")
@Data
public class MutualFunds {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "mf_id")
    private UUID mfId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_Id")
    @JsonBackReference
    private User user;

    @Column(name = "demat_number",nullable = false,unique = true)
    private String dematAccountNumber;

    @Column(name = "mf_name")
    private String mfName;

    @Column(name = "units")
    private double units;

    @Column(name = "nav")
    private double avgNav;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private boolean deletedFlag = false;
}
