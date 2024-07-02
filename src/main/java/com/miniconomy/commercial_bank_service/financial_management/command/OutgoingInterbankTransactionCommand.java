package com.miniconomy.commercial_bank_service.financial_management.command;

import com.miniconomy.commercial_bank_service.financial_management.entity.InterbankTransaction;
import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;
import com.miniconomy.commercial_bank_service.financial_management.enumeration.InterbankTransactionStatusEnum;
import com.miniconomy.commercial_bank_service.financial_management.enumeration.TransactionStatusEnum;
import com.miniconomy.commercial_bank_service.financial_management.service.InterbankService;

public class OutgoingInterbankTransactionCommand extends TransactionCommandDecorator {
    
    private InterbankService interbankService;

    public OutgoingInterbankTransactionCommand(TransactionCommand transactionCommand, InterbankService interbankService) {
        super(transactionCommand);
        this.interbankService = interbankService;
    }

    @Override
    public Transaction execute() {
        Transaction transaction = this.transactionCommand.execute();
        
        if (!transaction.getTransactionStatus().equals(TransactionStatusEnum.failed)) {
            // Add to Interbank Transaction Table and deposit to Retail Bank
            InterbankTransaction interbankTransaction = new InterbankTransaction();
            interbankTransaction.setInterbankTransactionId(transaction.getTransactionId());
            interbankTransaction.setInterbankTransactionStatus(InterbankTransactionStatusEnum.pending);

            interbankService.saveInterbankTransaction()
        }

        return transaction;
    }
}
