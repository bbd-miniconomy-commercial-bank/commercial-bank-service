package com.miniconomy.commercial_bank_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miniconomy.commercial_bank_service.dto.TransactionRequest;
import com.miniconomy.commercial_bank_service.entity.Transaction;
import com.miniconomy.commercial_bank_service.response.TransactionResponse;
import com.miniconomy.commercial_bank_service.service.TransactionService;

import java.util.List;
import java.util.NoSuchElementException;
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
    UUID accountId = UUID.fromString("3d807dc5-5a12-455c-9b66-6876906e70d6");
    List<TransactionResponse> transactions = this.transactionService.retrieveTransactions(accountId, pageable);
    if(transactions.size() > 0)
    {
      return new ResponseEntity<>(transactions, HttpStatus.OK);
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
      TransactionResponse transaction = this.transactionService.retrieveTransactionsById(id);
      return new ResponseEntity<>(transaction, HttpStatus.OK);
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