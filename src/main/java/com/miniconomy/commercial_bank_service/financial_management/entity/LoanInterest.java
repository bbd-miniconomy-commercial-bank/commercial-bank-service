package com.miniconomy.commercial_bank_service.financial_management.entity;

import java.util.UUID;

public class LoanInterest {

    private UUID loanInterestId;
    private String loanType;
    private double interestRate;

    // Getters and setters
    public UUID getLoanInterestId() {
        return loanInterestId;
    }

    public void setLoanInterestId(UUID loanInterestId) {
        this.loanInterestId = loanInterestId;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}
