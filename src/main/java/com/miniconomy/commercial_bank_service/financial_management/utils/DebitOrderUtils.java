package com.miniconomy.commercial_bank_service.financial_management.utils;

import com.miniconomy.commercial_bank_service.financial_management.entity.DebitOrder;
import com.miniconomy.commercial_bank_service.financial_management.response.DebitOrderResponse;

public class DebitOrderUtils {
    
    public static DebitOrderResponse debitOrderResponseMapper(DebitOrder debitOrder) {

        return new DebitOrderResponse(
            debitOrder.getDebitOrderId(),
            debitOrder.getDebitAccount().getAccountName(),
            debitOrder.getCreditAccount().getAccountName(),
            debitOrder.getDebitOrderCreatedDate(),
            debitOrder.getDebitOrderAmount(),
            debitOrder.getDebitOrderSenderRef(),
            debitOrder.getDebitOrderReceiverRef(),
            debitOrder.isDebitOrderDisabled()
        );
    }
}
