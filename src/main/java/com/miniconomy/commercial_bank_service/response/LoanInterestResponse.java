package com.miniconomy.commercial_bank_service.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanInterestResponse {
    
    private UUID loanInterestId;
    private BigDecimal loanInterestRate;
    private BigDecimal loanInterestAmount;
    private Character loanInterestDate;
}
