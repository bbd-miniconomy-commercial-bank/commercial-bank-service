package com.miniconomy.commercial_bank_service.financial_management.controller;

import org.springframework.web.bind.annotation.*;

import com.miniconomy.commercial_bank_service.financial_management.entity.DebitOrder;
import com.miniconomy.commercial_bank_service.financial_management.request.DebitOrderRequest;
import com.miniconomy.commercial_bank_service.financial_management.request.DebitOrdersCreateRequest;
import com.miniconomy.commercial_bank_service.financial_management.response.DebitOrderResponse;
import com.miniconomy.commercial_bank_service.financial_management.response.ListResponseTemplate;
import com.miniconomy.commercial_bank_service.financial_management.response.ResponseTemplate;
import com.miniconomy.commercial_bank_service.financial_management.service.DebitOrderService;
import com.miniconomy.commercial_bank_service.financial_management.utils.DebitOrderUtils;

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

@Tag(name = "Debit Orders", description = "Queries related to service's debit orders")
@RestController
@RequestMapping("/debitOrders")
public class DebitOrderController {
    
  private final DebitOrderService debitOrderService;

  public DebitOrderController(DebitOrderService debitOrderService) {
    this.debitOrderService = debitOrderService;
  }

  @Operation(
    summary = "Create debitOrders",
    description = "Allows services to create debit orders"
  )
  @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
  public ResponseEntity<ResponseTemplate<ListResponseTemplate<DebitOrderResponse>>> postDebitOrders(@RequestBody DebitOrdersCreateRequest debitOrderCreateRequest, @RequestAttribute String accountName) {
    
    ResponseTemplate<ListResponseTemplate<DebitOrderResponse>> response = new ResponseTemplate<>();
    int status = HttpStatus.OK.value();

    List<DebitOrder> debitOrders = debitOrderCreateRequest.getDebitOrders().stream().map(
      (debitOrderRequest) -> DebitOrderUtils.debitOrderMapper(debitOrderRequest)
    ).collect(Collectors.toList());

    List<DebitOrder> createdDebitOrders = this.debitOrderService.saveDebitOrders(debitOrders, accountName);
    List<DebitOrderResponse> debitOrderResponses = createdDebitOrders.stream().map(
      (debitOrder) -> DebitOrderUtils.debitOrderResponseMapper(debitOrder)
    ).collect(Collectors.toList());

    ListResponseTemplate<DebitOrderResponse> listResponseTemplate = new ListResponseTemplate<>(1, debitOrderResponses.size(), debitOrderResponses);
    response.setData(listResponseTemplate);
    
    response.setStatus(status);
    return ResponseEntity.status(status).body(response);
  }
  
  @Operation(
    summary = "Get services debit orders",
    description = "Allows services to view their debit orders"
  )
  @GetMapping(value = "", produces = "application/json")
  public ResponseEntity<ResponseTemplate<ListResponseTemplate<DebitOrderResponse>>> getDebitOrders(
        @RequestParam(defaultValue = "1") @Positive(message = "page must be greater than or equal to 1.") int page, 
        @RequestParam(defaultValue = "10") @Positive(message = "pageSize must be greater than or equal to 1")  @Max(value = 25, message = "Maximum pageSize is 25") int pageSize, 
        @RequestAttribute String accountName
  ) {
    
    ResponseTemplate<ListResponseTemplate<DebitOrderResponse>> response = new ResponseTemplate<>();
    int status = HttpStatus.OK.value();

    Pageable pageable = PageRequest.of(page - 1, pageSize);
    List<DebitOrder> debitOrders = this.debitOrderService.retrieveDebitOrders(accountName, pageable);

    List<DebitOrderResponse> debitOrderResponses = debitOrders.stream().map(
      (debitOrder) -> DebitOrderUtils.debitOrderResponseMapper(debitOrder)
    ).collect(Collectors.toList());

    
    ListResponseTemplate<DebitOrderResponse> listResponseTemplate = new ListResponseTemplate<>(page, pageSize, debitOrderResponses);
    response.setData(listResponseTemplate);

    response.setStatus(status);
    return ResponseEntity.status(status).body(response);
  }

  @Operation(
    summary = "Get a specific debit order",
    description = "Retrieves the information for a specific debit order by its ID"
  )
  @GetMapping(value = "/{id}", produces = "application/json")
  public ResponseEntity<ResponseTemplate<DebitOrderResponse>> getDebitOrderById(@PathVariable UUID id, @RequestAttribute String accountName) {
    
    ResponseTemplate<DebitOrderResponse> response = new ResponseTemplate<>();
    int status = HttpStatus.OK.value();

    Optional<DebitOrder> debitOrderOptional = debitOrderService.getDebitOrderById(id, accountName);
    if (debitOrderOptional.isPresent()) {
      DebitOrder debitOrder = debitOrderOptional.get();
      DebitOrderResponse debitOrderResponse = DebitOrderUtils.debitOrderResponseMapper(debitOrder);

      response.setData(debitOrderResponse);
    } else {
      status = HttpStatus.NOT_FOUND.value();
      response.setMessage("Debit order not found with id: " + id);
    }

    response.setStatus(status);
    return ResponseEntity.status(status).body(response);
  }

  @Operation(
    summary = "Update a specific debit order",
    description = "Updates the information for a specific debit order by its ID"
  )
  @PutMapping(value = "/{id}", produces = "application/json")
  public ResponseEntity<ResponseTemplate<DebitOrderResponse>> updateDebitOrder(@PathVariable UUID id, @RequestBody DebitOrderRequest debitOrderRequest, @RequestAttribute String accountName) {
    
    ResponseTemplate<DebitOrderResponse> response = new ResponseTemplate<>();
    int status = HttpStatus.OK.value();

    DebitOrder debitOrder = DebitOrderUtils.debitOrderMapper(debitOrderRequest);
    
    Optional<DebitOrder> updateDbOrderOptional = debitOrderService.updateDebitOrder(id, debitOrder, accountName);
    if (updateDbOrderOptional.isPresent()) {
      DebitOrder updatedDebitOrder = updateDbOrderOptional.get();
      DebitOrderResponse debitOrderResponse = DebitOrderUtils.debitOrderResponseMapper(updatedDebitOrder);

      response.setData(debitOrderResponse);
    } else {
      status = HttpStatus.NOT_FOUND.value();
      response.setMessage("Debit order not found with id: " + id);
    }

    response.setStatus(status);
    return ResponseEntity.status(status).body(response);
  }
}