package com.miniconomy.commercial_bank_service.dto;

import lombok.Data;

@Data
public class TransactionRequest {
    private Long debitAccId;
    private Double transactionAmount;
    private String creditRef;
    private String debitRef;
}
