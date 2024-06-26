package com.miniconomy.commercial_bank_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.dto.TransactionRequest;
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
  
  public List<Transaction> retrieveTransactions(Long creditAccountId)
  {
    return transactionRepository.findByAccountId(creditAccountId);
  } 

  public Optional<Transaction> retrieveTransactionsById(Long id)
  {
    return transactionRepository.findById(id);
  } 

  public List<Transaction> saveTransactions(List<TransactionRequest> transactionRequests)
  {
    List<Transaction> transactions = new ArrayList<>();
    for (TransactionRequest request : transactionRequests)
    {
      Transaction transaction = new Transaction();
      transaction.setDebitAccountId(request.getDebitAccountId());
      transaction.setTransactionAmount(request.getTransactionAmount());
      transaction.setCreditRef(request.getCreditRef());
      transaction.setDebitRef(request.getDebitRef());

      // TODO: Replace with creditors id using passed in token
      transaction.setCreditAccountId((long) 1);
      transaction.setTransactionStatus("pending");
      transaction.setTransactionDate(java.time.LocalDate.now().toString());
      
      transactions.add(transaction);
    }
    return transactionRepository.saveAll(transactions);
  }
}
