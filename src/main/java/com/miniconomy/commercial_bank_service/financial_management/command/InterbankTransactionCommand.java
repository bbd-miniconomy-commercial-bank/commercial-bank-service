package com.miniconomy.commercial_bank_service.financial_management.command;

import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;

public class InterbankTransactionCommand extends TransactionCommandDecorator {

    public InterbankTransactionCommand(TransactionCommand transactionCommand) {
        super(transactionCommand);
    }

    @Override
    public Transaction execute() {
        Transaction transaction = this.transactionCommand.execute();
        // Deposit into Retail Bank

        return transaction;
    }
    
}
