package com.miniconomy.commercial_bank_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miniconomy.commercial_bank_service.entity.DebitOrder;
import com.miniconomy.commercial_bank_service.response.DebitOrderResponse;
import com.miniconomy.commercial_bank_service.service.DebitOrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Debit Orders", description = "Queries related to service's debit orders")
@RestController
@RequestMapping("/debitOrders")
class DebitOrderController {
    
  final DebitOrderService debitOrderService;

  public DebitOrderController(DebitOrderService debitOrderService)
  {
    this.debitOrderService = debitOrderService;
  }
  
  @Operation(
    summary = "Get services debit orders",
    description = "Allows services to view their debit orders"
  )
  @GetMapping(value = "", produces = "application/json")
  public ResponseEntity<?> getTransactions(@PageableDefault(size = 10) Pageable pageable)
  {
    UUID creditAccountId = UUID.fromString("3d807dc5-5a12-455c-9b66-6876906e70d6");
    List<DebitOrder> debitOrders = this.debitOrderService.retrieveDebitOrders(creditAccountId, pageable);
    if(debitOrders.size() > 0)
    {
      List<DebitOrderResponse> responseArray = new ArrayList<>();
      for(DebitOrder debitOrder: debitOrders)
      {
        DebitOrderResponse response = new DebitOrderResponse(debitOrder.getCreditAccountId(), debitOrder.getDebitAccountId(), debitOrder.getDebitOrderCreatedDate(), debitOrder.getDebitOrderAmount(), debitOrder.getDebitOrderReceiverRef(), debitOrder.getDebitOrderSenderRef());
        responseArray.add(response);
      }
      return new ResponseEntity<>(responseArray, HttpStatus.OK);
    }
    else
    {
      return new ResponseEntity<>("No debit orders found", HttpStatus.NOT_FOUND);
    }
  }
}