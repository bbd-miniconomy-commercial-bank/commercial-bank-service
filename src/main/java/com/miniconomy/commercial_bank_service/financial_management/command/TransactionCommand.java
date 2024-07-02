package com.miniconomy.commercial_bank_service.financial_management.command;

import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;

public abstract class TransactionCommand {

    public final Transaction transaction;

    public TransactionCommand(Transaction transaction) {
        this.transaction = transaction;
    }

    public Transaction getTransaction() {
        return this.transaction;
    }
    
    public abstract void execute();
}
