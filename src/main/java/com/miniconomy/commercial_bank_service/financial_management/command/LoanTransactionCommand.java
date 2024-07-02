package com.miniconomy.commercial_bank_service.financial_management.command;

import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;

public class LoanTransactionCommand extends TransactionCommandDecorator {

    public LoanTransactionCommand(Transaction transaction, TransactionCommand transactionCommand) {
        super(transaction, transactionCommand);
    }
    
    @Override
    public void execute() {
        this.transactionCommand.execute();
        // Add to Loan Transaction Table
    }
    
}
