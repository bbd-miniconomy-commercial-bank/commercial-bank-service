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
    private UUID creditAccountId;
    private UUID debitAccountId;
    private String debitOrderCreatedDate;
    private Long debitOrderAmount;
    private String debitOrderReceiverRef;
    private String debitOrderSenderRef;
    private boolean debitOrderDisabled;
}
