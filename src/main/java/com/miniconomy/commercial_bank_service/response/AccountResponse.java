package com.miniconomy.commercial_bank_service.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountResponse
{
  private String accountName;
  private BigDecimal accountBalance;  
}
