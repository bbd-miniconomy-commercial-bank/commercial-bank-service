package com.miniconomy.commercial_bank_service.financial_management.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("accountId", accountId);
        return namedParameterJdbcTemplate.query(sql, paramMap, accountRowMapper)
                .stream()
                .findFirst();
    }

    public Optional<Account> findByAccountName(String accountName) {
        String sql = "SELECT * FROM account WHERE account_name = :accountName";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("accountName", accountName);
        return namedParameterJdbcTemplate.query(sql, paramMap, accountRowMapper)
                .stream()
                .findFirst();
    }

    public Optional<Account> findByAccountCN(String accountCN) {
        String sql = "SELECT * FROM account WHERE account_cn = :accountCN";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("accountCN", accountCN);
        return namedParameterJdbcTemplate.query(sql, paramMap, accountRowMapper)
                .stream()
                .findFirst();
    }
}
