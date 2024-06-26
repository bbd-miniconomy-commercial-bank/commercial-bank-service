package com.miniconomy.commercial_bank_service.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionResponse
{
  private Long debitor;
  private Long creditor;
  private Double amount;
  private String status;
  private String debitRef;
  private String creditRef;
  private String date;
  
}
