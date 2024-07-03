package com.miniconomy.commercial_bank_service.financial_management.command;

import com.miniconomy.commercial_bank_service.financial_management.entity.InterbankTransaction;
import com.miniconomy.commercial_bank_service.financial_management.entity.IncomingInterbankDepositCallback;
import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;
import com.miniconomy.commercial_bank_service.financial_management.enumeration.InterbankTransactionStatusEnum;
import com.miniconomy.commercial_bank_service.financial_management.enumeration.TransactionStatusEnum;
import com.miniconomy.commercial_bank_service.financial_management.service.InterbankService;

import java.util.Optional;

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
            interbankTransaction.setInterbankTransactionStatus(InterbankTransactionStatusEnum.complete);
            Optional<InterbankTransaction> interbankTransactionOptional = interbankService.updateInterbankTransaction(interbankTransaction);

            if (interbankTransactionOptional.isPresent()) {
                interbankTransaction = interbankTransactionOptional.get();
                
                // Notify Retial Bank
                IncomingInterbankDepositCallback outgoingInterbankDepositCallback = new IncomingInterbankDepositCallback(
                    "retail-bank", 
                    transaction.getDebitAccountName(), 
                    transaction.getCreditAccountName(), 
                    transaction.getTransactionAmount(), 
                    interbankTransaction.getInterbankTransactionId().toString(), 
                    true
                );

                interbankService.processDepositCallback(outgoingInterbankDepositCallback);
            }
        }

        return transaction;
    }
    
}
