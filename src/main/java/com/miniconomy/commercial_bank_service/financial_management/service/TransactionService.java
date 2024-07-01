package com.miniconomy.commercial_bank_service.financial_management.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.financial_management.entity.Account;
import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;
import com.miniconomy.commercial_bank_service.financial_management.entity.TransactionStatusType;
import com.miniconomy.commercial_bank_service.financial_management.repository.AccountRepository;
import com.miniconomy.commercial_bank_service.financial_management.repository.TransactionRepository;
import com.miniconomy.commercial_bank_service.financial_management.request.TransactionRequest;

@Service
public class TransactionService
{
  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  private final TransactionRepository transactionRepository;
  private final AccountRepository accountRepository;

  public TransactionService(TransactionRepository transactionRepository, AccountRepository accRepo)
  {
    this.transactionRepository = transactionRepository;
    this.accountRepository = accRepo;
  }
  
  public List<Transaction> retrieveTransactions(String accountName, Pageable pages)
  {
    Optional<Account> acc = accountRepository.findByAccountName(accountName);
    if (acc.isPresent()) {
      List<Transaction> credT = transactionRepository.findByCreditAccount(acc.get(), pages);
      List<Transaction> debT = transactionRepository.findByDebitAccount(acc.get(), pages); 

      credT.addAll(debT);
      return credT;
    }
    return List.of(); // returns empty list
  } 

  public Optional<Transaction> retrieveTransactionsById(UUID id, String accountName)
  {
    Optional<Account> accountOptional = accountRepository.findByAccountName(accountName);

    String sql = "SELECT * FROM transaction WHERE transaction_id = :id AND credit_account_id = :accountId OR debit_account_id = :accountId";
    SqlParameterSource parameters = new MapSqlParameterSource()
        .addValue("id", id)
        .addValue("accountId", accountOptional.get().getId());
    Transaction transaction = jdbcTemplate.queryForObject(sql, parameters, Transaction.class);

    return Optional.of(transaction);
    //if 
    //Account creditAccount = accountRepository.findById(transaction.getCreditAccountId()).get();
    //Account debitAccount = accountRepository.findById(transaction.getDebitAccountId()).get();
//
    //TransactionResponse transactionResponse = new TransactionResponse(debitAccount.getAccountName(), creditAccount.getAccountName(), transaction.getTransactionAmount(), transaction.getTransactionStatus(), transaction.getDebitRef(), transaction.getCreditRef(), transaction.getTransactionDate());
    //return transactionResponse;
  } 

  public List<Transaction> saveTransactions(List<TransactionRequest> tRequests, String creditAccountName)
  {
    //tRequests.forEach(t -> System.out.println(t.getDebitRef()));
    List<Transaction> transactions = new ArrayList<>();

    for (TransactionRequest request : tRequests)
    {
      Transaction transaction = new Transaction();

      Optional<Account> dbAcc = accountRepository.findByAccountName(request.getDebitAccountName());
      Optional<Account> crAcc = accountRepository.findByAccountName(creditAccountName);
      
      if (dbAcc.isPresent() && crAcc.isPresent()) {
        transaction.setDebitAccount(dbAcc.get());
        transaction.setCreditAccount(crAcc.get());
        transaction.setTransactionAmount(request.getAmount());
        transaction.setCreditRef(request.getCreditRef());
        transaction.setDebitRef(request.getDebitRef());

        transaction.setTransactionStatus(TransactionStatusType.pending);

        transaction.setTransactionDate(java.time.LocalDate.now().toString().replace("-", ""));

        transactions.add(transaction);
      }
      else {
        continue;
      }
    }
    if (transactions.size()>0) {
      transactionRepository.saveAll(transactions);
    }

    return transactions;
  }
}
