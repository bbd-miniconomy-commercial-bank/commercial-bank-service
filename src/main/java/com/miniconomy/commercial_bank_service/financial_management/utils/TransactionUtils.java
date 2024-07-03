package com.miniconomy.commercial_bank_service.financial_management.utils;

import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;
import com.miniconomy.commercial_bank_service.financial_management.enumeration.TransactionStatusEnum;
import com.miniconomy.commercial_bank_service.financial_management.request.TransactionRequest;
import com.miniconomy.commercial_bank_service.financial_management.response.TransactionResponse;

public class TransactionUtils {
    
    public static TransactionResponse transactionResponseMapper(Transaction transaction, String accountName) {
        
        String transactionReference;
        if (transaction.getDebitAccountName().equals(accountName)) {
            transactionReference = transaction.getTransactionDebitRef();
        } else {
            transactionReference = transaction.getTransactionCreditRef();
        }

        return new TransactionResponse(
            transaction.getTransactionId(),
            transaction.getDebitAccountName(),
            transaction.getCreditAccountName(),
            transactionReference,
            transaction.getTransactionAmount(), 
            transaction.getTransactionDate(),
            transaction.getTransactionStatus()
        );
    }

    public static Transaction transactionMapper(TransactionRequest transactionRequest) {

        return new Transaction(
            null,
            transactionRequest.getDebitAccountName(),
            transactionRequest.getCreditAccountName(),
            transactionRequest.getDebitRef(),
            transactionRequest.getCreditRef(),
            transactionRequest.getAmount(), 
            null, // IMPLEMENT
            TransactionStatusEnum.pending
        );
    }

}
