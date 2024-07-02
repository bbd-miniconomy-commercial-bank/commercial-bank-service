package com.miniconomy.commercial_bank_service.admin_portal.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountResponse
{
  private String accountName;
  private Integer accountBalance;  
}
