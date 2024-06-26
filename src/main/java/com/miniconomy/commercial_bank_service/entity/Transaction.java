package com.miniconomy.commercial_bank_service.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Transaction
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long transactionId;
  private Long creditAccountId;
  private Long debitAccountId;
  private String transactionDate;
  private BigDecimal transactionAmount;
  private String creditRef;
  private String debitRef;

  @Enumerated(EnumType.STRING)
  private TransactionStatusType transactionStatus;
}
