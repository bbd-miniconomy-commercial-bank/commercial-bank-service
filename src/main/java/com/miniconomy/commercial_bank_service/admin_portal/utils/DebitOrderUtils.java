package com.miniconomy.commercial_bank_service.admin_portal.utils;

import com.miniconomy.commercial_bank_service.admin_portal.entity.DebitOrder;
import com.miniconomy.commercial_bank_service.admin_portal.response.DebitOrderResponse;

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
