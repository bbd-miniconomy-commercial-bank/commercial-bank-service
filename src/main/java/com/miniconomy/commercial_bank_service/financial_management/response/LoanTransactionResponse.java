package com.miniconomy.commercial_bank_service.financial_management.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoanTransactionResponse {

    private UUID loanTransactionId;
    private UUID transactionId;
}
