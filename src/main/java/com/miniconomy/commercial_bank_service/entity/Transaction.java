package com.miniconomy.commercial_bank_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Transaction
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long creditAccId;
  private Long debitAccId;
  private String transactionDate;
  private Double transactionAmount;
  private String creditRef;
  private String debitRef;
  private String transactionStatus;

  // Getters
  public Long getTransactionId()
  {
    return id;
  } 

  public Long getcreditAccId()
  {
    return creditAccId;
  }

  public Long getdebitAccId()
  {
    return debitAccId;
  }

  public String getTransactionDate() 
  {
    return transactionDate;
  }

  public Double getTransactionAmount() 
  {
    return transactionAmount;
  }

  public String getCreditRef() 
  {
    return creditRef;
  }

  public String getDebitRef() 
  {
    return debitRef;
  }

  public String getTransactionStatus() 
  {
    return transactionStatus;
  }

  // Setters
  public void setTransactionId(Long id) 
  {
    this.id = id;
  }

  public void setCreditAccId(Long creditAccId) 
  {
    this.creditAccId = creditAccId;
  }

  public void setDebitAccId(Long debitAccId) 
  {
    this.debitAccId = debitAccId;
  }

  public void setTransactionDate(String transactionDate) 
  {
    this.transactionDate = transactionDate;
  }

  public void setTransactionAmount(Double transactionAmount) 
  {
    this.transactionAmount = transactionAmount;
  }

  public void setCreditRef(String creditRef) 
  {
    this.creditRef = creditRef;
  }

  public void setDebitRef(String debitRef) 
  {
    this.debitRef = debitRef;
  }

  public void setTransactionStatus(String transactionStatus) 
  {
    this.transactionStatus = transactionStatus;
  }
}
