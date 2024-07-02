package com.miniconomy.commercial_bank_service.admin_portal.utils;

import com.miniconomy.commercial_bank_service.admin_portal.entity.Account;
import com.miniconomy.commercial_bank_service.admin_portal.response.AccountResponse;

public class AccountUtils {
 
    public static AccountResponse accountResponseMapper(Account account) {

        return new AccountResponse(
            account.getAccountName(), 
            4000 // Fix :)
        );
    }
}
