package com.miniconomy.commercial_bank_service.service;

import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.entity.Account;
import com.miniconomy.commercial_bank_service.repository.AccountRepository;

@Service
public class AccountService
{
  private final AccountRepository accountRepository;

  public AccountService(AccountRepository accountRepository)
  {
    this.accountRepository = accountRepository;
  }
  
  public Account retrieveAccountBalance(String accountName)
  {
    return accountRepository.findByAccountName(accountName);
  } 

}
