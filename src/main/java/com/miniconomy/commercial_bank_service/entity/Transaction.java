package com.miniconomy.commercial_bank_service.entity;

import java.math.BigDecimal;
import java.util.UUID;

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
  private UUID transactionId;
  private UUID creditAccountId;
  private UUID debitAccountId;
  private String transactionDate;
  private BigDecimal transactionAmount;
  private String creditRef;
  private String debitRef;

  @Enumerated(EnumType.STRING)
  private TransactionStatusType transactionStatus;
}
