package com.miniconomy.commercial_bank_service.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class DebitOrder
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long debitOrderId;
  private Long creditAccountId;
  private Long debitAccountId;
  private String debitOrderCreatedDate;
  private BigDecimal debitOrderAmount;
  private String debitOrderReceiverRef;
  private String debitOrderSenderRef;

  public DebitOrder() { }

  public DebitOrder(Long creditAccId, Long debitAccId, String dbDate, BigDecimal amount, String dbOrderRecRef, String dbOrderSendRef) {
    this.creditAccountId = creditAccId;
    this.debitAccountId = debitAccId;
    this.debitOrderCreatedDate = dbDate;
    this.debitOrderAmount = amount;
    this.debitOrderReceiverRef = dbOrderRecRef;
    this.debitOrderSenderRef = dbOrderSendRef;
  }
}
