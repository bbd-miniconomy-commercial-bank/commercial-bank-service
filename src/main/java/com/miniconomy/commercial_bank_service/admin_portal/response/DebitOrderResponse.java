package com.miniconomy.commercial_bank_service.admin_portal.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
