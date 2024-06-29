package com.miniconomy.commercial_bank_service.financial_management.controller;

import org.springframework.web.bind.annotation.*;

import com.miniconomy.commercial_bank_service.financial_management.dto.LoanRequest;
import com.miniconomy.commercial_bank_service.financial_management.entity.Loan;
import com.miniconomy.commercial_bank_service.financial_management.response.BasicResponse;
import com.miniconomy.commercial_bank_service.financial_management.response.LoanResponse;
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
    @PostMapping(value = "/apply", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createLoan(@RequestBody LoanRequest loan) {
        Optional<Loan> createdLoan = loanService.createLoan(loan);
        if (createdLoan.isPresent()) {
            LoanResponse response = new LoanResponse(
                    createdLoan.get().getLoanId(),
                    createdLoan.get().getLoanAmount(),
                    createdLoan.get().getLoanType(),
                    createdLoan.get().getAccount().getAccountName()
                    //createdLoan.get().getLoanInterests().stream().map(interest -> new LoanInterestResponse(interest.getLoanInterestId(), interest.getLoanInterestRate(), interest.getLoanInterestAmount(), interest.getLoanInterestDate())).collect(Collectors.toSet()),
                    //createdLoan.get().getLoanTransactions().stream().map(transaction -> new LoanTransactionResponse(transaction.getLoanTransactionId(), transaction.getTransactionId())).collect(Collectors.toSet())
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Your request contains incorrect details");
    }

    @Operation(
        summary = "Get loan details by ID", 
        description = "Retrieve loan details by loan ID"
    )
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> getLoanById(@PathVariable UUID id) {
        System.out.println(id);
        Optional<Loan> loan = loanService.getLoanById(id);
        System.out.println(loan.get());
        if (loan.isPresent()) {
            LoanResponse response = new LoanResponse(
                loan.get().getLoanId(),
                loan.get().getLoanAmount(),
                loan.get().getLoanType(),
                loan.get().getAccount().getAccountName()
                //loan.get().getLoanInterests().stream().map(interest -> new LoanInterestResponse(interest.getLoanInterestId(), interest.getLoanInterestRate(), interest.getLoanInterestAmount(), interest.getLoanInterestDate())).collect(Collectors.toSet()),
                //loan.get().getLoanTransactions().stream().map(transaction -> new LoanTransactionResponse(transaction.getLoanTransactionId(), transaction.getTransactionId())).collect(Collectors.toSet())
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Your request contains incorrect details");
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
        List<LoanResponse> responses = loans.stream().map(
            loan -> new LoanResponse(
                loan.getLoanId(),
                loan.getLoanAmount(),
                loan.getLoanType(),
                loan.getAccount().getAccountName()
                //loan.getLoanInterests().stream().map(interest -> new LoanInterestResponse(interest.getLoanInterestId(), interest.getLoanInterestRate(), interest.getLoanInterestAmount(), interest.getLoanInterestDate())).collect(Collectors.toSet()),
                //loan.getLoanTransactions().stream().map(transaction -> new LoanTransactionResponse(transaction.getLoanTransactionId(), transaction.getTransactionId())).collect(Collectors.toSet())
            )).collect(Collectors.toList());
        return new BasicResponse<List<LoanResponse>>(responses);
    }    
}
