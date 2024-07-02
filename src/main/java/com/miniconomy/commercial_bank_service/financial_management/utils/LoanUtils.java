package com.miniconomy.commercial_bank_service.financial_management.utils;

import com.miniconomy.commercial_bank_service.financial_management.entity.Loan;
import com.miniconomy.commercial_bank_service.financial_management.response.LoanResponse;

public class LoanUtils {
    
    public static LoanResponse loanResponseMappper(Loan loan) {

        return new LoanResponse(
            loan.getLoanId(),
            loan.getLoanAmount(),
            loan.getLoanType(),
            loan.getAccountId().toString()
        );
    }
}
