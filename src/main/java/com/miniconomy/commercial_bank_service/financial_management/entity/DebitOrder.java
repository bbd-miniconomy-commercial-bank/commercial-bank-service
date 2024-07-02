package com.miniconomy.commercial_bank_service.financial_management.entity;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DebitOrder {
    private UUID debitOrderId;
    private String creditAccountName;
    private String debitAccountName;
    private String debitOrderDebitRef;
    private String debitOrderCreditRef;
    private Long debitOrderAmount;
    private String debitOrderCreatedDate;
    private boolean debitOrderDisabled;
}
