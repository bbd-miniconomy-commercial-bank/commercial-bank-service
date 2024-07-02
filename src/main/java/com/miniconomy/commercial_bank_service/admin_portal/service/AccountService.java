package com.miniconomy.commercial_bank_service.admin_portal.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.admin_portal.entity.Account;
import com.miniconomy.commercial_bank_service.admin_portal.repository.AccountRepository;

@Service
public class AccountService
{
  private final AccountRepository accountRepository;

  public AccountService(AccountRepository accountRepository)
  {
    this.accountRepository = accountRepository;
  }
  
  public Optional<Account> retrieveAccountBalance(String accountName)
  {
    return accountRepository.findByAccountName(accountName);
  } 

}
