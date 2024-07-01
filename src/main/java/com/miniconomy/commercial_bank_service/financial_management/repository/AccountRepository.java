package com.miniconomy.commercial_bank_service.financial_management.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.miniconomy.commercial_bank_service.financial_management.entity.Account;

@Repository
public class AccountRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Account> accountRowMapper = (rs, rowNum) -> {
        Account account = new Account();
        account.setAccountId(UUID.fromString(rs.getString("account_id")));
        account.setAccountName(rs.getString("account_name"));
        account.setAccountCn(rs.getString("account_cn"));
        account.setAccountNotificationEndpoint(rs.getString("account_notification_endpoint"));
        return account;
    };

    public Optional<Account> findById(UUID accountId) {
        String sql = "SELECT * FROM account WHERE account_id = ?";
        return jdbcTemplate.query(sql, accountRowMapper, accountId)
                .stream()
                .findFirst();
    }

    public Optional<Account> findByAccountName(String accountName) {
        String sql = "SELECT * FROM account WHERE account_name = ?";
        return jdbcTemplate.query(sql, accountRowMapper, accountName)
                .stream()
                .findFirst();
    }

    public Account save(Account account) {
        String sql = "INSERT INTO account (account_id, account_name, account_cn, account_notification_endpoint) " +
                     "VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, account.getAccountId(), account.getAccountName(), account.getAccountCn(), account.getAccountNotificationEndpoint());
        return account;
    }

    public void update(Account account) {
        String sql = "UPDATE account SET account_notification_endpoint = ? WHERE account_id = ?";
        jdbcTemplate.update(sql, account.getAccountNotificationEndpoint(), account.getAccountId());
    }

    public void deleteById(UUID accountId) {
        String sql = "DELETE FROM account WHERE account_id = ?";
        jdbcTemplate.update(sql, accountId);
    }
}
