package com.miniconomy.commercial_bank_service.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.miniconomy.commercial_bank_service.entity.Account;

import lombok.Data;

@Data
public class DebitOrderRequest {
    private String creditAccountName;
    private String debitAccountName;
    private Long debitOrderAmount;
    private String debitOrderReceiverRef;
    private String debitOrderSenderRef;
}
