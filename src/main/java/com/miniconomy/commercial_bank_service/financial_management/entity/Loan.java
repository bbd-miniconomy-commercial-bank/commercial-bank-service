package com.miniconomy.commercial_bank_service.financial_management.entity;

import java.util.UUID;

public class Loan {

    private UUID loanId;
    private UUID accountId;
    private long loanAmount;
    private LoanType loanType;
    private String loanCreatedDate; 

    // Getters and Setters
    public UUID getLoanId() {
        return loanId;
    }

    public void setLoanId(UUID loanId) {
        this.loanId = loanId;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public long getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(long loanAmount) {
        this.loanAmount = loanAmount;
    }

    public LoanType getLoanType() {
        return loanType;
    }

    public void setLoanType(LoanType loanType) {
        this.loanType = loanType;
    }

    public String getLoanCreatedDate() {
        return loanCreatedDate;
    }

    public void setLoanCreatedDate(String loanCreatedDate) {
        this.loanCreatedDate = loanCreatedDate;
    }
}
