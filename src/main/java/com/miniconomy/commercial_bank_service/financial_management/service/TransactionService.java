package com.miniconomy.commercial_bank_service.financial_management.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.financial_management.entity.Account;
import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;
import com.miniconomy.commercial_bank_service.financial_management.entity.TransactionStatusType;
import com.miniconomy.commercial_bank_service.financial_management.repository.AccountRepository;
import com.miniconomy.commercial_bank_service.financial_management.repository.TransactionRepository;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public List<Transaction> retrieveTransactions(String accountName, Pageable pageable) {
        
        List<Transaction> transactions = List.of();
        Optional<Account> accountOptional = accountRepository.findByAccountName(accountName);
        
        if (accountOptional.isPresent()) {
            transactions = transactionRepository.findByAccountName(accountName, pageable);
        }

        return transactions; // returns empty list if account not found
    }

    public Optional<Transaction> retrieveTransactionById(UUID id, String accountName) {
        return transactionRepository.findById(id, accountName);
    }

    public List<Transaction> saveTransactions(List<Transaction> transactions, String accountName) {
        List<Transaction> createdTransactions = new ArrayList<>();

        for (Transaction transaction : transactions) {
            Optional<Account> dbAcc = accountRepository.findByAccountName(transaction.getDebitAccountName());
            Optional<Account> crAcc = accountRepository.findByAccountName(transaction.getCreditAccountName());

            if (dbAcc.isPresent() && crAcc.isPresent()) {
                Optional<Transaction> createdTransactionOptional = transactionRepository.save(transaction);
                Transaction createdTransaction;

                if (createdTransactionOptional.isPresent()) {
                    createdTransaction = createdTransactionOptional.get();
                } else {
                    createdTransaction = transaction;
                    createdTransaction.setTransactionStatus(TransactionStatusType.failed);
                }

                createdTransactions.add(createdTransaction);
            }
        }

        return createdTransactions;
    }
}
