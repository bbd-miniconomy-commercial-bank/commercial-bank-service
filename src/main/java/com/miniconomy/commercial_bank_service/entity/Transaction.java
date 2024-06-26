package com.miniconomy.commercial_bank_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
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

  public Transaction() {

  }

  public Transaction(
    Long creditAccId, 
    Long debitAccId, 
    String transactionDate, 
    Double amount, 
    String cref, 
    String dref, 
    String transactionStatus
  ) {
    
  }
}
