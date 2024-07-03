package com.miniconomy.commercial_bank_service.financial_management.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RetailBankCallbackRequest {
    private boolean processedSuccessfully;
    private int rejectionCode;
    private String rejectionReason;
}
