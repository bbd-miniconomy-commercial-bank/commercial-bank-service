package com.miniconomy.commercial_bank_service.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TransactionRequest {
    private String debitAccountName;
    private Long transactionAmount;
    private String creditRef;
    private String debitRef;
}
