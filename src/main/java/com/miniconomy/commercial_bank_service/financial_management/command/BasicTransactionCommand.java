package com.miniconomy.commercial_bank_service.financial_management.command;

import java.util.Optional;

import com.miniconomy.commercial_bank_service.financial_management.entity.Account;
import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;
import com.miniconomy.commercial_bank_service.financial_management.enumeration.TransactionStatusEnum;
import com.miniconomy.commercial_bank_service.financial_management.service.AccountService;
import com.miniconomy.commercial_bank_service.financial_management.service.TransactionService;
import com.miniconomy.commercial_bank_service.simulation_management.store.SimulationStore;

import jakarta.validation.constraints.NotNull;

public class BasicTransactionCommand extends TransactionCommand {
    
    private final TransactionService transactionService;
    private final AccountService accountService;

    private Transaction transaction;

    public BasicTransactionCommand(@NotNull Transaction transaction, TransactionService transactionService, AccountService accountService) {
        this.transaction = transaction;
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @Override
    public Transaction execute() {

        Optional<Account> dbAcc = accountService.retrieveAccountByName(transaction.getDebitAccountName());
        Optional<Account> crAcc = accountService.retrieveAccountByName(transaction.getCreditAccountName());

        if (dbAcc.isPresent() && crAcc.isPresent()) {
            transaction.setTransactionDate(SimulationStore.getCurrentDate());
            Optional<Transaction> transactionOptional = transactionService.saveTransaction(transaction);
            if (transactionOptional.isPresent()) {
                transaction = transactionOptional.get();
            } else {
                transaction.setTransactionStatus(TransactionStatusEnum.failed);
            }
        } else {
            transaction.setTransactionStatus(TransactionStatusEnum.failed);
        }

        return transaction; 
    }

    @Override
    public Transaction rollback() {
        transaction.setTransactionStatus(TransactionStatusEnum.failed);
        Optional<Transaction> transactionOptional = transactionService.updateTransaction(transaction);

        if (transactionOptional.isEmpty()) {
            System.out.println("ERROR OCCURED WHILE REVERTING TRANSACTION: " + transaction.getTransactionId());
        } else {
            transaction = transactionOptional.get();
        }

        return transaction;
    }

    
}
