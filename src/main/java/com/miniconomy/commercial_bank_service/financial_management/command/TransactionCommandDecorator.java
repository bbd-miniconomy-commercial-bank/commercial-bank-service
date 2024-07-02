package com.miniconomy.commercial_bank_service.financial_management.command;

import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;

public abstract class TransactionCommandDecorator extends TransactionCommand {
    
    protected TransactionCommand transactionCommand;

    public TransactionCommandDecorator(Transaction transaction, TransactionCommand transactionCommand) {
        super(transaction);
        this.transactionCommand = transactionCommand;
    }
}
