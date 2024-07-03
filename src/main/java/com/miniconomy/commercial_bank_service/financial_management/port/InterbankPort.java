package com.miniconomy.commercial_bank_service.financial_management.port;

import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.financial_management.adapter.RetailBankAdapter;
import com.miniconomy.commercial_bank_service.financial_management.entity.IncomingInterbankDepositCallback;
import com.miniconomy.commercial_bank_service.financial_management.entity.OutgoingInterbankDeposit;

@Service
public class InterbankPort {
    
    private final RetailBankAdapter retailBankAdapter;

    public InterbankPort(RetailBankAdapter retailBankAdapter) {
        this.retailBankAdapter = retailBankAdapter;
    }

    public boolean sendOutgoingDeposit(OutgoingInterbankDeposit outgoingInterbankDeposit) {
        boolean processed = false;
        
        if (outgoingInterbankDeposit.getExternalBankId().equals("retail-bank")) {
            processed = retailBankAdapter.processInterbankDeposit(outgoingInterbankDeposit);
        } 

        return processed;
    }

    public boolean sendIncomingDepositCallback(IncomingInterbankDepositCallback incomingInterbankDepositCallback) {
        boolean processed = false;
        
        if (incomingInterbankDepositCallback.getExternalBankId().equals("retail-bank")) {
            processed = retailBankAdapter.processInterbankDepositCallback(incomingInterbankDepositCallback);
        } 

        return processed;
    }

}
