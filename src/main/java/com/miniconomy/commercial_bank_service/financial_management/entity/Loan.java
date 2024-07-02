package com.miniconomy.commercial_bank_service.financial_management.entity;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Loan {
    private UUID loanId;
    private String accountName;
    private long loanAmount;
    private LoanType loanType;
    private String loanCreatedDate; 
}
