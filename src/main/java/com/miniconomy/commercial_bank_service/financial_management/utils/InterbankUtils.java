package com.miniconomy.commercial_bank_service.financial_management.utils;

import com.miniconomy.commercial_bank_service.financial_management.entity.IncomingInterbankDeposit;
import com.miniconomy.commercial_bank_service.financial_management.entity.InterbankTransaction;
import com.miniconomy.commercial_bank_service.financial_management.entity.OutgoingInterbankDeposit;
import com.miniconomy.commercial_bank_service.financial_management.entity.IncomingInterbankDepositCallback;
import com.miniconomy.commercial_bank_service.financial_management.request.InterbankDepositRequest;
import com.miniconomy.commercial_bank_service.financial_management.request.OutgoingRetailBankRequest;
import com.miniconomy.commercial_bank_service.financial_management.request.RetailBankCallbackRequest;
import com.miniconomy.commercial_bank_service.financial_management.response.InterbankDepositResponse;

public class InterbankUtils {
    
    public static InterbankDepositResponse interbankDepositResponseMapper(InterbankTransaction interbankTransaction) {
        
        return new InterbankDepositResponse(
            interbankTransaction.getInterbankTransactionId(),
            interbankTransaction.getInterbankTransactionStatus()
        );
    }

    public static IncomingInterbankDeposit incomingInterbankDepositMapper(InterbankDepositRequest interbankDepositRequest) {
        
        return new IncomingInterbankDeposit(
            interbankDepositRequest.getDebitAccountName(),
            interbankDepositRequest.getCreditAccountName(),
            interbankDepositRequest.getAmount(),
            interbankDepositRequest.getDebitRef(),
            interbankDepositRequest.getCreditRef()
        );
    }

    public static OutgoingRetailBankRequest outgoingRetailBankRequestMapper(OutgoingInterbankDeposit outgoingInterbankDeposit) {

        return new OutgoingRetailBankRequest(
            Long.parseLong(outgoingInterbankDeposit.getCreditAccountId()), 
            outgoingInterbankDeposit.getAmount(), 
            outgoingInterbankDeposit.getReference(),
            outgoingInterbankDeposit.getDebitAccountId()
        );
    }

    public static RetailBankCallbackRequest outgoingCallbackRetailBankRequestMapper(IncomingInterbankDepositCallback outgoingInterbankDepositCallback) {

        return new RetailBankCallbackRequest(
            outgoingInterbankDepositCallback.isCompleted(), 
        0, 
        ""
        );
    }
}
