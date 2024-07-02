package com.miniconomy.commercial_bank_service.financial_management.command;

import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;

public class DebitOrderTransactionCommand extends TransactionCommandDecorator {

    public DebitOrderTransactionCommand(TransactionCommand transactionCommand) {
        super(transactionCommand);
    }

    @Override
        public Transaction execute() {
        Transaction transaction = this.transactionCommand.execute();
        // Add to Debit Order Transaction Table

        return transaction;
    }
    
}
