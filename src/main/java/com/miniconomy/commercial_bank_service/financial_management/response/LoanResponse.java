package com.miniconomy.commercial_bank_service.financial_management.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

import com.miniconomy.commercial_bank_service.financial_management.enumeration.LoanType;

@Data
@AllArgsConstructor
public class LoanResponse {

    private UUID id;
    private Long amount;
    private LoanType type;
    private String accountName;
    
}
