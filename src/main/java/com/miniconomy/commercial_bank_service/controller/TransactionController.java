package com.miniconomy.commercial_bank_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miniconomy.commercial_bank_service.entity.Transaction;
import com.miniconomy.commercial_bank_service.response.TransactionResponse;
import com.miniconomy.commercial_bank_service.response.BasicResponse;
import com.miniconomy.commercial_bank_service.service.TransactionService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

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
  public List<BasicResponse<TransactionResponse>> getTransactions(@RequestParam Long creditAccId)
  {
    List<Transaction> transactions = this.transactionService.retrieveTransactions(creditAccId);
    List<BasicResponse<TransactionResponse>> responseArray = new ArrayList<>();
    for(Transaction transaction: transactions)
    {
      TransactionResponse response = new TransactionResponse(transaction.getdebitAccId(), transaction.getcreditAccId(), transaction.getTransactionAmount(), transaction.getTransactionStatus(), transaction.getDebitRef(), transaction.getCreditRef(), transaction.getTransactionDate());
      responseArray.add(new BasicResponse<TransactionResponse>(response));
    }
    return responseArray;
  }

}