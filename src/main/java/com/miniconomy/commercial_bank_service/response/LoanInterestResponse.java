package com.miniconomy.commercial_bank_service.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanInterestResponse {
    
    private UUID loanInterestId;
    private Long loanInterestRate;
    private Long loanInterestAmount;
    private String loanInterestDate;
}
