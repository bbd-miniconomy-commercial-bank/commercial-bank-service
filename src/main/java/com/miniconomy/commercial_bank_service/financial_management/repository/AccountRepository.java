package com.miniconomy.commercial_bank_service.financial_management.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.miniconomy.commercial_bank_service.financial_management.entity.Account;

@Repository
public class AccountRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final RowMapper<Account> accountRowMapper = (rs, rowNum) -> {
        Account account = new Account();
        account.setAccountId(UUID.fromString(rs.getString("account_id")));
        account.setAccountName(rs.getString("account_name"));
        account.setAccountCn(rs.getString("account_cn"));
        account.setAccountNotificationEndpoint(rs.getString("account_notification_endpoint"));
        return account;
    };

    public Optional<Account> findById(UUID accountId) {
        String sql = "SELECT * FROM account WHERE account_id = :accountId";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("accountId", accountId);
        return namedParameterJdbcTemplate.query(sql, paramMap, accountRowMapper)
                .stream()
                .findFirst();
    }

    public Optional<Account> findByAccountName(String accountName) {
        String sql = "SELECT * FROM account WHERE account_name = :accountName";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("accountName", accountName);
        return namedParameterJdbcTemplate.query(sql, paramMap, accountRowMapper)
                .stream()
                .findFirst();
    }

    public Account save(Account account) {
        String sql = "INSERT INTO account (account_id, account_name, account_cn, account_notification_endpoint) " +
                     "VALUES (:accountId, :accountName, :accountCn, :accountNotificationEndpoint)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("accountId", account.getAccountId());
        paramMap.put("accountName", account.getAccountName());
        paramMap.put("accountCn", account.getAccountCn());
        paramMap.put("accountNotificationEndpoint", account.getAccountNotificationEndpoint());
        namedParameterJdbcTemplate.update(sql, paramMap);
        return account;
    }


    public void update(Account account) {
        String sql = "UPDATE account SET account_notification_endpoint = :accountNotificationEndpoint WHERE account_id = :accountId";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("accountNotificationEndpoint", account.getAccountNotificationEndpoint());
        paramMap.put("accountId", account.getAccountId());
        namedParameterJdbcTemplate.update(sql, paramMap);
    }

    public void deleteById(UUID accountId) {
        String sql = "DELETE FROM account WHERE account_id = :accountId";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("accountId", accountId);
        namedParameterJdbcTemplate.update(sql, paramMap);
    }
}
