package com.miniconomy.commercial_bank_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.miniconomy.commercial_bank_service.entity.LoanInterest;

@Repository
public interface LoanInterestRepository extends JpaRepository<LoanInterest, Long> {
}
