package com.miniconomy.commercial_bank_service.admin_portal.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import com.miniconomy.commercial_bank_service.admin_portal.entity.LoanType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanResponse {

    private UUID id;
    private Long amount;
    private LoanType type;
    private String accountName;
    
}
