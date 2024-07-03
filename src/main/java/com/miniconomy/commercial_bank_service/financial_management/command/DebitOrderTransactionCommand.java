package com.miniconomy.commercial_bank_service.financial_management.command;

import com.miniconomy.commercial_bank_service.financial_management.entity.DebitOrderTransaction;
import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;
import com.miniconomy.commercial_bank_service.financial_management.enumeration.TransactionStatusEnum;
import com.miniconomy.commercial_bank_service.financial_management.service.DebitOrderService;

import java.util.Optional;

public class DebitOrderTransactionCommand extends TransactionCommandDecorator {

    private final DebitOrderService debitOrderService;

    private DebitOrderTransaction debitOrderTransaction;

    public DebitOrderTransactionCommand(TransactionCommand transactionCommand, DebitOrderService debitOrderService, DebitOrderTransaction debitOrderTransaction) {
        super(transactionCommand);
        this.debitOrderService = debitOrderService;
        this.debitOrderTransaction = debitOrderTransaction;
    }

    @Override
        public Transaction execute() {
        Transaction transaction = this.transactionCommand.execute();
        
        // Add to Debit Order Transaction Table
        if (!transaction.getTransactionStatus().equals(TransactionStatusEnum.failed)) {
            
            debitOrderTransaction.setTransactionId(transaction.getTransactionId());
            
            Optional<DebitOrderTransaction> debitOrderTransactionOptional = debitOrderService.saveDebitOrderTransaction(debitOrderTransaction);
            if (debitOrderTransactionOptional.isPresent()) {
                debitOrderTransaction = debitOrderTransactionOptional.get();
            } else {                
                return this.transactionCommand.rollback();
            }
        }

        return transaction;
    }

    @Override
    public Transaction rollback() {
        return this.transactionCommand.rollback();
    }
    
}
