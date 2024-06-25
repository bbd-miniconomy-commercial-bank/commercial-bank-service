package com.miniconomy.commercial_bank_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Account {
  @Id
  private Long accountId;
  private String accountName;

  // Getters and Setters
  public Long getId() {
    return accountId;
  }

  public void setId(Long accountId) {
    this.accountId = accountId;
  }

  public String getAccountName() {
    return accountName;
  }

  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }
}
