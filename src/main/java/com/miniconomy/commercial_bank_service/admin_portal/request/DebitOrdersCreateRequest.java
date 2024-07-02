package com.miniconomy.commercial_bank_service.admin_portal.request;

import java.util.List;

import lombok.Data;

@Data
public class DebitOrdersCreateRequest {
    
    List<DebitOrderRequest> debitOrders;
}
