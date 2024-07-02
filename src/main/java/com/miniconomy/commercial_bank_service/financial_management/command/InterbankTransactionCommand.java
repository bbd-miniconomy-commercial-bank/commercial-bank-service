package com.miniconomy.commercial_bank_service.financial_management.command;

import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;

public class InterbankTransactionCommand extends TransactionCommandDecorator {

    public InterbankTransactionCommand(Transaction transaction, TransactionCommand transactionCommand) {
        super(transaction, transactionCommand);
    }

    @Override
    public void execute() {
        this.transactionCommand.execute();
        // Deposit into Retail Bank
    }
    
}
