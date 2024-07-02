package com.miniconomy.commercial_bank_service.financial_management.utils;

import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;
import com.miniconomy.commercial_bank_service.financial_management.response.TransactionResponse;

public class TransactionUtils {
    
    public static TransactionResponse transactionResponseMapper(Transaction transaction, String accountName) {
        
        String transactionReference;
        if (transaction.getDebitAccount().getAccountName().equals(accountName)) {
            transactionReference = transaction.getDebitRef();
        } else {
            transactionReference = transaction.getCreditRef();
        }

        return new TransactionResponse(
            transaction.getTransactionId(),
            transaction.getDebitAccountId().toString(),
            transaction.getCreditAccountId().toString(),
            transaction.getTransactionAmount(), 
            transaction.getTransactionStatus(), 
            transactionReference,
            transaction.getTransactionDate()
        );
    }

}
