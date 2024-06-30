package com.miniconomy.commercial_bank_service.financial_management.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.financial_management.entity.Account;
import com.miniconomy.commercial_bank_service.financial_management.entity.AccountDelegation;
import com.miniconomy.commercial_bank_service.financial_management.repository.AccountDelegationRepository;
import com.miniconomy.commercial_bank_service.financial_management.repository.AccountRepository;

@Service
public class AccountService
{
  private final AccountRepository accountRepository;
  private final AccountDelegationRepository accDelegationRepo;

  public AccountService(AccountRepository accountRepository, AccountDelegationRepository acdR)
  {
    this.accountRepository = accountRepository;
    this.accDelegationRepo = acdR;
  }
  
  public Optional<Account> retrieveAccountBalance(String accountName)
  {
    return accountRepository.findByAccountName(accountName);
  } 

}
