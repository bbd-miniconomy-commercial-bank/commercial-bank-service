package com.miniconomy.commercial_bank_service.entity;

import java.math.BigDecimal;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long loanId;

    @Column(precision = 13, scale = 3)
    private BigDecimal loanAmount;

    @Enumerated(EnumType.STRING)
    private LoanType loanType;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL)
    private Set<LoanInterest> loanInterests;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL)
    private Set<LoanTransaction> loanTransactions;

    // Getters and Setters

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public LoanType getLoanType() {
        return loanType;
    }

    public void setLoanType(LoanType loanType) {
        this.loanType = loanType;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Set<LoanInterest> getLoanInterests() {
        return loanInterests;
    }

    public void setLoanInterests(Set<LoanInterest> loanInterests) {
        this.loanInterests = loanInterests;
    }

    public Set<LoanTransaction> getLoanTransactions() {
        return loanTransactions;
    }

    public void setLoanTransactions(Set<LoanTransaction> loanTransactions) {
        this.loanTransactions = loanTransactions;
    } 
}
