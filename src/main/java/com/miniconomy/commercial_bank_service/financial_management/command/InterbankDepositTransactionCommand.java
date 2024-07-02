package com.miniconomy.commercial_bank_service.financial_management.command;

import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;

public class InterbankDepositTransactionCommand extends TransactionCommandDecorator {

    public InterbankDepositTransactionCommand(TransactionCommand transactionCommand) {
        super(transactionCommand);
    }

    @Override
    public Transaction execute() {
        Transaction transaction = this.transactionCommand.execute();
        // Add to Interbank Transaction Table and deposit to Retail Bank

        return transaction;
    }
    
}
