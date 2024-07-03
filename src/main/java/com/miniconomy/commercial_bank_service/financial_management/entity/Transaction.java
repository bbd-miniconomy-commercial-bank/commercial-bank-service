package com.miniconomy.commercial_bank_service.financial_management.entity;

import com.miniconomy.commercial_bank_service.financial_management.enumeration.TransactionStatusEnum;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    private UUID transactionId;
    private String debitAccountName;
    private String creditAccountName;
    private String transactionDebitRef;
    private String transactionCreditRef;
    private long transactionAmount;
    private String transactionDate;
    private TransactionStatusEnum transactionStatus;
}
