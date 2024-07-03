package com.miniconomy.commercial_bank_service.financial_management.service;

import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.financial_management.builder.TransactionCommandBuilder;
import com.miniconomy.commercial_bank_service.financial_management.command.TransactionCommand;
import com.miniconomy.commercial_bank_service.financial_management.entity.Account;
import com.miniconomy.commercial_bank_service.financial_management.entity.Loan;
import com.miniconomy.commercial_bank_service.financial_management.entity.LoanTransaction;
import com.miniconomy.commercial_bank_service.financial_management.invoker.TransactionInvoker;
import com.miniconomy.commercial_bank_service.financial_management.repository.LoanRepository;
import com.miniconomy.commercial_bank_service.financial_management.repository.LoanTransactionRepository;
import com.miniconomy.commercial_bank_service.simulation_management.store.SimulationStore;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoanService {

    private final LoanTransactionRepository loanTransactionRepository;
    private final LoanRepository loanRepository;
    private final AccountService accountService;

    private final TransactionCommandBuilder transactionCommandBuilder;

    public LoanService(
        LoanTransactionRepository loanTransactionRepository, 
        LoanRepository loanRepository, 
        AccountService accountService,
        TransactionCommandBuilder transactionCommandBuilder
    ) {
        this.loanTransactionRepository = loanTransactionRepository;
        this.loanRepository = loanRepository;
        this.accountService = accountService;
        this.transactionCommandBuilder = transactionCommandBuilder;
    }

    public Optional<Loan> createLoan(Loan loan, String accountName) {
    
        Optional<Loan> createdLoan = Optional.empty();
    
        Optional<Account> accountOptional = accountService.retrieveAccountByName(accountName);
        if (accountOptional.isPresent()) {
            loan.setLoanCreatedDate(SimulationStore.getCurrentDate());
            createdLoan = loanRepository.insert(loan);
        }

        return createdLoan;
    }

    public Optional<Loan> retrieveLoanById(UUID loanId, String accountName) {
        return loanRepository.findById(loanId, accountName);
    }

    public List<Loan> retrieveAccountLoans(String accountName, Pageable pageable) {
        return loanRepository.findAllByAccountName(accountName, pageable);
    }

    public Optional<LoanTransaction> connectLoanToTransaction(UUID loanId, UUID transactionId) {
        LoanTransaction loanTransaction = new LoanTransaction(
            null,
            loanId,
            transactionId
        );

        return loanTransactionRepository.insert(loanTransaction);
    }

    public void processLoans() {

        int loansPerBatch = 25;
        int page = 0;

        Pageable pageable = PageRequest.of(page, loansPerBatch);
        List<Loan> loans = loanRepository.findAll(pageable);
        while (loans.size() > 0) {
            pageable = PageRequest.of(++page, loansPerBatch);

            for (Loan loan : loans) {
                TransactionCommand transactionCommand = transactionCommandBuilder.buildTransactionCommand(loan);
                TransactionInvoker.handler(transactionCommand);
            }

            loans = loanRepository.findAll(pageable);
        }

    }
}
