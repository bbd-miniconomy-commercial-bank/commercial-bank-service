package com.miniconomy.commercial_bank_service.financial_management.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutgoingRetailBankRequest {
    private Long toPersonaId;
    private long amountInMibiDough;
    private String reference;
    private String fromAccountId;
}
