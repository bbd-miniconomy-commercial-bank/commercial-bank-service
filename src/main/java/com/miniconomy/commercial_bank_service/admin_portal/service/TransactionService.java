package com.miniconomy.commercial_bank_service.admin_portal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.admin_portal.entity.Account;
import com.miniconomy.commercial_bank_service.admin_portal.entity.Transaction;
import com.miniconomy.commercial_bank_service.admin_portal.entity.TransactionStatusType;
import com.miniconomy.commercial_bank_service.admin_portal.repository.AccountRepository;
import com.miniconomy.commercial_bank_service.admin_portal.repository.TransactionRepository;
import com.miniconomy.commercial_bank_service.admin_portal.request.TransactionRequest;

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
  
  public List<Transaction> retrieveTransactions(UUID creditAccountId, Pageable pages)
  {
    Optional<Account> acc = accountRepository.findById(creditAccountId);
    if (acc.isPresent()) {
      List<Transaction> credT = transactionRepository.findByCreditAccount(acc.get(), pages);
      List<Transaction> debT = transactionRepository.findByDebitAccount(acc.get(), pages); 

      credT.addAll(debT);
      return credT;
    }
    return List.of(); // returns empty list
  } 

  public Optional<Transaction> retrieveTransactionsById(UUID id)
  {
    Optional<Transaction> transaction = transactionRepository.findById(id);
    return transaction;
    //if 
    //Account creditAccount = accountRepository.findById(transaction.getCreditAccountId()).get();
    //Account debitAccount = accountRepository.findById(transaction.getDebitAccountId()).get();
//
    //TransactionResponse transactionResponse = new TransactionResponse(debitAccount.getAccountName(), creditAccount.getAccountName(), transaction.getTransactionAmount(), transaction.getTransactionStatus(), transaction.getDebitRef(), transaction.getCreditRef(), transaction.getTransactionDate());
    //return transactionResponse;
  } 

  public List<Transaction> saveTransactions(List<TransactionRequest> tRequests)
  {
    //tRequests.forEach(t -> System.out.println(t.getDebitRef()));
    List<Transaction> transactions = new ArrayList<>();

    for (TransactionRequest request : tRequests)
    {
      Transaction transaction = new Transaction();

      Optional<Account> dbAcc = accountRepository.findByAccountName(request.getDebitAccountName());
      Optional<Account> crAcc = accountRepository.findByAccountName(request.getCreditAccountName()); // for now it's commercial-bank, but their api token should have an account-name
      
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
