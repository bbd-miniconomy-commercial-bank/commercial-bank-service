package com.miniconomy.commercial_bank_service.financial_management.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miniconomy.commercial_bank_service.financial_management.entity.LoanInterest;

@Repository
public interface LoanInterestRepository extends JpaRepository<LoanInterest, UUID> {
}
