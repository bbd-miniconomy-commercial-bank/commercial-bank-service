package com.miniconomy.commercial_bank_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miniconomy.commercial_bank_service.dto.TransactionRequest;
import com.miniconomy.commercial_bank_service.entity.Transaction;
import com.miniconomy.commercial_bank_service.response.TransactionResponse;
import com.miniconomy.commercial_bank_service.service.TransactionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
class TransactionController {
    
  private final TransactionService transactionService;

  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }
  
  @Operation(
    summary = "Get services transactions",
    description = "Allows services to view their transactions"
  )
  @GetMapping(value = "/transactions", produces = "application/json")
  public ResponseEntity<?> getTransactions(@RequestParam Long creditAccId) {
    List<Transaction> transactions = this.transactionService.retrieveTransactions(creditAccId);
    if(transactions.size() > 0) {
      List<TransactionResponse> responseArray = new ArrayList<>();
      for(Transaction transaction: transactions) {
        TransactionResponse response = new TransactionResponse(
          transaction.getDebitAccountId(), 
          transaction.getCreditAccountId(), 
          transaction.getTransactionAmount(), 
          transaction.getTransactionStatus(), 
          transaction.getDebitRef(), 
          transaction.getCreditRef(), 
          transaction.getTransactionDate()
        );
        responseArray.add(response);
      }
      return ResponseEntity.status(HttpStatus.OK).body(responseArray);
    }
    return ResponseEntity.status(HttpStatus.OK).body("No transactions were found");
  }

  @Operation(
    summary = "Get services transactions by id",
    description = "Allows services to view their transactions by id"
  )
  @GetMapping(value = "transactions/{id}", produces = "application/json")
  public ResponseEntity<?> getTransactionsById(@PathVariable Long id) {
    Optional<Transaction> optionalTransaction = this.transactionService.retrieveTransactionsById(id);
    if (optionalTransaction.isPresent()) {
      Transaction transaction = optionalTransaction.get();

      TransactionResponse response = new TransactionResponse(
        transaction.getDebitAccountId(),
        transaction.getCreditAccountId(), 
        transaction.getTransactionAmount(), 
        transaction.getTransactionStatus(), 
        transaction.getDebitRef(), 
        transaction.getCreditRef(), 
        transaction.getTransactionDate()
      );

      return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction does not exist");
  }

  @Operation(
    summary = "Create transactions",
    description = "Allows services to create transactions"
  )
  @PostMapping(value = "/transactions/create", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> postTransactions(@RequestBody List<TransactionRequest> transactions) {
    List<Transaction> savedTransactions = this.transactionService.saveTransactions(transactions);
    return ResponseEntity.status(HttpStatus.OK).body(savedTransactions);
  }
}