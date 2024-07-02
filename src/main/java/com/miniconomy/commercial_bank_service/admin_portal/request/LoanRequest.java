package com.miniconomy.commercial_bank_service.admin_portal.request;

import com.miniconomy.commercial_bank_service.financial_management.entity.LoanType;
import lombok.Data;

@Data
public class LoanRequest {
    private Long amount;
    private String accountName;
    private LoanType type;
}
