package com.miniconomy.commercial_bank_service.financial_management.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OutgoingInterbankDepositCallback {
    private String externalBankId;
    private String debitAccountId;
    private String creditAccountId;
    private long amount;
    private String reference;
    private boolean completed;
}
