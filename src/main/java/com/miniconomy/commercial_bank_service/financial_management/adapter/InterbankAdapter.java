package com.miniconomy.commercial_bank_service.financial_management.adapter;

import com.miniconomy.commercial_bank_service.financial_management.entity.OutgoingInterbankDeposit;

public interface InterbankAdapter {
    
    public boolean processInterbankDeposit(OutgoingInterbankDeposit outgoingInterbankDeposit);
}
