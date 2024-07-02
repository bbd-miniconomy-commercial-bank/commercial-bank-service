package com.miniconomy.commercial_bank_service.admin_portal.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DebitOrderRequest {
    private String debitAccountName;
    private String creditAccountName;
    private Long amount;
    private String debitRef;
    private String creditRef;
}
