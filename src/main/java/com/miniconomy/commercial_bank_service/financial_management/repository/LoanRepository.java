package com.miniconomy.commercial_bank_service.financial_management.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miniconomy.commercial_bank_service.financial_management.entity.Loan;

public interface LoanRepository extends JpaRepository<Loan, UUID> {
    
}