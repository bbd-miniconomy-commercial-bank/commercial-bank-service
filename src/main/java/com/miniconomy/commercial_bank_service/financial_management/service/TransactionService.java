package com.miniconomy.commercial_bank_service.financial_management.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.financial_management.command.BasicTransactionCommand;
import com.miniconomy.commercial_bank_service.financial_management.command.OutgoingInterbankTransactionCommand;
import com.miniconomy.commercial_bank_service.financial_management.command.NotifyTransactionCommand;
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
    private final NotificationService notificationService;
    private final InterbankService interbankService;

    public TransactionService(TransactionRepository transactionRepository, AccountService accountService, NotificationService notificationService, InterbankService interbankService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.notificationService = notificationService;
        this.interbankService = interbankService;
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

    public List<Transaction> saveTransactions(List<Transaction> transactions, String accountName) {
        List<Transaction> createdTransactions = new ArrayList<>();

        for (Transaction transaction : transactions) {
            Transaction createdTransaction;

            if (transaction.getDebitAccountName().equals(accountName)) {
                TransactionCommand transactionCommand = transactionCommandBuilder(transaction, false, true);
                createdTransaction = TransactionInvoker.handler(transactionCommand); 
            } else {
                createdTransaction = transaction;
                transaction.setTransactionStatus(TransactionStatusEnum.failed);
            }
            
            createdTransactions.add(createdTransaction);
        }

        return createdTransactions;
    }

    private TransactionCommand transactionCommandBuilder(Transaction transaction, boolean notifyDebitAccount, boolean notifyCreditAccount) {
        TransactionCommand transactionCommand;

        Optional<Account> dbAccountOptional = accountService.retrieveAccountByName(transaction.getCreditAccountName());
        if (dbAccountOptional.isPresent()) {
            transactionCommand = new BasicTransactionCommand(transaction, this, accountService);
        } else {
            transaction.setDebitAccountName("retail-bank");
            transactionCommand = new BasicTransactionCommand(transaction, this, accountService);
            transactionCommand = new OutgoingInterbankTransactionCommand(transactionCommand, interbankService);
        }

        if (notifyDebitAccount) {
            transactionCommand = new NotifyTransactionCommand(transactionCommand, notificationService, transaction.getDebitAccountName());
        }
        if (notifyCreditAccount) {
            transactionCommand = new NotifyTransactionCommand(transactionCommand, notificationService, transaction.getDebitAccountName());
        }

        return transactionCommand;
    }
}
