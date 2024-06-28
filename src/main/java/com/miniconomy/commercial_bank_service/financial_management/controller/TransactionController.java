package com.miniconomy.commercial_bank_service.financial_management.controller;

import org.springframework.web.bind.annotation.*;

import com.miniconomy.commercial_bank_service.financial_management.dto.TransactionRequest;
import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;
import com.miniconomy.commercial_bank_service.financial_management.response.TransactionResponse;
import com.miniconomy.commercial_bank_service.financial_management.service.TransactionService;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    UUID accountId = UUID.fromString("988f6a7d-a6cb-432a-97f9-45061b143658"); //  we get the account name or account id from the access token
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
    List<TransactionResponse> savedTransactions = this.transactionService.saveTransactions(transactions);
    if (savedTransactions.isEmpty()) {
      String message = "Your transactions have an invalid reference/account_name";
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createEntity("message", createEntity(message, savedTransactions)));
    }
    return ResponseEntity.status(HttpStatus.OK).body(savedTransactions);
  }

  public HashMap<String, Object> createEntity(String x, Object y) {
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put(x, y);
    return map;
  }
}