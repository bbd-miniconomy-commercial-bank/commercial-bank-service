package com.miniconomy.commercial_bank_service.controller;

import org.springframework.web.bind.annotation.*;

import com.miniconomy.commercial_bank_service.dto.DebitOrderRequest;
import com.miniconomy.commercial_bank_service.dto.TransactionRequest;
import com.miniconomy.commercial_bank_service.entity.*;
import com.miniconomy.commercial_bank_service.response.DebitOrderResponse;
import com.miniconomy.commercial_bank_service.service.DebitOrderService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    summary = "Create debitOrders",
    description = "Allows services to create debit orders"
  )
  @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> postDebitOrders(@RequestBody List<DebitOrderRequest> dbOrders) {
    List<DebitOrderResponse> dbos = this.debitOrderService.saveDebitOrders(dbOrders);
    return ResponseEntity.status(HttpStatus.OK).body(dbos);
  }
  
  @Operation(
    summary = "Get services debit orders",
    description = "Allows services to view their debit orders"
  )
  @GetMapping(value = "", produces = "application/json")
  public ResponseEntity<?> getDebitOrders(@PageableDefault(size = 10) Pageable pageable)
  {
    UUID creditAccountId = UUID.fromString("3d807dc5-5a12-455c-9b66-6876906e70d6");
    List<DebitOrderResponse> debitOrders = this.debitOrderService.retrieveDebitOrders(creditAccountId, pageable);
    if(debitOrders.size() > 0) {
      return new ResponseEntity<>(debitOrders, HttpStatus.OK);
    }
    else {
      return new ResponseEntity<>("No debit orders found", HttpStatus.NOT_FOUND);
    }
  }

  @Operation(
    summary = "Disable a debit order",
    description = "Disables a specific debit order by its ID"
  )
  @DeleteMapping(value = "/{id}", produces = "application/json")
  public ResponseEntity<?> disableDebitOrder(@PathVariable UUID id) {
    boolean isDisabled = debitOrderService.disableDebitOrder(id);
    if (isDisabled) {
      return new ResponseEntity<>("Disabled debit order", HttpStatus.OK);
    } 
    return new ResponseEntity<>("Debit order not found", HttpStatus.NOT_FOUND);
  }

  @Operation(
    summary = "Get a specific debit order",
    description = "Retrieves the information for a specific debit order by its ID"
  )
  @GetMapping(value = "/{id}", produces = "application/json")
  public ResponseEntity<?> getDebitOrderById(@PathVariable UUID id) {
    Optional<DebitOrder> debitOrderOptional = debitOrderService.getDebitOrderById(id);
    if (debitOrderOptional.isPresent()) {
      DebitOrder debitOrder = debitOrderOptional.get();
      DebitOrderResponse response = new DebitOrderResponse(
        debitOrder.getDebitOrderId(),
        debitOrder.getCreditAccount().getAccountName(),
        debitOrder.getDebitAccount().getAccountName(),
        debitOrder.getDebitOrderCreatedDate(),
        debitOrder.getDebitOrderAmount(),
        debitOrder.getDebitOrderReceiverRef(),
        debitOrder.getDebitOrderSenderRef(),
        debitOrder.isDebitOrderDisabled()
      );
      return new ResponseEntity<>(response, HttpStatus.OK);
    }
    return new ResponseEntity<>("Debit order not found", HttpStatus.NOT_FOUND);
  }

  @Operation(
    summary = "Update a specific debit order",
    description = "Updates the information for a specific debit order by its ID"
  )
  @PutMapping(value = "/{id}", produces = "application/json")
  public ResponseEntity<?> updateDebitOrder(@PathVariable UUID id, @RequestBody DebitOrderRequest body) {
    Optional<DebitOrder> updateDbOrder = debitOrderService.updateDebitOrder(id, body);
    if (updateDbOrder.isPresent()) {
      DebitOrder debitOrder = updateDbOrder.get();
      DebitOrderResponse response = new DebitOrderResponse(
        debitOrder.getDebitOrderId(),
        debitOrder.getCreditAccount().getAccountName(),
        debitOrder.getDebitAccount().getAccountName(),
        debitOrder.getDebitOrderCreatedDate(),
        debitOrder.getDebitOrderAmount(),
        debitOrder.getDebitOrderReceiverRef(),
        debitOrder.getDebitOrderSenderRef(),
        debitOrder.isDebitOrderDisabled()
      );
      return new ResponseEntity<>(response, HttpStatus.OK);
    } 
    return new ResponseEntity<>("Debit order not found", HttpStatus.NOT_FOUND);
  }
}