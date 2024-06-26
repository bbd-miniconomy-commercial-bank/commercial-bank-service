package com.miniconomy.commercial_bank_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miniconomy.commercial_bank_service.entity.Account;
import com.miniconomy.commercial_bank_service.response.AccountResponse;
import com.miniconomy.commercial_bank_service.response.BasicResponse;
import com.miniconomy.commercial_bank_service.service.AccountService;

import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Account", description = "Queries related to service's account")
@RestController
@RequestMapping("/account")
class AccountController {
    
  final AccountService accountService;

  public AccountController(AccountService accountService)
  {
    this.accountService = accountService;
  }
  
  @Operation(
    summary = "Get services account balance",
    description = "Allows services to view their bank balances"
  )
  @GetMapping(value = "/balance", produces = "application/json")
  public BasicResponse<AccountResponse> getAccountBalance(@RequestParam String accountName)
  {
    Account account = this.accountService.retrieveAccountBalance(accountName);
    AccountResponse response = new AccountResponse(account.getAccountName(), null); //TODO: Calculate balance from transactions
    return new BasicResponse<AccountResponse>(response);
  }

}