package com.miniconomy.commercial_bank_service.financial_management.request;

import lombok.Data;

@Data
public class TransactionRequest {
    private String debitAccountName;
    private String creditAccountName;
    private String debitAccountName;
    private Long amount;
    private String debitRef;
    private String creditRef;
}
