package com.miniconomy.commercial_bank_service.financial_management.controller;

import org.springframework.web.bind.annotation.*;

import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;
import com.miniconomy.commercial_bank_service.financial_management.request.TransactionsCreateRequest;
import com.miniconomy.commercial_bank_service.financial_management.response.ListResponseTemplate;
import com.miniconomy.commercial_bank_service.financial_management.response.ResponseTemplate;
import com.miniconomy.commercial_bank_service.financial_management.response.TransactionResponse;
import com.miniconomy.commercial_bank_service.financial_management.service.TransactionService;
import com.miniconomy.commercial_bank_service.financial_management.utils.TransactionUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;

@Tag(name = "Transactions", description = "Queries related to service's transactions")
@RestController
@RequestMapping("/transactions")
public class TransactionController {
    
  private final TransactionService transactionService;

  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }
  
  @Operation(
    summary = "Get services transactions",
    description = "Allows services to view their transactions"
  )
  @GetMapping(value = "", produces = "application/json")
  public ResponseEntity<ResponseTemplate<ListResponseTemplate<TransactionResponse>>> getTransactions(
        @RequestParam(defaultValue = "1") @Positive(message = "page must be greater than or equal to 1.") int page, 
        @RequestParam(defaultValue = "10") @Positive(message = "pageSize must be greater than or equal to 1")  @Max(value = 25, message = "Maximum pageSize is 25") int pageSize, 
        @RequestAttribute String accountName
  ) {
    
    ResponseTemplate<ListResponseTemplate<TransactionResponse>> response = new ResponseTemplate<>();
    int status = HttpStatus.OK.value();

    Pageable pageable = PageRequest.of(page, pageSize);
    List<Transaction> transactions = this.transactionService.retrieveTransactions(accountName, pageable);
    List<TransactionResponse> transactionResponses = transactions.stream().map(
            (transaction) -> TransactionUtils.transactionResponseMapper(transaction, accountName)
          ).collect(Collectors.toList());

    ListResponseTemplate<TransactionResponse> listResponseTemplate = new ListResponseTemplate<>(page, pageSize, transactionResponses);
    response.setData(listResponseTemplate);

    response.setStatus(status);
    return ResponseEntity.status(status).body(response);
  }

  @Operation(
    summary = "Get services transactions by id",
    description = "Allows services to view their transactions by id"
  )
  @GetMapping(value = "/{id}", produces = "application/json")
  public ResponseEntity<ResponseTemplate<TransactionResponse>> getTransactionsById(@PathVariable UUID id, @RequestAttribute String accountName) {

    ResponseTemplate<TransactionResponse> response = new ResponseTemplate<>();
    int status = HttpStatus.OK.value();
    
    Optional<Transaction> transactionOptional = this.transactionService.retrieveTransactionById(id, accountName);
    if (transactionOptional.isPresent()) {
      Transaction transaction = transactionOptional.get();
      TransactionResponse transactionResponse = TransactionUtils.transactionResponseMapper(transaction, accountName); // To be fixed
      
      response.setData(transactionResponse);
    } else {
      status = HttpStatus.NOT_FOUND.value();
      response.setMessage("Transaction not found with id: " + id);
    }

    response.setStatus(status);
    return ResponseEntity.status(status).body(response);
  }

  @Operation(
    summary = "Create transactions",
    description = "Allows services to create transactions"
  )
  @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
  public ResponseEntity<ResponseTemplate<ListResponseTemplate<TransactionResponse>>> postTransactions(@RequestBody TransactionsCreateRequest transactionsCreateRequest, @RequestAttribute String accountName) {
    
    ResponseTemplate<ListResponseTemplate<TransactionResponse>> response = new ResponseTemplate<>();
    int status = HttpStatus.OK.value();
    
    List<Transaction> transactions = transactionsCreateRequest.getTransactions().stream().map(
      (transactionsRequest) -> TransactionUtils.transactionMapper(transactionsRequest)
    ).collect(Collectors.toList());

    List<Transaction> savedTransactions = this.transactionService.saveTransactions(transactions, accountName);
    List<TransactionResponse> transactionResponses = savedTransactions.stream().map(
            (transaction) -> TransactionUtils.transactionResponseMapper(transaction, accountName)
          ).collect(Collectors.toList());
    
    ListResponseTemplate<TransactionResponse> listResponseTemplate = new ListResponseTemplate<>(1, transactionResponses.size(), transactionResponses);
    response.setData(listResponseTemplate);
    
    response.setStatus(status);
    return ResponseEntity.status(status).body(response);
  }
}