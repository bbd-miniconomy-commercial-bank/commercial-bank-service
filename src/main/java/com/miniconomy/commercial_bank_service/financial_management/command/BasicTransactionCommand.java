package com.miniconomy.commercial_bank_service.financial_management.command;

import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BasicTransactionCommand extends TransactionCommand {
    
    private Transaction transaction;

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}
