package com.miniconomy.commercial_bank_service.entity;

import java.math.BigDecimal;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Transaction
{
  @Id
  @UuidGenerator(style = UuidGenerator.Style.RANDOM)
  @Column(name = "transaction_id")
  private UUID transactionId;

  @ManyToOne(cascade = CascadeType.ALL) 
  @JoinColumn(name = "credit_account_id")
  private Account creditAccount;

  @ManyToOne(cascade = CascadeType.ALL) 
  @JoinColumn(name = "debit_account_id")
  private Account debitAccount;

  @Column(name = "transaction_date")
  private String transactionDate;

  @Column(name = "transaction_amount")
  private BigDecimal transactionAmount;

  @Column(name = "credit_ref")
  private String creditRef;

  @Column(name = "dedit_ref")
  private String debitRef;

  @Column(name = "transaction_status")
  @Enumerated(EnumType.STRING)
  private TransactionStatusType transactionStatus;

}
