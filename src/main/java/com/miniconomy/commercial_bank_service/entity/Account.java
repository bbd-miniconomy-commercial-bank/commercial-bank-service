package com.miniconomy.commercial_bank_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Account {
  @Id
  private Long id;
  private String accountName;

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAccountName() {
    return accountName;
  }

  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }
}
