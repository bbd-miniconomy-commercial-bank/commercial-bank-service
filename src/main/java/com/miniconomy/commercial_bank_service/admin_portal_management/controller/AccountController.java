package com.miniconomy.commercial_bank_service.admin_portal_management.controller;

import com.miniconomy.commercial_bank_service.financial_management.entity.Account;
import com.miniconomy.commercial_bank_service.financial_management.response.*;
import com.miniconomy.commercial_bank_service.financial_management.service.AccountService;
import com.miniconomy.commercial_bank_service.financial_management.utils.AccountUtils;
import com.miniconomy.commercial_bank_service.financial_management.utils.DebitOrderUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin")
class AccountController {
    
  private final AccountService accountService;

  public AccountController(AccountService accountService)
  {
    this.accountService = accountService;
  }
  
  @GetMapping(value = "/accounts", produces = "application/json")
  public ResponseEntity<ResponseTemplate<ListResponseTemplate<AccountResponse>>> getAllAccounts(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize)
  {

    ResponseTemplate<ListResponseTemplate<AccountResponse>> response = new ResponseTemplate<>();
    int status = HttpStatus.OK.value();

    Pageable pageable = PageRequest.of(page, pageSize);
    List<Account> accounts = this.accountService.getAllAccounts(pageable);

    List<AccountResponse> accountsResponse = accounts.stream().map(
      (acc) -> AccountUtils.accountResponseMapper(acc)
    ).collect(Collectors.toList());

    ListResponseTemplate<AccountResponse> listResponseTemplate = new ListResponseTemplate<>(page, pageSize, accountsResponse);
    response.setData(listResponseTemplate);

    response.setStatus(status);
    return ResponseEntity.status(status).body(response);
  }

}