package com.miniconomy.commercial_bank_service.entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class DebitOrder
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private UUID debitOrderId;
  private UUID creditAccountId;
  private UUID debitAccountId;
  private String debitOrderCreatedDate;
  private BigDecimal debitOrderAmount;
  private String debitOrderReceiverRef;
  private String debitOrderSenderRef;
}
