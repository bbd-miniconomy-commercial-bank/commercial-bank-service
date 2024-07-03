package com.miniconomy.commercial_bank_service.financial_management.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutgoingInterbankDeposit {
    private String externalBankId;
    private String debitAccountId;
    private String creditAccountId;
    private long amount;
    private String reference;
}
