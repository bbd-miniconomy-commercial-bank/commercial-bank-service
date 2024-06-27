package com.miniconomy.commercial_bank_service.response;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DebitOrderResponse
{
  private UUID debitOrderId;
  private String creditAccountName;
  private String debitAccountName;
  private String debitOrderCreatedDate;
  private BigDecimal debitOrderAmount;
  private String debitOrderReceiverRef;
  private String debitOrderSenderRef;
  private boolean debitOrderDisabled;
}
