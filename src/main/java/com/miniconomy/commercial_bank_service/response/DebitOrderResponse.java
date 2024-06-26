package com.miniconomy.commercial_bank_service.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DebitOrderResponse
{
  private String creditAccountId;
  private String debitAccountId;
  private String debitOrderCreatedDate;
  private BigDecimal debitOrderAmount;
  private String debitOrderReceiverRef;
  private String debitOrderSenderRef;
}
