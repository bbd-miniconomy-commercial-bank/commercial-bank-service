package com.miniconomy.commercial_bank_service.response;

import java.math.BigDecimal;

import com.miniconomy.commercial_bank_service.entity.TransactionStatusType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionResponse
{
  private String debitor;
  private String creditor;
  private BigDecimal amount;
  private TransactionStatusType status;
  private String debitRef;
  private String creditRef;
  private String date;
}
