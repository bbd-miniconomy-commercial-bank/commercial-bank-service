package com.miniconomy.commercial_bank_service.financial_management.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterbankCallbackRequest {
    
    String reference;
    String status;
}
