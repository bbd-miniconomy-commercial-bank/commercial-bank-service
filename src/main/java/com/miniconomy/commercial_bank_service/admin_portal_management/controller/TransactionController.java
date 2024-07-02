package com.miniconomy.commercial_bank_service.admin_portal_management.controller;

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

@RestController
@RequestMapping("/admin")
class TransactionController {
    
  private final TransactionService transactionService;

  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }
  
  @GetMapping(value = "/transactions", produces = "application/json")
  public ResponseEntity<ResponseTemplate<ListResponseTemplate<TransactionResponse>>> getAllTransactions(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize)
  {
    
    ResponseTemplate<ListResponseTemplate<TransactionResponse>> response = new ResponseTemplate<>();
    int status = HttpStatus.OK.value();

    Pageable pageable = PageRequest.of(page, pageSize);
    List<Transaction> transactions = this.transactionService.retrieveAllTransactions(pageable);
    List<TransactionResponse> transactionResponses = transactions.stream().map(
            (transaction) -> TransactionUtils.transactionResponseMapper(transaction, transaction.getDebitAccountName())
          ).collect(Collectors.toList());

    ListResponseTemplate<TransactionResponse> listResponseTemplate = new ListResponseTemplate<>(page, pageSize, transactionResponses);
    response.setData(listResponseTemplate);

    response.setStatus(status);
    return ResponseEntity.status(status).body(response);
  }

}