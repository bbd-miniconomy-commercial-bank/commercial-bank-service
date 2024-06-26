package com.miniconomy.commercial_bank_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miniconomy.commercial_bank_service.entity.DebitOrder;
import com.miniconomy.commercial_bank_service.response.DebitOrderResponse;
import com.miniconomy.commercial_bank_service.service.DebitOrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
  public ResponseEntity<?> getTransactions(@RequestParam Long creditAccId, @RequestParam Integer page, @RequestParam Integer pageSize) //TODO: Remove param and use token to get id
  {
    List<DebitOrder> debitOrders = this.debitOrderService.retrieveDebitOrders(creditAccId);
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