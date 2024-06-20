package com.example.FinSync.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DepositRepository extends JpaRepository<Deposit, UUID> {
    List<Deposit> findByUserId(Long userId);

    Deposit findByDepositAccountNumber(String depositAccountNumber);

    Boolean existsByDepositAccountNumber(String depositAccountNumber);
}
