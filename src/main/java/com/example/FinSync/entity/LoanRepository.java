package com.example.FinSync.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LoanRepository extends JpaRepository<Loan, UUID> {
    List<Loan> findByUserId(Long userId);

    Loan findByLoanAccountNumber(String loanAccountNumber);

    Boolean existsByLoanAccountNumber(String loanAccountNumber);
}
