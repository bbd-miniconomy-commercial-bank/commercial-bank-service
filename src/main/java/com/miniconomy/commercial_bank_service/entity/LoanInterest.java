package com.miniconomy.commercial_bank_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "loan_interest")
public class LoanInterest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long loanInterestId;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    @Column(name = "loan_interest_rate", precision = 5, scale = 2)
    private BigDecimal loanInterestRate;

    @Column(name = "loan_interest_amount", precision = 13, scale = 3)
    private BigDecimal loanInterestAmount;

    @Column(name = "loan_interest_date")
    private Character loanInterestDate;

}
