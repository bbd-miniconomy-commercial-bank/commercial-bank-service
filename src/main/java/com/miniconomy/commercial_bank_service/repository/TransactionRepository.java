package com.miniconomy.commercial_bank_service.repository;

import com.miniconomy.commercial_bank_service.entity.Transaction;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
  List<Transaction> findByCreditAccId(Long creditAccId);
}
