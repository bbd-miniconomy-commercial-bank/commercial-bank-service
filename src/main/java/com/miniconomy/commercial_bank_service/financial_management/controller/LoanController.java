package com.miniconomy.commercial_bank_service.financial_management.controller;

import org.springframework.web.bind.annotation.*;

import com.miniconomy.commercial_bank_service.financial_management.entity.Loan;
import com.miniconomy.commercial_bank_service.financial_management.request.LoanRequest;
import com.miniconomy.commercial_bank_service.financial_management.response.ResponseTemplate;
import com.miniconomy.commercial_bank_service.financial_management.response.LoanResponse;
import com.miniconomy.commercial_bank_service.financial_management.response.ListResponseTemplate;
import com.miniconomy.commercial_bank_service.financial_management.service.LoanService;
import com.miniconomy.commercial_bank_service.financial_management.utils.LoanUtils;

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

@Tag(
    name = "Loans", 
    description = "Queries related to service's loans"
)
@RestController
@RequestMapping("/loans")
public class LoanController {

    final LoanService loanService;

    public LoanController(LoanService loanService)
    {
        this.loanService = loanService;
    }

    @Operation(
        summary = "Create a new loan",
        description = "Create a new loan for an account"
    )
    @PostMapping(value = "/apply", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ResponseTemplate<LoanResponse>> createLoan(@RequestBody LoanRequest loanRequest, @RequestAttribute String accountName) {

        ResponseTemplate<LoanResponse> response = new ResponseTemplate<>();
        int status = HttpStatus.OK.value();

        Loan loan = LoanUtils.loanMappper(loanRequest);
        Optional<Loan> createdLoanOptional = loanService.createLoan(loan, accountName);

        if (createdLoanOptional.isPresent()) {
            Loan createdLoan = createdLoanOptional.get();
            LoanResponse loanResponse = LoanUtils.loanResponseMappper(createdLoan);
            
            response.setData(loanResponse);
        } else {
            status = HttpStatus.BAD_REQUEST.value();
            response.setMessage("Required fields not set correctly in request body");
        }

        response.setStatus(status);
        return ResponseEntity.status(status).body(response);
    }

    @Operation(
        summary = "Get loan details by ID", 
        description = "Retrieve loan details by loan ID"
    )
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ResponseTemplate<LoanResponse>> getLoanById(@PathVariable UUID id, @RequestAttribute String accountName) {
        
        ResponseTemplate<LoanResponse> response = new ResponseTemplate<>();
        int status = HttpStatus.OK.value();

        Optional<Loan> loanOptional = loanService.retrieveLoanById(id, accountName);
        
        if (loanOptional.isPresent()) {
            Loan loan = loanOptional.get();
            LoanResponse loanResponse = LoanUtils.loanResponseMappper(loan);
            
            response.setData(loanResponse);
        } else {
            status = HttpStatus.NOT_FOUND.value();
            response.setMessage("Loan not found with id: " + id);
        }

        response.setStatus(status);
        return ResponseEntity.status(status).body(response);
    }

    @Operation(
        summary = "Get all loans", 
        description = "Retrieve all loans"
    )
    @GetMapping(
        value = "", 
        produces = "application/json"
    )
    public ResponseEntity<ResponseTemplate<ListResponseTemplate<LoanResponse>>> getAllLoans(
        @RequestParam(defaultValue = "1") @Positive(message = "page must be greater than or equal to 1.") int page, 
        @RequestParam(defaultValue = "10") @Positive(message = "pageSize must be greater than or equal to 1")  @Max(value = 25, message = "Maximum pageSize is 25") int pageSize, 
        @RequestAttribute String accountName
    ) {

        ResponseTemplate<ListResponseTemplate<LoanResponse>> response = new ResponseTemplate<>();
        int status = HttpStatus.OK.value();

        Pageable pageable = PageRequest.of(page - 1, pageSize);
        List<Loan> loans = loanService.retrieveAccountLoans(accountName, pageable);

        List<LoanResponse> loanResponsesList = loans.stream().map(
            loan -> LoanUtils.loanResponseMappper(loan)
        ).collect(Collectors.toList());

        ListResponseTemplate<LoanResponse> listResponseTemplate = new ListResponseTemplate<>(page, pageSize, loanResponsesList);

        response.setData(listResponseTemplate);

        response.setStatus(status);
        return ResponseEntity.status(status).body(response);
    }    
}
