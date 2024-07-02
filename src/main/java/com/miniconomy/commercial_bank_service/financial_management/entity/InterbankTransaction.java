package com.miniconomy.commercial_bank_service.financial_management.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import com.miniconomy.commercial_bank_service.financial_management.enumeration.InterbankTransactionStatusEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterbankTransaction {
    
    UUID interbankTransactionId;
    UUID transactionId;
    InterbankTransactionStatusEnum interbankTransactionStatus;
}
