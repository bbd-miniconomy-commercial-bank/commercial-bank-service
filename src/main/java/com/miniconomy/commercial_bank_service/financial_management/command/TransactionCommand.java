package com.miniconomy.commercial_bank_service.financial_management.command;

import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;

public abstract class TransactionCommand {
    
    public abstract Transaction execute();
    public abstract Transaction rollback();
}
