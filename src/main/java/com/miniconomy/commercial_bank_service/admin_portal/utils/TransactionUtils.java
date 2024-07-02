package com.miniconomy.commercial_bank_service.admin_portal.utils;

import com.miniconomy.commercial_bank_service.admin_portal.entity.Transaction;
import com.miniconomy.commercial_bank_service.admin_portal.response.TransactionResponse;

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
            transaction.getDebitAccount().getAccountName(), 
            transaction.getCreditAccount().getAccountName(), 
            transaction.getTransactionAmount(), 
            transaction.getTransactionStatus(), 
            transactionReference,
            transaction.getTransactionDate()
        );
    }

}
