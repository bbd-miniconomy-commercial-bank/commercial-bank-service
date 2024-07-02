package com.miniconomy.commercial_bank_service.admin_portal.controller;

import org.springframework.web.bind.annotation.*;

import com.miniconomy.commercial_bank_service.admin_portal.entity.DebitOrder;
import com.miniconomy.commercial_bank_service.admin_portal.request.DebitOrderRequest;
import com.miniconomy.commercial_bank_service.admin_portal.request.DebitOrdersCreateRequest;
import com.miniconomy.commercial_bank_service.admin_portal.response.DebitOrderResponse;
import com.miniconomy.commercial_bank_service.admin_portal.response.ListResponseTemplate;
import com.miniconomy.commercial_bank_service.admin_portal.response.ResponseTemplate;
import com.miniconomy.commercial_bank_service.admin_portal.service.DebitOrderService;
import com.miniconomy.commercial_bank_service.admin_portal.utils.DebitOrderUtils;

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
  public ResponseEntity<ResponseTemplate<ListResponseTemplate<DebitOrderResponse>>> postDebitOrders(@RequestBody DebitOrdersCreateRequest dbOrders) {
    
    ResponseTemplate<ListResponseTemplate<DebitOrderResponse>> response = new ResponseTemplate<>();
    int status = HttpStatus.OK.value();

    List<DebitOrder> debitOrders = this.debitOrderService.saveDebitOrders(dbOrders.getDebitOrders());
    List<DebitOrderResponse> debitOrderResponses = debitOrders.stream().map(
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
  public ResponseEntity<ResponseTemplate<ListResponseTemplate<DebitOrderResponse>>> getDebitOrders(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize) {
    
    ResponseTemplate<ListResponseTemplate<DebitOrderResponse>> response = new ResponseTemplate<>();
    int status = HttpStatus.OK.value();

    UUID creditAccountId = UUID.fromString("3d807dc5-5a12-455c-9b66-6876906e70d6");
    Pageable pageable = PageRequest.of(page, pageSize);
    List<DebitOrderResponse> debitOrders = this.debitOrderService.retrieveDebitOrders(creditAccountId, pageable);
    
    ListResponseTemplate<DebitOrderResponse> listResponseTemplate = new ListResponseTemplate<>(page, pageSize, debitOrders);
    response.setData(listResponseTemplate);

    response.setStatus(status);
    return ResponseEntity.status(status).body(response);
  }

  @Operation(
    summary = "Get a specific debit order",
    description = "Retrieves the information for a specific debit order by its ID"
  )
  @GetMapping(value = "/{id}", produces = "application/json")
  public ResponseEntity<ResponseTemplate<DebitOrderResponse>> getDebitOrderById(@PathVariable UUID id) {
    
    ResponseTemplate<DebitOrderResponse> response = new ResponseTemplate<>();
    int status = HttpStatus.OK.value();

    Optional<DebitOrder> debitOrderOptional = debitOrderService.getDebitOrderById(id);
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
  public ResponseEntity<ResponseTemplate<DebitOrderResponse>> updateDebitOrder(@PathVariable UUID id, @RequestBody DebitOrderRequest body) {
    
    ResponseTemplate<DebitOrderResponse> response = new ResponseTemplate<>();
    int status = HttpStatus.OK.value();
    
    Optional<DebitOrder> updateDbOrder = debitOrderService.updateDebitOrder(id, body);
    if (updateDbOrder.isPresent()) {
      DebitOrder debitOrder = updateDbOrder.get();
      DebitOrderResponse debitOrderResponse = DebitOrderUtils.debitOrderResponseMapper(debitOrder);

      response.setData(debitOrderResponse);
    } else {
      status = HttpStatus.NOT_FOUND.value();
      response.setMessage("Debit order not found with id: " + id);
    }

    response.setStatus(status);
    return ResponseEntity.status(status).body(response);
  }
}