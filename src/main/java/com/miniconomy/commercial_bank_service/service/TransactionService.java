package com.miniconomy.commercial_bank_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.swing.text.html.Option;

import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.dto.TransactionRequest;
import com.miniconomy.commercial_bank_service.entity.Account;
import com.miniconomy.commercial_bank_service.entity.Transaction;
import com.miniconomy.commercial_bank_service.entity.TransactionStatusType;
import com.miniconomy.commercial_bank_service.repository.AccountRepository;
import com.miniconomy.commercial_bank_service.repository.TransactionRepository;

@Service
public class TransactionService
{
  private final TransactionRepository transactionRepository;
  private final AccountRepository accountRepository;

  public TransactionService(TransactionRepository transactionRepository, AccountRepository accRepo)
  {
    this.transactionRepository = transactionRepository;
    this.accountRepository = accRepo;
  }
  
  public List<Transaction> retrieveTransactions(UUID creditAccountId)
  {
    Optional<Account> acc = accountRepository.findById(creditAccountId);
    if (acc.isPresent()) {
      return transactionRepository.findByCreditAccount(acc.get());
    }
    return List.of(); // returns empty list
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
      Optional<Account> dbAcc = accountRepository.findByAccountName(request.getDebitAccountName());
      Optional<Account> crAcc = accountRepository.findByAccountName("commercial-bank"); // for now it's commercial-bank, but their api token should have an account-name
      if (dbAcc.isPresent() && crAcc.isPresent()) {
        transaction.setDebitAccount(dbAcc.get());
        transaction.setCreditAccount(crAcc.get());
        transaction.setTransactionAmount(request.getTransactionAmount());
        transaction.setCreditRef(request.getCreditRef());
        transaction.setDebitRef(request.getDebitRef());
        transaction.setTransactionStatus(TransactionStatusType.PENDING);
        transaction.setTransactionDate(java.time.LocalDate.now().toString());
        transactions.add(transaction);
      }
    }
    return transactionRepository.saveAll(transactions);
  }
}
