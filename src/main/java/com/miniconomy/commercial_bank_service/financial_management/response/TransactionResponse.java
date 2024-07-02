package com.miniconomy.commercial_bank_service.financial_management.response;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

import com.miniconomy.commercial_bank_service.financial_management.enumeration.TransactionStatusType;

@Data
@AllArgsConstructor
public class TransactionResponse
{
  private UUID id;
  private String debitAccountName;
  private String creditAccountName;
  private Long amount;
  private TransactionStatusType status;
  private String reference;
  private String date;
}
