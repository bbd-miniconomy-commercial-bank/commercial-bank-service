package com.miniconomy.commercial_bank_service.admin_portal.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miniconomy.commercial_bank_service.admin_portal.entity.Loan;

public interface LoanRepository extends JpaRepository<Loan, UUID> {
    
}