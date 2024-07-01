package com.miniconomy.commercial_bank_service.financial_management.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterbankTransaction {
    
    UUID interbankTransactionId;
    UUID transactionId;
    String interbankTransactionStatus;
}
