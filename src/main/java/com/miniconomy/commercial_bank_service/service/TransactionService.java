package com.miniconomy.commercial_bank_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.dto.TransactionRequest;
import com.miniconomy.commercial_bank_service.entity.Transaction;
import com.miniconomy.commercial_bank_service.entity.TransactionStatusType;
import com.miniconomy.commercial_bank_service.repository.TransactionRepository;

@Service
public class TransactionService
{
  private final TransactionRepository transactionRepository;

  public TransactionService(TransactionRepository transactionRepository)
  {
    this.transactionRepository = transactionRepository;
  }
  
  public List<Transaction> retrieveTransactions(UUID creditAccountId, Pageable page)
  {
    System.out.println(creditAccountId);
    return transactionRepository.findByAccountId(creditAccountId, page);
  } 

  public Optional<Transaction> retrieveTransactionsById(UUID id)
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
      transaction.setCreditAccountId(UUID.fromString("3d807dc5-5a12-455c-9b66-6876906e70d6"));
      transaction.setTransactionStatus(TransactionStatusType.pending);
      transaction.setTransactionDate(java.time.LocalDate.now().toString());
      
      transactions.add(transaction);
    }
    return transactionRepository.saveAll(transactions);
  }
}
