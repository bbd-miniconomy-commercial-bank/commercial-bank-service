package com.miniconomy.commercial_bank_service.financial_management.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountResponse
{
  private String accountName;
  private Double accountBalance;  
}
