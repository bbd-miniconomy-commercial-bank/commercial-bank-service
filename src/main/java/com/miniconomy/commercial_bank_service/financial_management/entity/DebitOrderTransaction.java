package com.miniconomy.commercial_bank_service.financial_management.entity;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DebitOrderTransaction {
    private UUID debitOrderTransactionId;
    private UUID debitOrderId;
    private UUID transactionId;
    private UUID creditAccountId;
    private String creditAccountName;
    private UUID debitAccountId;
    private String debitAccountName;
    private String transactionDate;
    private Long transactionAmount;
    private String creditRef;
    private String debitRef;
    private String transactionStatus;
}
