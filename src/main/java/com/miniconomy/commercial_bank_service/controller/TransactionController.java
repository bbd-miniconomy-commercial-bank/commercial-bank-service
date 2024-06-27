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

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Transactions", description = "Queries related to service's transactions")
@RestController
@RequestMapping("/transactions")
class TransactionController {
    
  private final TransactionService transactionService;

  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }
  
  @Operation(
    summary = "Get services transactions",
    description = "Allows services to view their transactions"
  )
  @GetMapping(value = "", produces = "application/json")
  public ResponseEntity<?> getTransactions(@PageableDefault(size = 10) Pageable pageable)
  {
    UUID accountId = UUID.fromString("3d807dc5-5a12-455c-9b66-6876906e70d6"); //  we get the account name or account id from the access token
    List<TransactionResponse> transactions = this.transactionService.retrieveTransactions(accountId, pageable);
    if(transactions.size() > 0)
    {
      return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No transactions were found");
  }

  @Operation(
    summary = "Get services transactions by id",
    description = "Allows services to view their transactions by id"
  )
  @GetMapping(value = "/{id}", produces = "application/json")
  public ResponseEntity<?> getTransactionsById(@PathVariable UUID id) {

    Optional<Transaction> t = this.transactionService.retrieveTransactionsById(id);
    if (t.isPresent()) {
      Transaction transaction = t.get();
      TransactionResponse response = new TransactionResponse(transaction.getDebitAccount().getAccountName(), transaction.getCreditAccount().getAccountName(), transaction.getTransactionAmount(), transaction.getTransactionStatus(), transaction.getDebitRef(), transaction.getCreditRef(), transaction.getTransactionDate());
      return new ResponseEntity<>(response, HttpStatus.OK);
    }
    return new ResponseEntity<>("Transaction does not exist", HttpStatus.NOT_FOUND);
  }

  @Operation(
    summary = "Create transactions",
    description = "Allows services to create transactions"
  )
  @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> postTransactions(@RequestBody List<TransactionRequest> transactions) {
    List<Transaction> savedTransactions = this.transactionService.saveTransactions(transactions);
    return ResponseEntity.status(HttpStatus.OK).body(savedTransactions);
  }
}