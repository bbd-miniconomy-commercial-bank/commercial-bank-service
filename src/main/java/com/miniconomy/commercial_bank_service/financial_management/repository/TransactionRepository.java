package com.miniconomy.commercial_bank_service.financial_management.repository;

import com.miniconomy.commercial_bank_service.financial_management.entity.Account;
import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
  //@Query("SELECT t FROM Transaction t WHERE t.creditAccountId = :accountId OR t.debitAccountId = :accountId")
  List<Transaction> findByDebitAccount(Account acc, Pageable page);
  List<Transaction> findByCreditAccount(Account acc, Pageable page);

  //@Query("SELECT t FROM Transaction t WHERE t.creditAccountId = :accountId OR t.debitAccountId = :accountId")
  //List<Transaction> findByAccountId(@Param("accountId") UUID accountId, Pageable pageable);
}
