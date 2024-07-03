package com.miniconomy.commercial_bank_service.financial_management.controller;

import org.springframework.web.bind.annotation.RestController;

import com.miniconomy.commercial_bank_service.financial_management.entity.Account;
import com.miniconomy.commercial_bank_service.financial_management.response.AccountResponse;
import com.miniconomy.commercial_bank_service.financial_management.response.ResponseTemplate;
import com.miniconomy.commercial_bank_service.financial_management.service.AccountService;
import com.miniconomy.commercial_bank_service.financial_management.utils.AccountUtils;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Account", description = "Queries related to service's account")
@RestController
@RequestMapping("/account")
public class AccountController {
    
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
  public ResponseEntity<ResponseTemplate<AccountResponse>> getAccountBalance(@RequestAttribute String accountName)
  {

    ResponseTemplate<AccountResponse> response = new ResponseTemplate<>();
    int status = HttpStatus.OK.value();

    Optional<Account> accountOptional = this.accountService.retrieveAccountBalance(accountName);

    if (accountOptional.isPresent()) {
      Account account = accountOptional.get();
      AccountResponse accountResponse = AccountUtils.accountResponseMapper(account);
      response.setData(accountResponse);
    } else {
      status = HttpStatus.NOT_FOUND.value();
      response.setMessage("Account not found");
    }

    response.setStatus(status);
    return ResponseEntity.status(status).body(response);
  }

}