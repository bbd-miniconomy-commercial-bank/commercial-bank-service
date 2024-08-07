package com.miniconomy.commercial_bank_service.financial_management.repository;

import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;
import com.miniconomy.commercial_bank_service.financial_management.enumeration.TransactionStatusEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;

@Repository
public class TransactionRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final RowMapper<Transaction> transactionRowMapper = (rs, rowNum) -> {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.fromString(rs.getString("transaction_id")));
        transaction.setDebitAccountName(rs.getString("debit_account_name"));
        transaction.setCreditAccountName(rs.getString("credit_account_name"));
        transaction.setTransactionDebitRef(rs.getString("transaction_debit_ref"));
        transaction.setTransactionCreditRef(rs.getString("transaction_credit_ref"));
        transaction.setTransactionAmount(rs.getLong("transaction_amount"));
        transaction.setTransactionDate(rs.getString("transaction_date"));
        transaction.setTransactionStatus(TransactionStatusEnum.valueOf(rs.getString("transaction_status")));
        return transaction;
    };

    public List<Transaction> findByAccountName(String accountName, Pageable pageable) {
        String sql = "SELECT * FROM account_transaction_view WHERE credit_account_name = :accountName OR debit_account_name = :accountName LIMIT :limit OFFSET :offset";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("accountName", accountName)
            .addValue("limit", pageable.getPageSize())
            .addValue("offset", pageable.getOffset());
            
        try {
            return namedParameterJdbcTemplate.query(sql, paramMap, transactionRowMapper);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<Transaction> findByAccountNameAndDate(String accountName, String year, Pageable pageable) {
        String sql = "SELECT * FROM account_transaction_view WHERE transaction_date LIKE ':year|__|__' AND credit_account_name = :accountName OR debit_account_name = :accountName LIMIT :limit OFFSET :offset";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("accountName", accountName)
            .addValue("year", year)
            .addValue("limit", pageable.getPageSize())
            .addValue("offset", pageable.getOffset());
            
        try {
            return namedParameterJdbcTemplate.query(sql, paramMap, transactionRowMapper);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public Optional<Transaction> findById(UUID id, String accountName) {
        String sql = "SELECT * FROM account_transaction_view WHERE transaction_id = :transactionId AND account_name = :accountName";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("transactionId", id.toString())
            .addValue("accountName", accountName);

        try {
            return namedParameterJdbcTemplate.query(sql, paramMap, transactionRowMapper)
                .stream()
                .findFirst();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Transaction> insert(Transaction transaction) {
        String sql = "SELECT * " +
                     "FROM insert_and_return_transaction(:debitAccountName, :creditAccountName, :tranasactionDebitRef, :tranasactionCreditRef, :transactionAmount, :transactionDate, :transactionStatus)";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("debitAccountName", transaction.getDebitAccountName())
            .addValue("creditAccountName", transaction.getCreditAccountName())
            .addValue("tranasactionDebitRef", transaction.getTransactionDebitRef())
            .addValue("tranasactionCreditRef", transaction.getTransactionCreditRef())
            .addValue("transactionAmount", transaction.getTransactionAmount())
            .addValue("transactionDate", transaction.getTransactionDate())
            .addValue("transactionStatus", transaction.getTransactionStatus().toString());

        try {
            return namedParameterJdbcTemplate.query(sql, paramMap, transactionRowMapper)
                .stream()
                .findFirst();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Transaction> update(Transaction transaction) {
        String sql = "SELECT * " +
                     "FROM update_and_return_transaction(:transactionId, :debitAccountName, :creditAccountName, :tranasactionDebitRef, :tranasactionCreditRef, :transactionAmount, :transactionDate, :transactionStatus)";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("transactionId", transaction.getTransactionId().toString())
            .addValue("debitAccountName", transaction.getDebitAccountName())
            .addValue("creditAccountName", transaction.getCreditAccountName())
            .addValue("tranasactionDebitRef", transaction.getTransactionDebitRef())
            .addValue("tranasactionCreditRef", transaction.getTransactionCreditRef())
            .addValue("transactionAmount", transaction.getTransactionAmount())
            .addValue("transactionDate", transaction.getTransactionDate())
            .addValue("transactionStatus", transaction.getTransactionStatus().toString());

        try {
            return namedParameterJdbcTemplate.query(sql, paramMap, transactionRowMapper)
                .stream()
                .findFirst();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}


