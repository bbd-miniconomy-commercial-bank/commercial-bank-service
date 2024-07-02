package com.miniconomy.commercial_bank_service.financial_management.request;

import com.miniconomy.commercial_bank_service.financial_management.enumeration.NotificationTypeEnum;
import com.miniconomy.commercial_bank_service.financial_management.response.TransactionResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationRequest {
    
    NotificationTypeEnum type;
    TransactionResponse transaction;
}
