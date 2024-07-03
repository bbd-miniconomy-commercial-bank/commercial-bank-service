package com.miniconomy.commercial_bank_service.financial_management.entity;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class LoanTransaction {
    private UUID loanTransactionId;
    private UUID loanId;
    private UUID transactionId;
}
