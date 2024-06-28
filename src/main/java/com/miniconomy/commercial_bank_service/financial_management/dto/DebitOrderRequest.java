package com.miniconomy.commercial_bank_service.financial_management.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class DebitOrderRequest {
    private UUID debitOrderId;
    private String creditAccountName;
    private String debitAccountName;
    private String debitOrderCreatedDate;
    private Long debitOrderAmount;
    private String debitOrderReceiverRef;
    private String debitOrderSenderRef;
    private boolean debitOrderDisabled;
}
