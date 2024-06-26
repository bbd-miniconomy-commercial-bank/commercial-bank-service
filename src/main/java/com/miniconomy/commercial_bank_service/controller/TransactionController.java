package com.miniconomy.commercial_bank_service.controller;

import org.springframework.web.bind.annotation.*;

import com.miniconomy.commercial_bank_service.dto.TransactionRequest;
import com.miniconomy.commercial_bank_service.entity.Transaction;
import com.miniconomy.commercial_bank_service.response.TransactionResponse;
import com.miniconomy.commercial_bank_service.service.TransactionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
  public ResponseEntity<?> getTransactions(@RequestParam UUID creditAccId) {
    List<Transaction> transactions = this.transactionService.retrieveTransactions(creditAccId);
    if(transactions.size() > 0) {
      List<TransactionResponse> responseArray = new ArrayList<>();
      for(Transaction transaction: transactions) {
        TransactionResponse response = new TransactionResponse(
          transaction.getDebitAccount().getAccountName(), 
          transaction.getCreditAccount().getAccountName(), 
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
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No transactions were found");
  }

  @Operation(
    summary = "Get services transactions by id",
    description = "Allows services to view their transactions by id"
  )
  @GetMapping(value = "transactions/{id}", produces = "application/json")
  public ResponseEntity<?> getTransactionsById(@PathVariable UUID id) {
    Optional<Transaction> optionalTransaction = this.transactionService.retrieveTransactionsById(id);
    if (optionalTransaction.isPresent()) {
      Transaction transaction = optionalTransaction.get();

      TransactionResponse response = new TransactionResponse(
        transaction.getDebitAccount().getAccountName(), 
        transaction.getCreditAccount().getAccountName(), 
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