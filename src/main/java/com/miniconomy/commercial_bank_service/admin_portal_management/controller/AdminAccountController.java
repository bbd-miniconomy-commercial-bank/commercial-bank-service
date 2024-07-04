package com.miniconomy.commercial_bank_service.admin_portal_management.controller;

import com.miniconomy.commercial_bank_service.financial_management.entity.Account;
import com.miniconomy.commercial_bank_service.financial_management.response.*;
import com.miniconomy.commercial_bank_service.financial_management.service.AccountService;
import com.miniconomy.commercial_bank_service.financial_management.utils.AccountUtils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin")
class AdminAccountController {
    
  private final AccountService accountService;

  public AdminAccountController(AccountService accountService)
  {
    this.accountService = accountService;
  }
  
  @GetMapping(value = "/accounts", produces = "application/json")
  public ResponseEntity<ResponseTemplate<ListResponseTemplate<AccountResponse>>> getAllAccounts() 
  {
    ResponseTemplate<ListResponseTemplate<AccountResponse>> response = new ResponseTemplate<>();
    int status = HttpStatus.OK.value();

    List<Account> accounts = this.accountService.getAllAccounts();

    List<AccountResponse> accountsResponse = accounts.stream().map(
      (acc) -> AccountUtils.accountResponseMapper(acc)
    ).collect(Collectors.toList());

    ListResponseTemplate<AccountResponse> listResponseTemplate = new ListResponseTemplate<>(1, 10, accountsResponse);
    response.setData(listResponseTemplate);

    response.setStatus(status);
    return ResponseEntity.status(status).body(response);
  }

}