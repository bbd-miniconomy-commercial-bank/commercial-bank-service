package com.miniconomy.commercial_bank_service.admin_portal_management.controller;

import org.springframework.web.bind.annotation.*;

import com.miniconomy.commercial_bank_service.financial_management.entity.Loan;
import com.miniconomy.commercial_bank_service.financial_management.response.ResponseTemplate;
import com.miniconomy.commercial_bank_service.financial_management.response.LoanResponse;
import com.miniconomy.commercial_bank_service.financial_management.response.ListResponseTemplate;
import com.miniconomy.commercial_bank_service.financial_management.service.LoanService;
import com.miniconomy.commercial_bank_service.financial_management.utils.LoanUtils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/admin")
public class AdminLoanController {

    final LoanService loanService;

    public AdminLoanController(LoanService loanService)
    {
        this.loanService = loanService;
    }

    @GetMapping(value = "/loans", produces = "application/json")
    public ResponseEntity<ResponseTemplate<ListResponseTemplate<LoanResponse>>> getAllLoans(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize) {

        ResponseTemplate<ListResponseTemplate<LoanResponse>> response = new ResponseTemplate<>();
        int status = HttpStatus.OK.value();

        Pageable pageable = PageRequest.of(page, pageSize);
        List<Loan> loans = loanService.retrieveAllLoans(pageable);

        List<LoanResponse> loanResponsesList = loans.stream().map(
            loan -> LoanUtils.loanResponseMappper(loan)
        ).collect(Collectors.toList());

        ListResponseTemplate<LoanResponse> listResponseTemplate = new ListResponseTemplate<>(page, pageSize, loanResponsesList);

        response.setData(listResponseTemplate);

        response.setStatus(status);
        return ResponseEntity.status(status).body(response);
    }    
}
