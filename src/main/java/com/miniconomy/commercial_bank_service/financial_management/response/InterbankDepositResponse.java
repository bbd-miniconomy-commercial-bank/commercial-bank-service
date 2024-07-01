package com.miniconomy.commercial_bank_service.financial_management.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class InterbankDepositResponse {
    
    UUID depositReference;

}
