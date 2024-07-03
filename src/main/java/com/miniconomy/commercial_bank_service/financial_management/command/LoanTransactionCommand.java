package com.miniconomy.commercial_bank_service.financial_management.command;

import java.util.UUID;

import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;
import com.miniconomy.commercial_bank_service.financial_management.repository.LoanTransactionRepository;
import com.miniconomy.commercial_bank_service.financial_management.repository.TransactionRepository;

public class LoanTransactionCommand extends TransactionCommandDecorator {

    public LoanTransactionCommand(TransactionCommand transactionCommand) {
        super(transactionCommand);
    }
    
    @Override
    public Transaction execute( ) {
        Transaction transaction = this.transactionCommand.execute();
        // Add to Loan Transaction Table

        return transaction;
    }

    public Transaction execute(UUID loanId) {
        Transaction transaction = this.transactionCommand.execute();
        // Add to Loan Transaction Table
        

        TransactionRepository transactionRepository = new TransactionRepository();
        transactionRepository.insert(transaction);
        LoanTransactionRepository loanTransactionRepository = new LoanTransactionRepository();
        loanTransactionRepository.insert(transaction.getTransactionId(), loanId);
        
        return transaction;
    }
    
}
