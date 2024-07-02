package com.miniconomy.commercial_bank_service.admin_portal.request;

import lombok.Data;

@Data
public class TransactionRequest {
    private String debitAccountName;
    private String creditAccountName;
    private Long amount;
    private String debitRef;
    private String creditRef;
}
