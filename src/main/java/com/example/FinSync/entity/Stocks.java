package com.example.FinSync.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "stocks")
@Data
public class Stocks {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "stock_Id")
    private UUID stockId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @Column(name = "demat_number",unique = true)
    private String dematAccountNumber;

    @Column(name = "stock_name")
    private String stockName;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "stack_purchased_price")
    private double stockPurchesdPrice;

    @Column(name = "description")
    private String description;

    @Column(name = "stock_price")
    private double stockPrice;

    @Column(name = "stock_selling_price")
    private double stockSellingPrice;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_flag")
    private boolean deletedFlag = false;
}
