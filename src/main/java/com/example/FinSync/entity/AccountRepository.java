package com.example.FinSync.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {
    Account findByAccountNumber(String accountNumber);

    List<Account> findByUserId(Long userId);

    Boolean existsByAccountNumber(String accountNumber);


}
