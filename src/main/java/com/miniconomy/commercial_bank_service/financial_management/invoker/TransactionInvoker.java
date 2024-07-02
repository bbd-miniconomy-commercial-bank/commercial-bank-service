package com.miniconomy.commercial_bank_service.financial_management.invoker;

import com.miniconomy.commercial_bank_service.financial_management.command.TransactionCommand;
import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;

public class TransactionInvoker {
    
    public static Transaction handler(TransactionCommand transactionCommand) {
        transactionCommand.execute();
        return transactionCommand.getTransaction();
    }

}
