package com.miniconomy.commercial_bank_service.financial_management.response;


import com.miniconomy.commercial_bank_service.financial_management.entity.TransactionStatusType;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class TransactionResponse
{
  private UUID id;
  private String debitAccountName;
  private String creditAccountName;
  private Long amount;
  private TransactionStatusType status;
  private String transactionRef;
  private String date;
}
