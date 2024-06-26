package com.miniconomy.commercial_bank_service.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanInterestResponse {

    private Long loanInterestId;
    private BigDecimal loanInterestRate;
    private BigDecimal loanInterestAmount;
    private Character loanInterestDate;
}
