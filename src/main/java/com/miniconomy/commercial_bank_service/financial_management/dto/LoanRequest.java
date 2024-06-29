package com.miniconomy.commercial_bank_service.financial_management.dto;

import com.miniconomy.commercial_bank_service.entity.LoanType;
import lombok.Data;

@Data
public class LoanRequest {
    private Long loanAmount;
    private String accountName;
    private String loanType;
}
