package com.miniconomy.commercial_bank_service.financial_management.controller;

import org.springframework.web.bind.annotation.*;

import com.miniconomy.commercial_bank_service.financial_management.entity.Loan;
import com.miniconomy.commercial_bank_service.financial_management.request.LoanRequest;
import com.miniconomy.commercial_bank_service.financial_management.response.ResponseTemplate;
import com.miniconomy.commercial_bank_service.financial_management.response.LoanResponse;
import com.miniconomy.commercial_bank_service.financial_management.response.ListResponseTemplate;
import com.miniconomy.commercial_bank_service.financial_management.service.LoanService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

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
    public ResponseEntity<ResponseTemplate<LoanResponse>> createLoan(@RequestBody LoanRequest loan) {

        ResponseTemplate<LoanResponse> response = new ResponseTemplate<>();
        int status = HttpStatus.OK.value();

        Optional<Loan> createdLoan = loanService.createLoan(loan);

        if (createdLoan.isPresent()) {
            LoanResponse loanResponse = new LoanResponse(
                    createdLoan.get().getLoanId(),
                    createdLoan.get().getLoanAmount(),
                    createdLoan.get().getLoanType(),
                    createdLoan.get().getAccountId().toString()
            );
            
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
    public ResponseEntity<ResponseTemplate<LoanResponse>> getLoanById(@PathVariable UUID id) {
        
        ResponseTemplate<LoanResponse> response = new ResponseTemplate<>();
        int status = HttpStatus.OK.value();

        Optional<Loan> loan = loanService.getLoanById(id);
        
        if (loan.isPresent()) {
            LoanResponse loanResponse = new LoanResponse(
                loan.get().getLoanId(),
                loan.get().getLoanAmount(),
                loan.get().getLoanType(),
                loan.get().getAccountId().toString()
            );
            
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
    public ResponseEntity<ResponseTemplate<ListResponseTemplate<LoanResponse>>> getAllLoans(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize) {

        ResponseTemplate<ListResponseTemplate<LoanResponse>> response = new ResponseTemplate<>();
        int status = HttpStatus.OK.value();

        if (pageSize > 25) {
            pageSize = 25;
        }

        List<Loan> loans = loanService.getAllLoans();

        List<LoanResponse> loanResponsesList = loans.stream().map(
            loan -> new LoanResponse(
                loan.getLoanId(),
                loan.getLoanAmount(),
                loan.getLoanType(),
                loan.getAccountId().toString()
            )).collect(Collectors.toList());

        ListResponseTemplate<LoanResponse> listResponseTemplate = new ListResponseTemplate<>(page, pageSize, loanResponsesList);

        response.setData(listResponseTemplate);

        response.setStatus(status);
        return ResponseEntity.status(status).body(response);
    }    
}
