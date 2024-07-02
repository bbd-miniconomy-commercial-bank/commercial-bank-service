package com.miniconomy.commercial_bank_service.financial_management.adapter;

import com.miniconomy.commercial_bank_service.financial_management.entity.InterbankDeposit;

public class RetailBankAdapter implements InterbankAdapter {

    public boolean processInterbankDeposit(InterbankDeposit InterbankDeposit) {
        return true;
    }
    
}
