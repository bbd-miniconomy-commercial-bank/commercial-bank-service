package com.miniconomy.commercial_bank_service.financial_management.entity;

import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.miniconomy.commercial_bank_service.financial_management.enumeration.LoanType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
