package com.miniconomy.commercial_bank_service.repository;

import com.miniconomy.commercial_bank_service.entity.Transaction;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
  @Query("SELECT t FROM Transaction t WHERE t.creditAccountId = :accountId OR t.debitAccountId = :accountId")
  List<Transaction> findByAccountId(@Param("accountId") Long accountId);
  
  Optional<Transaction> findById(Long id);
}
