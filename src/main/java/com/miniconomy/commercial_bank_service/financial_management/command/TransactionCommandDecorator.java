package com.miniconomy.commercial_bank_service.financial_management.command;

public abstract class TransactionCommandDecorator extends TransactionCommand {
    
    protected TransactionCommand transactionCommand;

    public TransactionCommandDecorator(TransactionCommand transactionCommand) {
        this.transactionCommand = transactionCommand;
    }
}
