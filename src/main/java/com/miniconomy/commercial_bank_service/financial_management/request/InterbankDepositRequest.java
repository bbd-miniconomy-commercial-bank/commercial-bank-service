package com.miniconomy.commercial_bank_service.financial_management.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterbankDepositRequest {

    private String creditAccountName;
    private Long amount;
    private String debitRef;
    private String creditRef;
}
