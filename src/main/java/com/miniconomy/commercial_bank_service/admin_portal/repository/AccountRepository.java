package com.miniconomy.commercial_bank_service.admin_portal.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miniconomy.commercial_bank_service.admin_portal.entity.Account;

public interface AccountRepository extends JpaRepository<Account, UUID> {
  Optional<Account> findByAccountName(String accountName);
}
