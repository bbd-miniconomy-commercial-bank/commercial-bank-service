package com.miniconomy.commercial_bank_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.dto.TransactionRequest;
import com.miniconomy.commercial_bank_service.entity.Account;
import com.miniconomy.commercial_bank_service.entity.Transaction;
import com.miniconomy.commercial_bank_service.entity.TransactionStatusType;
import com.miniconomy.commercial_bank_service.repository.AccountRepository;
import com.miniconomy.commercial_bank_service.repository.TransactionRepository;
import com.miniconomy.commercial_bank_service.response.TransactionResponse;

@Service
public class TransactionService
{
  private final TransactionRepository transactionRepository;
  private final AccountRepository accountRepository;

  public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository)
  {
    this.transactionRepository = transactionRepository;
    this.accountRepository = accountRepository;
  }
  
  public List<TransactionResponse> retrieveTransactions(UUID creditAccountId, Pageable page)
  {
    List<Transaction> transactions = transactionRepository.findByAccountId(creditAccountId, page);
    return transactions.stream().map(transaction -> {
      Optional<Account> creditAccount = accountRepository.findById(transaction.getCreditAccountId());
      Optional<Account> debitAccount = accountRepository.findById(transaction.getDebitAccountId());

      TransactionResponse transactionResponse = new TransactionResponse(debitAccount.get().getAccountName(), creditAccount.get().getAccountName(), transaction.getTransactionAmount(), transaction.getTransactionStatus(), transaction.getDebitRef(), transaction.getCreditRef(), transaction.getTransactionDate());
      return transactionResponse;
    }).collect(Collectors.toList());
  } 

  public TransactionResponse retrieveTransactionsById(UUID id)
  {
    Transaction transaction = transactionRepository.findById(id).get();
    Account creditAccount = accountRepository.findById(transaction.getCreditAccountId()).get();
    Account debitAccount = accountRepository.findById(transaction.getDebitAccountId()).get();

    TransactionResponse transactionResponse = new TransactionResponse(debitAccount.getAccountName(), creditAccount.getAccountName(), transaction.getTransactionAmount(), transaction.getTransactionStatus(), transaction.getDebitRef(), transaction.getCreditRef(), transaction.getTransactionDate());
    return transactionResponse;
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
