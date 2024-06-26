package com.miniconomy.commercial_bank_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.entity.Transaction;
import com.miniconomy.commercial_bank_service.repository.TransactionRepository;

@Service
public class TransactionService
{
  private final TransactionRepository transactionRepository;

  public TransactionService(TransactionRepository transactionRepository)
  {
    this.transactionRepository = transactionRepository;
  }
  
  public List<Transaction> retrieveTransactions(Long creditAccId)
  {
    return transactionRepository.findByCreditAccId(creditAccId);
  } 

  public Optional<Transaction> retrieveTransactionsById(Long id)
  {
    return transactionRepository.findById(id);
  } 

}
