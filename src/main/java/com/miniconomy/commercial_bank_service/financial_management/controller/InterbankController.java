package com.miniconomy.commercial_bank_service.financial_management.controller;

import org.springframework.web.bind.annotation.RestController;

import com.miniconomy.commercial_bank_service.financial_management.entity.InterbankTransaction;
import com.miniconomy.commercial_bank_service.financial_management.request.InterbankCallbackRequest;
import com.miniconomy.commercial_bank_service.financial_management.request.InterbankDepositCreateRequest;
import com.miniconomy.commercial_bank_service.financial_management.response.InterbankDepositResponse;
import com.miniconomy.commercial_bank_service.financial_management.response.ListResponseTemplate;
import com.miniconomy.commercial_bank_service.financial_management.response.ResponseTemplate;
import com.miniconomy.commercial_bank_service.financial_management.service.InterbankService;
import com.miniconomy.commercial_bank_service.financial_management.utils.InterbankUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Interbank", description = "Queries related to interbank transactions")
@RestController
@RequestMapping("/interbank")
public class InterbankController {
    
  private final InterbankService interbankService;

  public InterbankController(InterbankService interbankService) {
    this.interbankService = interbankService;
  }

  @Operation(
    summary = "Create deposits",
    description = "Allows extrenal banks to create deposits"
  )
  @PostMapping(value = "/deposit", consumes = "application/json", produces = "application/json")
  public ResponseEntity<ResponseTemplate<ListResponseTemplate<InterbankDepositResponse>>> postDeposit(@RequestBody InterbankDepositCreateRequest interbankDepositCreateRequest, @RequestAttribute String accountName) {
    
    ResponseTemplate<ListResponseTemplate<InterbankDepositResponse>> response = new ResponseTemplate<>();
    int status = HttpStatus.OK.value();
    
    List<InterbankTransaction> interbankTransactions = this.interbankService.processDeposits(interbankDepositCreateRequest.getDeposits(), accountName);
    List<InterbankDepositResponse> interbankDepositResponses = interbankTransactions.stream().map(
            (deposit) -> InterbankUtils.interbankDepositResponseMapper(deposit)
          ).collect(Collectors.toList());
    
    ListResponseTemplate<InterbankDepositResponse> listResponseTemplate = new ListResponseTemplate<>(1, interbankDepositResponses.size(), interbankDepositResponses);
    response.setData(listResponseTemplate);
    
    response.setStatus(status);
    return ResponseEntity.status(status).body(response);
  }

  @Operation(
    summary = "Create deposits",
    description = "Allows extrenal banks to create deposits"
  )
  @PostMapping(value = "/callback", consumes = "application/json", produces = "application/json")
  public ResponseEntity<ResponseTemplate<Object>> postCallback(@RequestBody InterbankCallbackRequest interbankCallbackRequest, @RequestAttribute String accountName) {
    
    ResponseTemplate<Object> response = new ResponseTemplate<>();
    int status = HttpStatus.OK.value();
    
    // ADD IMPLEMENTATION
    
    response.setStatus(status);
    return ResponseEntity.status(status).body(response);
  }
}
