package com.miniconomy.commercial_bank_service.financial_management.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miniconomy.commercial_bank_service.financial_management.entity.Account;

public interface AccountRepository extends JpaRepository<Account, UUID> {
  Optional<Account> findByAccountName(String accountName);
}
