package com.miniconomy.commercial_bank_service.financial_management.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import com.miniconomy.commercial_bank_service.financial_management.entity.Account;
import com.miniconomy.commercial_bank_service.financial_management.entity.AccountDelegation;

public interface AccountDelegationRepository extends JpaRepository<AccountDelegation, UUID> {
    
    //List<AccountDelegation> findByAccount(Account account);
    //List<AccountDelegation> findByDelegatedAccount(Account account);
    //Optional<AccountDelegation> findById(UUID id);
}
