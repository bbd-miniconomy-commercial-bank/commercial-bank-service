package com.miniconomy.commercial_bank_service.financial_management.request;

import java.util.List;

import lombok.Data;

@Data
public class InterbankDepositCreateRequest {
    
    List<InterbankDepositRequest> deposits;

}
