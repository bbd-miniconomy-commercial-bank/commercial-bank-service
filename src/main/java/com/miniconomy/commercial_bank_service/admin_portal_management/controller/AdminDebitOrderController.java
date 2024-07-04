package com.miniconomy.commercial_bank_service.admin_portal_management.controller;

import org.springframework.web.bind.annotation.*;

import com.miniconomy.commercial_bank_service.financial_management.entity.DebitOrder;
import com.miniconomy.commercial_bank_service.financial_management.response.*;
import com.miniconomy.commercial_bank_service.financial_management.service.DebitOrderService;
import com.miniconomy.commercial_bank_service.financial_management.utils.DebitOrderUtils;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/admin")
class AdminDebitOrderController {
    
  private final DebitOrderService debitOrderService;

  public AdminDebitOrderController(DebitOrderService debitOrderService) {
    this.debitOrderService = debitOrderService;
  }
  
  @GetMapping(value = "/debitOrders", produces = "application/json")
  public ResponseEntity<ResponseTemplate<ListResponseTemplate<DebitOrderResponse>>> getDebitOrders(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize) {
    
    ResponseTemplate<ListResponseTemplate<DebitOrderResponse>> response = new ResponseTemplate<>();
    int status = HttpStatus.OK.value();

    Pageable pageable = PageRequest.of(page, pageSize);
    List<DebitOrder> debitOrders = this.debitOrderService.retrieveAllDebitOrders(pageable);

    List<DebitOrderResponse> debitOrderResponses = debitOrders.stream().map(
      (debitOrder) -> DebitOrderUtils.debitOrderResponseMapper(debitOrder)
    ).collect(Collectors.toList());

    ListResponseTemplate<DebitOrderResponse> listResponseTemplate = new ListResponseTemplate<>(page, pageSize, debitOrderResponses);
    response.setData(listResponseTemplate);

    response.setStatus(status);
    return ResponseEntity.status(status).body(response);
  }
}