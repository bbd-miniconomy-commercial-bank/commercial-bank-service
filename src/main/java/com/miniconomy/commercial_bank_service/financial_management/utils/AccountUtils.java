package com.miniconomy.commercial_bank_service.financial_management.utils;

import com.miniconomy.commercial_bank_service.financial_management.entity.Account;
import com.miniconomy.commercial_bank_service.financial_management.response.AccountResponse;

public class AccountUtils {
 
    public static AccountResponse accountResponseMapper(Account account) {

        return new AccountResponse(
            account.getAccountName(), 
            4000 // Fix :)
        );
    }
}
