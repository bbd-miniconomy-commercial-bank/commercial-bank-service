package com.miniconomy.commercial_bank_service.financial_management.command;

import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;
import com.miniconomy.commercial_bank_service.financial_management.repository.TransactionRepository;

public class BasicTransactionCommand extends TransactionCommand {
    
    private final TransactionRepository transactionRepository;

    public BasicTransactionCommand(Transaction transaction, TransactionRepository transactionRepository) {
        super(transaction);
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void execute() {
        // ADD FUNCTIONALITY
        transactionRepository.save(transaction);
    }
}
