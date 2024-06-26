package com.miniconomy.commercial_bank_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.miniconomy.commercial_bank_service.entity.Loan;
import com.miniconomy.commercial_bank_service.repository.LoanRepository;

import java.util.List;

@Service
public class LoanService {

    private final LoanRepository loanRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public Loan createLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    public Loan getLoanById(Long loanId) {
        return loanRepository.findById(loanId).orElse(null);
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }
}
