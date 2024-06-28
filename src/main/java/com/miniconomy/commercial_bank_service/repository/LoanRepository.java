package com.miniconomy.commercial_bank_service.repository;

import com.miniconomy.commercial_bank_service.entity.Loan;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, UUID> {
    
}