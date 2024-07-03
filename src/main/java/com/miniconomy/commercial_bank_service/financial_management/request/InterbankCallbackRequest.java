package com.miniconomy.commercial_bank_service.financial_management.request;

import com.miniconomy.commercial_bank_service.financial_management.enumeration.InterbankTransactionStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterbankCallbackRequest {
    
    String reference;
    InterbankTransactionStatusEnum status;
}
