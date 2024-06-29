package com.miniconomy.commercial_bank_service.financial_management.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

import com.miniconomy.commercial_bank_service.financial_management.entity.LoanType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanResponse {

    private UUID loanId;
    private Long loanAmount;
    private LoanType loanType;
    private String accountName;
    //private Set<LoanInterestResponse> loanInterests;
    //private Set<LoanTransactionResponse> loanTransactions;
}
