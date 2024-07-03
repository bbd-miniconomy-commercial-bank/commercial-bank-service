package com.miniconomy.commercial_bank_service.financial_management.command;

import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;
import com.miniconomy.commercial_bank_service.financial_management.enumeration.TransactionStatusEnum;
import com.miniconomy.commercial_bank_service.financial_management.service.DebitOrderService;

import java.util.UUID;

public class DebitOrderTransactionCommand extends TransactionCommandDecorator {

    private final DebitOrderService debitOrderService;
    private final UUID debitOrderId;

    public DebitOrderTransactionCommand(TransactionCommand transactionCommand, DebitOrderService debitOrderService, UUID debitOrderId) {
        super(transactionCommand);
        this.debitOrderService = debitOrderService;
        this.debitOrderId = debitOrderId;
    }

    @Override
        public Transaction execute() {
        Transaction transaction = this.transactionCommand.execute();
        
        // Add to Debit Order Transaction Table
        if (!transaction.getTransactionStatus().equals(TransactionStatusEnum.failed)) {
            debitOrderService.connectDebitOrderToTransaction(debitOrderId, transaction.getTransactionId());
        }

        return transaction;
    }
    
}
