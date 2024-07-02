package com.miniconomy.commercial_bank_service.financial_management.service;

import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.financial_management.entity.Account;
import com.miniconomy.commercial_bank_service.financial_management.entity.Loan;
import com.miniconomy.commercial_bank_service.financial_management.repository.AccountRepository;
import com.miniconomy.commercial_bank_service.financial_management.repository.LoanRepository;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final AccountRepository accRepo;

    public LoanService(LoanRepository loanRepository, AccountRepository acc) {
        this.loanRepository = loanRepository;
        this.accRepo = acc;
    }

    public Optional<Loan> createLoan(Loan loan, String accountName) {
    
        Optional<Loan> createdLoan = Optional.empty();

        Optional<Account> accountOptional = accRepo.findByAccountName(accountName);
        if (accountOptional.isPresent()) {
            createdLoan = loanRepository.save(loan);
        }

        return createdLoan;
    }

    public Optional<Loan> retrieveLoanById(UUID loanId, String accountName) {
        return loanRepository.findById(loanId, accountName);
    }

    public List<Loan> retrieveAccountLoans(String accountName, Pageable pageable) {
        return loanRepository.findAllByAccountName(accountName, pageable);
    }

    public List<Loan> retrieveAllLoans(Pageable pageable) {
        return loanRepository.findAllLoans(pageable);
    }
}
