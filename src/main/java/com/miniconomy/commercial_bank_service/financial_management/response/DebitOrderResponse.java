package com.miniconomy.commercial_bank_service.financial_management.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DebitOrderResponse
{
  private UUID id;
  private String debitAccountName;
  private String creditAccountName;
  private String creationDate;
  private Long amount;
  private String senderRef;
  private String receiverRef;
  private boolean disabled;
}
