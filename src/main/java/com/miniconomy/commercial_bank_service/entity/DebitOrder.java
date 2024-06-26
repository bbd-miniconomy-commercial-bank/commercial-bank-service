package com.miniconomy.commercial_bank_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DebitOrder
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long creditAccountId;
  private Long debitAccountId;
  private String debitOrderCreatedDate;
  private Double debitOrderAmount;
  private String debitOrderReceiverRef;
  private String debitOrderSenderRef;

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public Long getCreditAccountId()
  {
    return creditAccountId;
  }

  public void setCreditAccountId(Long creditAccountId)
  {
    this.creditAccountId = creditAccountId;
  }

  public Long getDebitAccountId()
  {
    return debitAccountId;
  }

  public void setDebitAccountId(Long debitAccountId)
  {
    this.debitAccountId = debitAccountId;
  }

  public String getDebitOrderCreatedDate()
  {
    return debitOrderCreatedDate;
  }

  public void setDebitOrderCreatedDate(String debitOrderCreatedDate)
  {
    this.debitOrderCreatedDate = debitOrderCreatedDate;
  }

  public Double getDebitOrderAmount()
  {
    return debitOrderAmount;
  }

  public void setDebitOrderAmount(Double debitOrderAmount)
  {
    this.debitOrderAmount = debitOrderAmount;
  }

  public String getDebitOrderReceiverRef()
  {
    return debitOrderReceiverRef;
  }

  public void setDebitOrderReceiverRef(String debitOrderReceiverRef)
  {
    this.debitOrderReceiverRef = debitOrderReceiverRef;
  }

  public String getDebitOrderSenderRef()
  {
    return debitOrderSenderRef;
  }

  public void setDebitOrderSenderRef(String debitOrderSenderRef)
  {
    this.debitOrderSenderRef = debitOrderSenderRef;
  }
}
