package com.miniconomy.commercial_bank_service.financial_management.utils;

import com.miniconomy.commercial_bank_service.financial_management.entity.InterbankTransaction;
import com.miniconomy.commercial_bank_service.financial_management.response.InterbankDepositResponse;

public class InterbankUtils {
    
    public static InterbankDepositResponse interbankDepositResponseMapper(InterbankTransaction interbank) {
        
        return new InterbankDepositResponse(
            interbank.getInterbankTransactionId()
        );
    }
}
