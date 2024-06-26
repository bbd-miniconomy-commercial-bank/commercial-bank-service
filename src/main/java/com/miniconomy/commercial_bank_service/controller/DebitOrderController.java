package com.miniconomy.commercial_bank_service.controller;

import org.springframework.web.bind.annotation.*;

import com.miniconomy.commercial_bank_service.dto.DebitOrderRequest;
import com.miniconomy.commercial_bank_service.dto.TransactionRequest;
import com.miniconomy.commercial_bank_service.entity.*;
import com.miniconomy.commercial_bank_service.response.DebitOrderResponse;
import com.miniconomy.commercial_bank_service.service.DebitOrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Debit Orders", description = "Queries related to service's debit orders")
@RestController
@RequestMapping("/debitOrders")
class DebitOrderController {
    
  private final DebitOrderService debitOrderService;

  public DebitOrderController(DebitOrderService debitOrderService) {
    this.debitOrderService = debitOrderService;
  }
  
  @Operation(
    summary = "Get services debit orders",
    description = "Allows services to view their debit orders"
  )
  @GetMapping(value = "", produces = "application/json")
  public ResponseEntity<?> getTransactions(@RequestParam UUID creditAccId) {//TODO: Remove param and use token to get id
    List<DebitOrder> debitOrders = this.debitOrderService.retrieveDebitOrders(creditAccId);
    if(debitOrders.size() > 0) {
      List<DebitOrderResponse> responseArray = new ArrayList<>();
      for(DebitOrder debitOrder: debitOrders) {
        DebitOrderResponse response = new DebitOrderResponse();
        response.setCreditAccountName(debitOrder.getCreditAccount().getAccountName()); 
        response.setDebitAccountName(debitOrder.getDebitAccount().getAccountName()); 
        response.setDebitOrderCreatedDate(debitOrder.getDebitOrderCreatedDate()); 
        response.setDebitOrderAmount(debitOrder.getDebitOrderAmount());
        response.setDebitOrderReceiverRef(debitOrder.getDebitOrderReceiverRef());
        response.setDebitOrderSenderRef(debitOrder.getDebitOrderSenderRef());
        responseArray.add(response);
      }
      return ResponseEntity.status(HttpStatus.OK).body(responseArray);
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No debit orders found");
  }

  @Operation(
    summary = "Create transactions",
    description = "Allows services to create transactions"
  )
  @PostMapping(value = "/debitOrders/create", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> postTransactions(@RequestBody List<DebitOrderRequest> dbOrders) {
    List<DebitOrder> dbos = this.debitOrderService.saveDebitOrders(dbOrders);
    return ResponseEntity.status(HttpStatus.OK).body(dbos);
  }
}