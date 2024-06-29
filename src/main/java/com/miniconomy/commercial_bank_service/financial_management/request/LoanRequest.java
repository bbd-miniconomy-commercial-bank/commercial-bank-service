package com.miniconomy.commercial_bank_service.financial_management.request;

import com.miniconomy.commercial_bank_service.financial_management.entity.LoanType;
import lombok.Data;

@Data
public class LoanRequest {
    private Long loanAmount;
    private String accountName;
    private LoanType loanType;
}
