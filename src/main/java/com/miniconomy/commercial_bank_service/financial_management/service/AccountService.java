package com.miniconomy.commercial_bank_service.financial_management.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.financial_management.entity.Account;
import com.miniconomy.commercial_bank_service.financial_management.repository.AccountRepository;

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

  public Optional<Account> retrieveAccountByCn(String cn)
  {
    return accountRepository.findByAccountCN(cn);
  }
}
