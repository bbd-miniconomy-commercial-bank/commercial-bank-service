package com.miniconomy.commercial_bank_service.financial_management.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutgoingRetailBankRequest {
    private long toPersonaId;
    private long amountInMibiDough;
    private String transactionRef;
    private String fromAccountId;
}
