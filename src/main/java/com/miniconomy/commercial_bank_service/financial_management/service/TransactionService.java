package com.miniconomy.commercial_bank_service.financial_management.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.financial_management.dto.TransactionRequest;
import com.miniconomy.commercial_bank_service.financial_management.entity.Account;
import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;
import com.miniconomy.commercial_bank_service.financial_management.entity.TransactionStatusType;
import com.miniconomy.commercial_bank_service.financial_management.repository.AccountRepository;
import com.miniconomy.commercial_bank_service.financial_management.repository.TransactionRepository;
import com.miniconomy.commercial_bank_service.financial_management.response.TransactionResponse;

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
  
  public List<TransactionResponse> retrieveTransactions(UUID creditAccountId, Pageable pages)
  {
    Optional<Account> acc = accountRepository.findById(creditAccountId);
    if (acc.isPresent()) {
      List<Transaction> credT = transactionRepository.findByCreditAccount(acc.get(), pages);
      List<Transaction> debT = transactionRepository.findByDebitAccount(acc.get(), pages);

      credT.addAll(debT);
      List<TransactionResponse> trList = new ArrayList<>();

      credT.stream().forEach(transaction -> {
        Optional<Account> creditAccount = accountRepository.findById(transaction.getCreditAccount().getId());
        Optional<Account> debitAccount = accountRepository.findById(transaction.getDebitAccount().getId());

        TransactionResponse transactionResponse = new TransactionResponse(debitAccount.get().getAccountName(), creditAccount.get().getAccountName(), transaction.getTransactionAmount(), transaction.getTransactionStatus(), transaction.getDebitRef(), transaction.getCreditRef(), transaction.getTransactionDate());
        trList.add(transactionResponse);
      });
      return trList;
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

  public List<TransactionResponse> saveTransactions(List<TransactionRequest> tRequests)
  {
    //tRequests.forEach(t -> System.out.println(t.getDebitRef()));
    List<Transaction> transactions = new ArrayList<>();
    List<TransactionResponse> tResponses = new ArrayList<>();

    for (TransactionRequest request : tRequests)
    {
      Transaction transaction = new Transaction();
      TransactionResponse res = new TransactionResponse();

      Optional<Account> dbAcc = accountRepository.findByAccountName(request.getDebitAccountName());
      Optional<Account> crAcc = accountRepository.findByAccountName("commercial-bank"); // for now it's commercial-bank, but their api token should have an account-name
      
      if (dbAcc.isPresent() && crAcc.isPresent()) {
        transaction.setDebitAccount(dbAcc.get());
        res.setDebitorAccName(dbAcc.get().getAccountName());

        transaction.setCreditAccount(crAcc.get());
        res.setCreditorAccName(crAcc.get().getAccountName());

        transaction.setTransactionAmount(request.getTransactionAmount());
        res.setAmount(request.getTransactionAmount());

        transaction.setCreditRef(request.getCreditRef());
        res.setCreditRef(request.getCreditRef());

        transaction.setDebitRef(request.getDebitRef());
        res.setDebitRef(request.getDebitRef());

        transaction.setTransactionStatus(TransactionStatusType.pending);
        res.setStatus(TransactionStatusType.pending);

        transaction.setTransactionDate(java.time.LocalDate.now().toString().replace("-", ""));
        res.setDate(java.time.LocalDate.now().toString().replace("-", ""));

        transactions.add(transaction);
        tResponses.add(res);
      }
      else {
        break;
      }
    }
    if (transactions.size()>0) {
      transactionRepository.saveAll(transactions);
    }

    return tResponses;
  }
}
