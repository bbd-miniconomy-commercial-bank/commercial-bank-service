package com.miniconomy.commercial_bank_service.financial_management.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

import com.miniconomy.commercial_bank_service.financial_management.enumeration.LoanTypeEnum;

@Data
@AllArgsConstructor
public class LoanResponse {

    private UUID id;
    private Long amount;
    private LoanTypeEnum type;
    private String accountName;
    
}
