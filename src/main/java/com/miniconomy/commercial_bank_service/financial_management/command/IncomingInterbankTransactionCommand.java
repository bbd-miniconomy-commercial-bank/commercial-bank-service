package com.miniconomy.commercial_bank_service.financial_management.command;

import com.miniconomy.commercial_bank_service.financial_management.entity.InterbankTransaction;
import com.miniconomy.commercial_bank_service.financial_management.entity.IncomingInterbankDepositCallback;
import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;
import com.miniconomy.commercial_bank_service.financial_management.enumeration.InterbankTransactionStatusEnum;
import com.miniconomy.commercial_bank_service.financial_management.enumeration.TransactionStatusEnum;
import com.miniconomy.commercial_bank_service.financial_management.service.InterbankService;

import java.util.Optional;

public class IncomingInterbankTransactionCommand extends TransactionCommandDecorator {

    private final InterbankService interbankService;

    private InterbankTransaction interbankTransaction;
    private IncomingInterbankDepositCallback incomingInterbankDepositCallback;

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
                incomingInterbankDepositCallback = new IncomingInterbankDepositCallback(
                    "retail-bank", 
                    transaction.getDebitAccountName(), 
                    transaction.getCreditAccountName(), 
                    transaction.getTransactionAmount(), 
                    interbankTransaction.getInterbankTransactionId().toString(), 
                    true
                );

                interbankService.processDepositCallback(incomingInterbankDepositCallback);
            } else {
                return this.transactionCommand.rollback();
            }
        }

        return transaction;
    }

    @Override
    public Transaction rollback() {
        interbankTransaction.setInterbankTransactionStatus(InterbankTransactionStatusEnum.failed);
        Optional<InterbankTransaction> interbankTransactionOptional = interbankService.updateInterbankTransaction(interbankTransaction);

        interbankTransaction = interbankTransactionOptional.get();
        
        // Notify Retial Bank
        incomingInterbankDepositCallback.setCompleted(false);
        interbankService.processDepositCallback(incomingInterbankDepositCallback);
        
        return this.transactionCommand.rollback();
    }
    
}
