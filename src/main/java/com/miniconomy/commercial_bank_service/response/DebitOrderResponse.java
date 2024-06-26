package com.miniconomy.commercial_bank_service.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DebitOrderResponse
{
  private Long creditAccountId;
  private Long debitAccountId;
  private String debitOrderCreatedDate;
  private Double debitOrderAmount;
  private String debitOrderReceiverRef;
  private String debitOrderSenderRef;
}
