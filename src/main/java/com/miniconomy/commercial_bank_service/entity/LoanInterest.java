package com.miniconomy.commercial_bank_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "loan_interest")
public class LoanInterest {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(name = "loan_interest_id")
    private UUID loanInterestId;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    @Column(name = "loan_interest_rate", precision = 5, scale = 2)
    private Long loanInterestRate;

    @Column(name = "loan_interest_amount", columnDefinition = "BIGINT")
    private Long loanInterestAmount;

    @Column(name = "loan_interest_date", columnDefinition = "CHAR(8)")
    private String loanInterestDate;

}
