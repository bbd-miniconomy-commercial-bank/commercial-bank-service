package com.miniconomy.commercial_bank_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miniconomy.commercial_bank_service.entity.Loan;
import com.miniconomy.commercial_bank_service.response.LoanResponse;
import com.miniconomy.commercial_bank_service.response.LoanTransactionResponse;
import com.miniconomy.commercial_bank_service.response.BasicResponse;
import com.miniconomy.commercial_bank_service.response.LoanInterestResponse;
import com.miniconomy.commercial_bank_service.service.LoanService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
    name = "Loan", 
    description = "Queries related to service's loans"
)
@RestController
@RequestMapping("/loan")
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
    @PostMapping(
        value = "/apply",
        produces = "application/json"
    )
     public BasicResponse<LoanResponse> createLoan(@RequestParam Loan loan) {
        Loan createdLoan = loanService.createLoan(loan);
        LoanResponse response = new LoanResponse(
                createdLoan.getLoanId(),
                createdLoan.getLoanAmount(),
                createdLoan.getLoanType(),
                createdLoan.getAccount().getAccountName(),
                createdLoan.getLoanInterests().stream().map(interest -> new LoanInterestResponse(interest.getLoanInterestId(), interest.getLoanInterestRate(), interest.getLoanInterestAmount(), interest.getLoanInterestDate())).collect(Collectors.toSet()),
                createdLoan.getLoanTransactions().stream().map(transaction -> new LoanTransactionResponse(transaction.getLoanTransactionId(), transaction.getTransactionId())).collect(Collectors.toSet())
        );
        return new BasicResponse<LoanResponse>(response);
    }

    @Operation(
        summary = "Get loan details by ID", 
        description = "Retrieve loan details by loan ID"
    )
    @GetMapping(
        value = "/{loanId}", 
        produces = "application/json"
    )
    public BasicResponse<LoanResponse> getLoanById(@RequestBody UUID loanId) {
        Loan loan = loanService.getLoanById(loanId);
        LoanResponse response = new LoanResponse(
                loan.getLoanId(),
                loan.getLoanAmount(),
                loan.getLoanType(),
                loan.getAccount().getAccountName(),
                loan.getLoanInterests().stream().map(interest -> new LoanInterestResponse(interest.getLoanInterestId(), interest.getLoanInterestRate(), interest.getLoanInterestAmount(), interest.getLoanInterestDate())).collect(Collectors.toSet()),
                loan.getLoanTransactions().stream().map(transaction -> new LoanTransactionResponse(transaction.getLoanTransactionId(), transaction.getTransactionId())).collect(Collectors.toSet())
        );
        return new BasicResponse<LoanResponse>(response);
    }

    @Operation(
        summary = "Get all loans", 
        description = "Retrieve all loans"
    )
    @GetMapping(
        value = "/all", 
        produces = "application/json"
    )
    public BasicResponse<List<LoanResponse>> getAllLoans() {
        List<Loan> loans = loanService.getAllLoans();
        List<LoanResponse> responses = loans.stream().map(loan -> new LoanResponse(
                loan.getLoanId(),
                loan.getLoanAmount(),
                loan.getLoanType(),
                loan.getAccount().getAccountName(),
                loan.getLoanInterests().stream().map(interest -> new LoanInterestResponse(interest.getLoanInterestId(), interest.getLoanInterestRate(), interest.getLoanInterestAmount(), interest.getLoanInterestDate())).collect(Collectors.toSet()),
                loan.getLoanTransactions().stream().map(transaction -> new LoanTransactionResponse(transaction.getLoanTransactionId(), transaction.getTransactionId())).collect(Collectors.toSet())
        )).collect(Collectors.toList());
        return new BasicResponse<List<LoanResponse>>(responses);
    }    
}
