package com.miniconomy.commercial_bank_service.financial_management.command;

import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;
import com.miniconomy.commercial_bank_service.financial_management.enumeration.TransactionStatusEnum;
import com.miniconomy.commercial_bank_service.financial_management.service.LoanService;

import java.util.UUID;

public class LoanTransactionCommand extends TransactionCommandDecorator {

    private final LoanService loanService;
    private final UUID loanId;

    public LoanTransactionCommand(TransactionCommand transactionCommand, LoanService loanService, UUID loanId) {
        super(transactionCommand);
        this.loanService = loanService;
        this.loanId = loanId;
    }
    
    @Override
    public Transaction execute( ) {
        Transaction transaction = this.transactionCommand.execute();
        
        // Add to Loan Transaction Table
        if (!transaction.getTransactionStatus().equals(TransactionStatusEnum.failed)) {
            loanService.connectLoanToTransaction(loanId, transaction.getTransactionId());
        }

        return transaction;
    }

    @Override
    public Transaction rollback() {
        return this.transactionCommand.rollback();
    }
    
}
