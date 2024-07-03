package com.miniconomy.commercial_bank_service.financial_management.command;

import java.util.Optional;

import com.miniconomy.commercial_bank_service.financial_management.entity.Account;
import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;
import com.miniconomy.commercial_bank_service.financial_management.enumeration.TransactionStatusEnum;
import com.miniconomy.commercial_bank_service.financial_management.service.AccountService;
import com.miniconomy.commercial_bank_service.financial_management.service.TransactionService;

public class BasicTransactionCommand extends TransactionCommand {
    
    private final TransactionService transactionService;
    private final AccountService accountService;

    public final Transaction transaction;

    public BasicTransactionCommand(Transaction transaction, TransactionService transactionService, AccountService accountService) {
        this.transaction = transaction;
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @Override
    public Transaction execute() {
        Transaction createdTransaction;

        Optional<Account> dbAcc = accountService.retrieveAccountByName(transaction.getDebitAccountName());
        Optional<Account> crAcc = accountService.retrieveAccountByName(transaction.getCreditAccountName());

        if (dbAcc.isPresent() && crAcc.isPresent()) {
            Optional<Transaction> transactionOptional = transactionService.saveTransaction(transaction);
            if (transactionOptional.isPresent()) {
                createdTransaction = transactionOptional.get();
            } else {
                createdTransaction = transaction;
                createdTransaction.setTransactionStatus(TransactionStatusEnum.failed);
            }
        } else {
            createdTransaction = transaction;
            createdTransaction.setTransactionStatus(TransactionStatusEnum.failed);
        }

        return createdTransaction; 
    }
}
