package com.example.FinSync.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MutualFundRepository extends JpaRepository<MutualFunds, UUID> {
    List<MutualFunds> findByUserId(Long userId);

    MutualFunds findByDematAccountNumber(String dematAccountNumber);
}
