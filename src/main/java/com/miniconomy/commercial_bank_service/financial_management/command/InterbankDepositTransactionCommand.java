package com.miniconomy.commercial_bank_service.financial_management.command;

import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;

public class InterbankDepositTransactionCommand extends TransactionCommandDecorator {

    public InterbankDepositTransactionCommand(Transaction transaction, TransactionCommand transactionCommand) {
        super(transaction, transactionCommand);
    }

    @Override
    public void execute() {
        this.transactionCommand.execute();
        // Add to Interbank Transaction Table
    }
    
}
