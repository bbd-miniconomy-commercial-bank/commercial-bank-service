package com.miniconomy.commercial_bank_service.admin_portal.service;

import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.admin_portal.entity.Account;
import com.miniconomy.commercial_bank_service.admin_portal.entity.Loan;
import com.miniconomy.commercial_bank_service.admin_portal.entity.LoanType;
import com.miniconomy.commercial_bank_service.admin_portal.repository.AccountRepository;
import com.miniconomy.commercial_bank_service.admin_portal.repository.LoanRepository;
import com.miniconomy.commercial_bank_service.admin_portal.request.LoanRequest;

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

    public Optional<Loan> createLoan(LoanRequest loan) {
        System.out.println(loan.toString());
        Loan newLoan = new Loan();
        if (loan.getType().equals(LoanType.LONG_TERM)) {
            newLoan.setLoanType(LoanType.LONG_TERM);
        }
        else if (loan.getType().equals(LoanType.SHORT_TERM)) {
            newLoan.setLoanType(LoanType.SHORT_TERM);
        }
        Optional<Account> acc = accRepo.findByAccountName(loan.getAccountName());
        if (acc.isPresent()) {
            newLoan.setLoanAmount(loan.getAmount());
            newLoan.setAccount(acc.get());
        }

        return Optional.of(loanRepository.save(newLoan));
    }

    public Optional<Loan> getLoanById(UUID loanId) {
        return Optional.of(loanRepository.findById(loanId).orElse(null));
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }
}
