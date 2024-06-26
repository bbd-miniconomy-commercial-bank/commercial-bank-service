package com.miniconomy.commercial_bank_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

@Entity
public class LoanInterest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long loanInterestId;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    @Column(precision = 5, scale = 2)
    private BigDecimal loanInterestRate;

    @Column(precision = 13, scale = 3)
    private BigDecimal loanInterestAmount;

    private Character loanInterestDate;

    // Getters and Setters

    public Long getLoanInterestId() {
        return loanInterestId;
    }

    public void setLoanInterestId(Long loanInterestId) {
        this.loanInterestId = loanInterestId;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public BigDecimal getLoanInterestRate() {
        return loanInterestRate;
    }

    public void setLoanInterestRate(BigDecimal loanInterestRate) {
        this.loanInterestRate = loanInterestRate;
    }

    public BigDecimal getLoanInterestAmount() {
        return loanInterestAmount;
    }

    public void setLoanInterestAmount(BigDecimal loanInterestAmount) {
        this.loanInterestAmount = loanInterestAmount;
    }

    public Character getLoanInterestDate() {
        return loanInterestDate;
    }

    public void setLoanInterestDate(Character loanInterestDate) {
        this.loanInterestDate = loanInterestDate;
    }
}
