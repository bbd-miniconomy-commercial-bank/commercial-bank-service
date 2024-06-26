package com.miniconomy.commercial_bank_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miniconomy.commercial_bank_service.dto.TransactionRequest;
import com.miniconomy.commercial_bank_service.entity.Transaction;
import com.miniconomy.commercial_bank_service.response.TransactionResponse;
import com.miniconomy.commercial_bank_service.service.TransactionService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Transactions", description = "Queries related to service's transactions")
@RestController
@RequestMapping("/transactions")
class TransactionController {
    
  final TransactionService transactionService;

  public TransactionController(TransactionService transactionService)
  {
    this.transactionService = transactionService;
  }
  
  @Operation(
    summary = "Get services transactions",
    description = "Allows services to view their transactions"
  )
  @GetMapping(value = "", produces = "application/json")
  public ResponseEntity<?> getTransactions(@PageableDefault(size = 10) Pageable pageable)
  {
    UUID creditAccountId = UUID.fromString("3d807dc5-5a12-455c-9b66-6876906e70d6");
    List<Transaction> transactions = this.transactionService.retrieveTransactions(creditAccountId, pageable);
    if(transactions.size() > 0)
    {
      List<TransactionResponse> responseArray = new ArrayList<>();
      for(Transaction transaction: transactions)
      {
        TransactionResponse response = new TransactionResponse(transaction.getDebitAccountId(), transaction.getCreditAccountId(), transaction.getTransactionAmount(), transaction.getTransactionStatus(), transaction.getDebitRef(), transaction.getCreditRef(), transaction.getTransactionDate());
        responseArray.add(response);
      }
      return new ResponseEntity<>(responseArray, HttpStatus.OK);
    }
    else
    {
      return new ResponseEntity<>("No transactions found", HttpStatus.NOT_FOUND);
    }
  }

  @Operation(
    summary = "Get services transactions by id",
    description = "Allows services to view their transactions by id"
  )
  @GetMapping(value = "/{id}", produces = "application/json")
  public ResponseEntity<?> getTransactionsById(@PathVariable UUID id)
  {
    try
    {
      Optional<Transaction> optionalTransaction = this.transactionService.retrieveTransactionsById(id);
      Transaction transaction = optionalTransaction.get();
      TransactionResponse response = new TransactionResponse(transaction.getDebitAccountId(), transaction.getCreditAccountId(), transaction.getTransactionAmount(), transaction.getTransactionStatus(), transaction.getDebitRef(), transaction.getCreditRef(), transaction.getTransactionDate());
      return new ResponseEntity<>(response, HttpStatus.OK);
    }
    catch(NoSuchElementException error)
    {
      return new ResponseEntity<>("Transaction does not exist", HttpStatus.NOT_FOUND);
    }
  }

  @Operation(
    summary = "Create transactions",
    description = "Allows services to create transactions"
  )
  @PostMapping(value = "", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> postTransactions(@RequestBody List<TransactionRequest> transactions)
  {
    List<Transaction> savedTransactions = transactionService.saveTransactions(transactions);
    return new ResponseEntity<>(savedTransactions, HttpStatus.CREATED);
  }
}