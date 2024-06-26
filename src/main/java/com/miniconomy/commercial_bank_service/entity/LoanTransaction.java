package com.miniconomy.commercial_bank_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class LoanTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long loanTransactionId;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    private Long transactionId;

    // Getters and Setters

    public Long getLoanTransactionId() {
        return loanTransactionId;
    }

    public void setLoanTransactionId(Long loanTransactionId) {
        this.loanTransactionId = loanTransactionId;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }
}
