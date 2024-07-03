package com.miniconomy.commercial_bank_service.financial_management.command;

import com.miniconomy.commercial_bank_service.financial_management.entity.InterbankTransaction;
import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;
import com.miniconomy.commercial_bank_service.financial_management.service.InterbankService;

public class IncomingInterbankTransactionCommand extends TransactionCommandDecorator {

    private InterbankService interbankService;

    private InterbankTransaction interbankTransaction;

    public IncomingInterbankTransactionCommand(TransactionCommand transactionCommand, InterbankService interbankService, InterbankTransaction interbankTransaction) {
        super(transactionCommand);
        this.interbankService = interbankService;
        this.interbankTransaction = interbankTransaction;
    }

    @Override
    public Transaction execute() {
        Transaction transaction = this.transactionCommand.execute();
        // Update Interbank Transaction Table and notify Retial Bank

        return transaction;
    }
    
}
