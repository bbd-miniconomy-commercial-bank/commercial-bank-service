package com.miniconomy.commercial_bank_service.financial_management.entity;

import java.util.UUID;

public class LoanTransaction {

    private UUID loanTransactionId;
    private UUID loanId;
    private UUID transactionId;

    // Constructors
    public LoanTransaction(UUID loanTransactionId, UUID loanId, UUID transactionId) {
        this.loanTransactionId = loanTransactionId;
        this.loanId = loanId;
        this.transactionId = transactionId;
    }

    // Getters and Setters
    public UUID getLoanTransactionId() {
        return loanTransactionId;
    }

    public void setLoanTransactionId(UUID loanTransactionId) {
        this.loanTransactionId = loanTransactionId;
    }

    public UUID getLoanId() {
        return loanId;
    }

    public void setLoanId(UUID loanId) {
        this.loanId = loanId;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }
}
