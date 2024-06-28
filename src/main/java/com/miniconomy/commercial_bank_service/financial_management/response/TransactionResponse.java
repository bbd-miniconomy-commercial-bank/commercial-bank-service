package com.miniconomy.commercial_bank_service.financial_management.response;


import com.miniconomy.commercial_bank_service.financial_management.entity.TransactionStatusType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse
{
  private String debitorAccName;
  private String creditorAccName;
  private Long amount;
  private TransactionStatusType status;
  private String debitRef;
  private String creditRef;
  private String date;
}
