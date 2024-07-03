package com.miniconomy.commercial_bank_service.financial_management.command;

import com.miniconomy.commercial_bank_service.financial_management.entity.InterbankTransaction;
import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;
import com.miniconomy.commercial_bank_service.financial_management.enumeration.TransactionStatusEnum;
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
        
        if (!transaction.getTransactionStatus().equals(TransactionStatusEnum.failed)) {
            // Update Interbank Transaction Table
            interbankTransaction.setTransactionId(transaction.getTransactionId());
            interbankService.updateInterbankTransaction(interbankTransaction);

            // IMPLEMENT Notify Retial Bank
            interbankService.processDepositCallback(null);
        }

        return transaction;
    }
    
}
