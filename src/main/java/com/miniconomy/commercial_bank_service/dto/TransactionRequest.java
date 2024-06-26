package com.miniconomy.commercial_bank_service.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;

@Data
public class TransactionRequest {
    private UUID debitAccountId;
    private BigDecimal transactionAmount;
    private String creditRef;
    private String debitRef;
}
