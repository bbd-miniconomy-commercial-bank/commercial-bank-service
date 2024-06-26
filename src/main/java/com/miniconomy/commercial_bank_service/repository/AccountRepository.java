package com.miniconomy.commercial_bank_service.repository;

import com.miniconomy.commercial_bank_service.entity.Account;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, UUID> {
  Account findByAccountName(String accountName);
}
