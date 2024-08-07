package com.miniconomy.commercial_bank_service.financial_management.response;

import com.miniconomy.commercial_bank_service.financial_management.enumeration.TransactionStatusEnum;

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
  private String reference;
  private Long amount;
  private String date;
  private TransactionStatusEnum status;
}
