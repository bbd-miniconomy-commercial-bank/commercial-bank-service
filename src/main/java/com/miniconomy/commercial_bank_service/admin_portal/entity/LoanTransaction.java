package com.miniconomy.commercial_bank_service.admin_portal.entity;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "loan_transaction")
public class LoanTransaction {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(name = "loan_transaction_id")
    private UUID loanTransactionId;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    @Column(name = "transaction_id")
    private UUID transactionId;

    // Getters and Setters
/*
    public UUID getLoanTransactionId() {
        return loanTransactionId;
    }

    public void setLoanTransactionId(UUID loanTransactionId) {
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

    public void setTransactionId(Long  transactionId) {
        this.transactionId = transactionId;
    }*/
}
