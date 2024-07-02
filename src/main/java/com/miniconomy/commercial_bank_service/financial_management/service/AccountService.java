package com.miniconomy.commercial_bank_service.financial_management.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.financial_management.entity.Account;
import com.miniconomy.commercial_bank_service.financial_management.repository.AccountRepository;

@Service
public class AccountService
{
  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  private final AccountRepository accountRepository;

  public AccountService(AccountRepository accountRepository)
  {
    this.accountRepository = accountRepository;
  }
  
  public Optional<Account> retrieveAccountBalance(String accountName)
  {
    return accountRepository.findByAccountName(accountName);
  } 

  public String findAccountNameByCn(String cn)
  {
    String sql = "SELECT account_name FROM account WHERE account_cn = :cn";
    SqlParameterSource parameters = new MapSqlParameterSource().addValue("cn", cn);
    return jdbcTemplate.queryForObject(sql, parameters, String.class);
  }
}
