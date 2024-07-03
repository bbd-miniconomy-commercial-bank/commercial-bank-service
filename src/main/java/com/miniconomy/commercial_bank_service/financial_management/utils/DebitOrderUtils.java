package com.miniconomy.commercial_bank_service.financial_management.utils;

import com.miniconomy.commercial_bank_service.financial_management.entity.DebitOrder;
import com.miniconomy.commercial_bank_service.financial_management.request.DebitOrderRequest;
import com.miniconomy.commercial_bank_service.financial_management.response.DebitOrderResponse;
import com.miniconomy.commercial_bank_service.simulation_management.store.SimulationStore;

public class DebitOrderUtils {
    
    public static DebitOrderResponse debitOrderResponseMapper(DebitOrder debitOrder) {

        return new DebitOrderResponse(
            debitOrder.getDebitOrderId(),
            debitOrder.getDebitAccountName(),
            debitOrder.getCreditAccountName(),
            debitOrder.getDebitOrderCreatedDate(),
            debitOrder.getDebitOrderAmount(),
            debitOrder.getDebitOrderDebitRef(),
            debitOrder.getDebitOrderCreditRef(),
            debitOrder.isDebitOrderDisabled()
        );
    }
    
    public static DebitOrder debitOrderMapper(DebitOrderRequest debitOrderRequest) {

        return new DebitOrder(
            null,
            debitOrderRequest.getDebitAccountName(),
            debitOrderRequest.getCreditAccountName(),
            debitOrderRequest.getDebitRef(),
            debitOrderRequest.getCreditRef(),
            debitOrderRequest.getAmount(),
            SimulationStore.getCurrentDate(),
            false
        );
    }
}
