package com.miniconomy.commercial_bank_service.financial_management.utils;

import com.miniconomy.commercial_bank_service.financial_management.entity.InterbankDeposit;
import com.miniconomy.commercial_bank_service.financial_management.entity.InterbankTransaction;
import com.miniconomy.commercial_bank_service.financial_management.request.InterbankDepositRequest;
import com.miniconomy.commercial_bank_service.financial_management.response.InterbankDepositResponse;

public class InterbankUtils {
    
    public static InterbankDepositResponse interbankDepositResponseMapper(InterbankTransaction interbankTransaction) {
        
        return new InterbankDepositResponse(
            interbankTransaction.getInterbankTransactionId(),
            interbankTransaction.getInterbankTransactionStatus()
        );
    }

    public static InterbankDeposit interbankDepositMapper(InterbankDepositRequest interbankDepositRequest) {
        
        return new InterbankDeposit(
            interbankDepositRequest.getCreditAccountName(),
            interbankDepositRequest.getAmount(),
            interbankDepositRequest.getDebitRef(),
            interbankDepositRequest.getCreditRef()
        );
    }
}
