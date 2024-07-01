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
import com.miniconomy.commercial_bank_service.financial_management.request.TransactionRequest;
import com.miniconomy.commercial_bank_service.financial_management.response.TransactionResponse;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public List<TransactionResponse> retrieveTransactions(UUID creditAccountId, Pageable pageable) {
        Optional<Account> acc = accountRepository.findById(creditAccountId);
        if (acc.isPresent()) {
            List<Transaction> credTransactions = transactionRepository.findByCreditAccount(acc.get().getAccountId(), pageable);
            List<Transaction> debTransactions = transactionRepository.findByDebitAccount(acc.get().getAccountId(), pageable);

            List<TransactionResponse> transactionResponses = new ArrayList<>();

            credTransactions.forEach(transaction -> {
                Optional<Account> creditAccount = accountRepository.findById(transaction.getCreditAccountId());
                Optional<Account> debitAccount = accountRepository.findById(transaction.getDebitAccountId());

                if (creditAccount.isPresent() && debitAccount.isPresent()) {
                    TransactionResponse transactionResponse = new TransactionResponse(
                            debitAccount.get().getAccountId().toString(),
                            creditAccount.get().getAccountName(),
                            transaction.getTransactionAmount(),
                            transaction.getTransactionStatus(),
                            transaction.getDebitRef(),
                            transaction.getCreditRef(),
                            transaction.getTransactionDate()
                    );
                    transactionResponses.add(transactionResponse);
                }
            });

            debTransactions.forEach(transaction -> {
                Optional<Account> creditAccount = accountRepository.findById(transaction.getCreditAccountId());
                Optional<Account> debitAccount = accountRepository.findById(transaction.getDebitAccountId());

                if (creditAccount.isPresent() && debitAccount.isPresent()) {
                    TransactionResponse transactionResponse = new TransactionResponse(
                            debitAccount.get().getAccountName(),
                            creditAccount.get().getAccountName(),
                            transaction.getTransactionAmount(),
                            transaction.getTransactionStatus(),
                            transaction.getDebitRef(),
                            transaction.getCreditRef(),
                            transaction.getTransactionDate()
                    );
                    transactionResponses.add(transactionResponse);
                }
            });

            return transactionResponses;
        }
        return List.of(); // returns empty list if account not found
    }

    public Optional<Transaction> retrieveTransactionById(UUID id) {
        return transactionRepository.findById(id);
    }

    public List<TransactionResponse> saveTransactions(List<TransactionRequest> transactionRequests) {
        List<Transaction> transactions = new ArrayList<>();
        List<TransactionResponse> transactionResponses = new ArrayList<>();

        for (TransactionRequest request : transactionRequests) {
            Optional<Account> dbAcc = accountRepository.findByAccountName(request.getDebitAccountName());
            Optional<Account> crAcc = accountRepository.findByAccountName(request.getCreditAccountName());

            if (dbAcc.isPresent() && crAcc.isPresent()) {
                Transaction transaction = new Transaction();
                transaction.setDebitAccountId(dbAcc.get().getAccountId());
                transaction.setCreditAccountId(crAcc.get().getAccountId());
                transaction.setTransactionAmount(request.getAmount());
                transaction.setCreditRef(request.getCreditRef());
                transaction.setDebitRef(request.getDebitRef());
                transaction.setTransactionStatus(TransactionStatusType.pending.toString());
                transaction.setTransactionDate("Date from Hand of Zeus");

                transactions.add(transaction);

                TransactionResponse transactionResponse = new TransactionResponse(
                        dbAcc.get().getAccountId().toString(),
                        crAcc.get().getAccountId().toString(),
                        request.getAmount(),
                        TransactionStatusType.pending.toString(),
                        request.getDebitRef(),
                        request.getCreditRef(),
                        "Date from hand of zeus"
                );
                transactionResponses.add(transactionResponse);
            }
        }

        if (!transactions.isEmpty()) {
            transactionRepository.saveAll(transactions);
        }

        return transactionResponses;
    }
}
