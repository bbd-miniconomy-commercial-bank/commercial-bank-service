package com.miniconomy.commercial_bank_service.financial_management.request;

import lombok.Data;

@Data
public class DebitOrderRequest {
    private String debitAccountName;
    private String creditAccountName;
    private String debitRef;
    private String creditRef;
    private Long amount;
}
