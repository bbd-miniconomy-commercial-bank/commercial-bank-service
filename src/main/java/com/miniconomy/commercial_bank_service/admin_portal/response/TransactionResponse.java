package com.miniconomy.commercial_bank_service.admin_portal.response;


import com.miniconomy.commercial_bank_service.admin_portal.entity.TransactionStatusType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
