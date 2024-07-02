package com.miniconomy.commercial_bank_service.admin_portal.utils;

import com.miniconomy.commercial_bank_service.admin_portal.entity.Loan;
import com.miniconomy.commercial_bank_service.admin_portal.response.LoanResponse;

public class LoanUtils {
    
    public static LoanResponse loanResponseMappper(Loan loan) {

        return new LoanResponse(
            loan.getLoanId(),
            loan.getLoanAmount(),
            loan.getLoanType(),
            loan.getAccount().getAccountName()
        );
    }
}
