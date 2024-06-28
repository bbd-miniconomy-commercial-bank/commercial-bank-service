package com.miniconomy.commercial_bank_service.entity;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "loan")
public class Loan {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(name="loan_id")
    private UUID loanId;

    @Column(name="loan_amount", columnDefinition = "BIGINT")
    private Long loanAmount;

    @Column(name="loan_type")
    @Enumerated(EnumType.STRING)
    private LoanType loanType;

    @ManyToOne(cascade = CascadeType.ALL) 
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL)
    private Set<LoanInterest> loanInterests;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL)
    private Set<LoanTransaction> loanTransactions;

    // Getters and Setters

    public UUID getLoanId() {
        return loanId;
    }

    public void setLoanId(UUID loanId) {
        this.loanId = loanId;
    }

    public Long getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Long loanAmount) {
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
