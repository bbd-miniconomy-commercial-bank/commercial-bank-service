package com.miniconomy.commercial_bank_service.financial_management.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miniconomy.commercial_bank_service.financial_management.entity.Loan;

public interface LoanRepository extends JpaRepository<Loan, UUID> {
    Optional<Loan> findByLoanIdAndAccountName(UUID loanId, String accountName);
    List<Loan> findByAccountName(String accountName);
}