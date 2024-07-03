package com.miniconomy.commercial_bank_service.financial_management.request;

import com.miniconomy.commercial_bank_service.financial_management.enumeration.LoanTypeEnum;

import lombok.Data;

@Data
public class LoanRequest {
    private Long amount;
    private String accountName;
    private LoanTypeEnum type;
}
