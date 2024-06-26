package com.miniconomy.commercial_bank_service.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DebitOrderRequest {
    private String debitAccountName;
    private String debitOrderCreatedDate;
    private BigDecimal debitOrderAmount;
    private String debitOrderReceiverRef;
    private String debitOrderSenderRef;
}
