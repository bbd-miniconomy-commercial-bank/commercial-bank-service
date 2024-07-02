package com.miniconomy.commercial_bank_service.financial_management.entity;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.miniconomy.commercial_bank_service.financial_management.enumeration.TransactionStatusType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction")
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

  @Column(name = "transaction_date", columnDefinition = "bpchar(8)")
  private String transactionDate;

  @Column(name = "transaction_amount", columnDefinition = "BIGINT")
  private Long transactionAmount;

  @Column(name = "credit_ref")
  private String creditRef;

  @Column(name = "debit_ref")
  private String debitRef;

  @Column(name = "transaction_status")
  @Enumerated(EnumType.STRING)
  private TransactionStatusType transactionStatus;

}
