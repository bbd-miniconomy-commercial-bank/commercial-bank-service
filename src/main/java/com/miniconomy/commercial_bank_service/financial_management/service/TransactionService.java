package com.miniconomy.commercial_bank_service.financial_management.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.financial_management.builder.TransactionCommandBuilder;
import com.miniconomy.commercial_bank_service.financial_management.command.TransactionCommand;
import com.miniconomy.commercial_bank_service.financial_management.entity.Account;
import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;
import com.miniconomy.commercial_bank_service.financial_management.enumeration.TransactionStatusEnum;
import com.miniconomy.commercial_bank_service.financial_management.invoker.TransactionInvoker;
import com.miniconomy.commercial_bank_service.financial_management.repository.TransactionRepository;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final AccountService accountService;

    private final TransactionCommandBuilder transactionCommandBuilder;

    public TransactionService(TransactionRepository transactionRepository, AccountService accountService, TransactionCommandBuilder transactionCommandBuilder) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.transactionCommandBuilder = transactionCommandBuilder;
    }

    public List<Transaction> retrieveTransactions(String accountName, Pageable pageable) {
        
        List<Transaction> transactions = List.of();
        Optional<Account> accountOptional = accountService.retrieveAccountByName(accountName);
        
        if (accountOptional.isPresent()) {
            transactions = transactionRepository.findByAccountName(accountName, pageable);
        }

        return transactions;
    }

    public Optional<Transaction> retrieveTransactionById(UUID id, String accountName) {
        return transactionRepository.findById(id, accountName);
    }

    public Optional<Transaction> saveTransaction(Transaction transaction) {
        return this.transactionRepository.insert(transaction);
    }

    public Optional<Transaction> updateTransaction(Transaction transaction) {
        return this.transactionRepository.update(transaction);
    }

    public List<Transaction> saveTransactions(List<Transaction> transactions, String accountName) {
        List<Transaction> createdTransactions = new ArrayList<>();

        for (Transaction transaction : transactions) {
            Transaction createdTransaction;

            if (transaction.getDebitAccountName().equals(accountName)) {
                TransactionCommand transactionCommand = transactionCommandBuilder.buildTransactionCommand(transaction, false, true);
                createdTransaction = TransactionInvoker.handler(transactionCommand); 
            } else {
                createdTransaction = transaction;
                transaction.setTransactionStatus(TransactionStatusEnum.failed);
            }
            
            createdTransactions.add(createdTransaction);
        }

        return createdTransactions;
    }

}
