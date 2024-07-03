package com.miniconomy.commercial_bank_service.financial_management.adapter;

import com.miniconomy.commercial_bank_service.financial_management.entity.OutgoingInterbankDeposit;
import com.miniconomy.commercial_bank_service.financial_management.entity.IncomingInterbankDepositCallback;

public interface InterbankAdapter {
    
    public boolean processInterbankDeposit(OutgoingInterbankDeposit outgoingInterbankDeposit);
    public boolean processInterbankDepositCallback(IncomingInterbankDepositCallback outgoingInterbankDepositCallback);

}
