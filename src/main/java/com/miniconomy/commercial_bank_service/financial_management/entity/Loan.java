package com.miniconomy.commercial_bank_service.financial_management.entity;

import com.miniconomy.commercial_bank_service.financial_management.enumeration.LoanTypeEnum;

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
    private LoanTypeEnum loanType;
    private String loanCreatedDate; 
}
