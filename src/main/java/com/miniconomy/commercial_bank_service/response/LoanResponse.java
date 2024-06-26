package com.miniconomy.commercial_bank_service.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

import com.miniconomy.commercial_bank_service.entity.LoanType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanResponse {

    private Long loanId;
    private BigDecimal loanAmount;
    private LoanType loanType;
    private String accountName;
    private Set<LoanInterestResponse> loanInterests;
    private Set<LoanTransactionResponse> loanTransactions;
}
