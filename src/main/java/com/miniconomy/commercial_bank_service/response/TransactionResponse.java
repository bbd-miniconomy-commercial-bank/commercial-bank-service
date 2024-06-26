package com.miniconomy.commercial_bank_service.response;

import java.math.BigDecimal;

import com.miniconomy.commercial_bank_service.entity.TransactionStatusType;

import com.miniconomy.commercial_bank_service.entity.TransactionStatusType;

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
  private BigDecimal amount;
  private TransactionStatusType status;
  private String debitRef;
  private String creditRef;
  private String date;
}
