package com.miniconomy.commercial_bank_service.financial_management.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class DebitOrderRequest {
    private String creditAccountName;
    private String debitAccountName;
    private Long debitOrderAmount;
    private String debitOrderReceiverRef;
    private String debitOrderSenderRef;
}
