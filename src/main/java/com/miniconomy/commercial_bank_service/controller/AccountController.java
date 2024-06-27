package com.miniconomy.commercial_bank_service.controller;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miniconomy.commercial_bank_service.entity.Account;
import com.miniconomy.commercial_bank_service.response.AccountResponse;
import com.miniconomy.commercial_bank_service.response.BasicResponse;
import com.miniconomy.commercial_bank_service.service.AccountService;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Account", description = "Queries related to service's account")
@RestController
@RequestMapping("/account")
class AccountController {
    
  private final AccountService accountService;

  public AccountController(AccountService accountService)
  {
    this.accountService = accountService;
  }
  
  @Operation(
    summary = "Get services account balance",
    description = "Allows services to view their bank balances"
  )
  @GetMapping(value = "/balance", produces = "application/json")
  public ResponseEntity<?> getAccountBalance(@RequestParam String accountName)
  {
    Optional<Account> account = this.accountService.retrieveAccountBalance(accountName);
    if (account.isPresent()) {
      AccountResponse response = new AccountResponse(account.get().getAccountName(), 4000.0); // account balance is the sum of all transactions
      String message = "This is your account balance:";
      return ResponseEntity.status(HttpStatus.OK).body(createEntity("message", createEntity(message, response)));
    }
    String message = "You gave an invalid account name:";
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createEntity("message", message));
  }

  public HashMap<String, Object> createEntity(String x, Object y) {
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put(x, y);
    return map;
  }

}