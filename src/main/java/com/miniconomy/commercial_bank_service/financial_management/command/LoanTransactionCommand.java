package com.miniconomy.commercial_bank_service.financial_management.command;

import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;

public class LoanTransactionCommand extends TransactionCommandDecorator {

    public LoanTransactionCommand(TransactionCommand transactionCommand) {
        super(transactionCommand);
    }
    
    @Override
    public Transaction execute() {
        Transaction transaction = this.transactionCommand.execute();
        // Add to Loan Transaction Table

        return transaction;
    }
    
}
