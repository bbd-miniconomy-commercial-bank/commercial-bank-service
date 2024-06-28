package com.miniconomy.commercial_bank_service.financial_management.dto;

import lombok.Data;

@Data
public class TransactionRequest {
    private String debitAccountName;
    private Long transactionAmount;
    private String creditRef;
    private String debitRef;
}
