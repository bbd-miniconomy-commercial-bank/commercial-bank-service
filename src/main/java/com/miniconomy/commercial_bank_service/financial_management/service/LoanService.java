package com.miniconomy.commercial_bank_service.financial_management.service;

import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.financial_management.entity.Account;
import com.miniconomy.commercial_bank_service.financial_management.entity.Loan;
import com.miniconomy.commercial_bank_service.financial_management.entity.LoanType;
import com.miniconomy.commercial_bank_service.financial_management.repository.AccountRepository;
import com.miniconomy.commercial_bank_service.financial_management.repository.LoanRepository;
import com.miniconomy.commercial_bank_service.financial_management.request.LoanRequest;

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

    public Optional<Loan> createLoan(LoanRequest loan, String accountName) {
        System.out.println(loan.toString());
        Loan newLoan = new Loan();
        if (loan.getType().equals(LoanType.LONG_TERM)) {
            newLoan.setLoanType(LoanType.LONG_TERM);
        }
        else if (loan.getType().equals(LoanType.SHORT_TERM)) {
            newLoan.setLoanType(LoanType.SHORT_TERM);
        }
        Optional<Account> acc = accRepo.findByAccountName(accountName);
        if (acc.isPresent()) {
            newLoan.setLoanAmount(loan.getAmount());
            newLoan.setAccount(acc.get());
        }

        return Optional.of(loanRepository.save(newLoan));
    }

    public Optional<Loan> getLoanById(UUID loanId, String accountName) {
        return Optional.of(loanRepository.findByLoanIdAndAccountName(loanId, accountName).orElse(null));
    }

    public List<Loan> getAllLoans(String accountName) {
        return loanRepository.findByAccountName(accountName);
    }
}
