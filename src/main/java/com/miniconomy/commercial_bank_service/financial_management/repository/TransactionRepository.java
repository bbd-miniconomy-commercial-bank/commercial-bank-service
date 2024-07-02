package com.miniconomy.commercial_bank_service.financial_management.repository;

import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;
import com.miniconomy.commercial_bank_service.financial_management.entity.TransactionStatusType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;

@Repository
public class TransactionRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final String SQL_FIND_BY_DEBIT_ACCOUNT =
            "SELECT * FROM transaction WHERE debit_account_id = :accountId LIMIT :limit OFFSET :offset";

    private final String SQL_FIND_BY_CREDIT_ACCOUNT =
            "SELECT * FROM transaction WHERE credit_account_id = :accountId LIMIT :limit OFFSET :offset";

    private final String SQL_FIND_BY_ID =
            "SELECT * FROM transaction WHERE transaction_id = :transactionId";

            private final String SQL_SAVE =
            "INSERT INTO transaction (transaction_id, credit_account_id, debit_account_id, transaction_date, transaction_amount, credit_ref, debit_ref, transaction_status) " +
            "VALUES (:transactionId, :creditAccountId, :debitAccountId, :transactionDate, :transactionAmount, :creditRef, :debitRef, :transactionStatus)";

    private final String SQL_SAVE_ALL =
            "INSERT INTO transaction (transaction_id, credit_account_id, debit_account_id, transaction_date, transaction_amount, credit_ref, debit_ref, transaction_status) " +
            "VALUES (:transactionId, :creditAccountId, :debitAccountId, :transactionDate, :transactionAmount, :creditRef, :debitRef, :transactionStatus)";

    private final RowMapper<Transaction> transactionRowMapper = (rs, rowNum) -> {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.fromString(rs.getString("transaction_id")));
        transaction.setCreditAccountId(UUID.fromString(rs.getString("credit_account_id")));
        transaction.setDebitAccountId(UUID.fromString(rs.getString("debit_account_id")));
        transaction.setTransactionDate(rs.getString("transaction_date"));
        transaction.setTransactionAmount(rs.getLong("transaction_amount"));
        transaction.setCreditRef(rs.getString("credit_ref"));
        transaction.setDebitRef(rs.getString("debit_ref"));
        transaction.setTransactionStatus(TransactionStatusType.valueOf(rs.getString("transaction_status")));
        return transaction;
    };

    public List<Transaction> findByDebitAccount(UUID accountId, Pageable page) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("accountId", accountId.toString())
            .addValue("limit", page.getPageSize())
            .addValue("offset", page.getOffset());
        return namedParameterJdbcTemplate.query(SQL_FIND_BY_DEBIT_ACCOUNT, paramMap, transactionRowMapper);
    }

    public List<Transaction> findByCreditAccount(UUID accountId, Pageable page) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("accountId", accountId.toString())
            .addValue("limit", page.getPageSize())
            .addValue("offset", page.getOffset());
        return namedParameterJdbcTemplate.query(SQL_FIND_BY_CREDIT_ACCOUNT, paramMap, transactionRowMapper);
    }

    public Optional<Transaction> findById(UUID id) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("transactionId", id.toString());
        List<Transaction> results = namedParameterJdbcTemplate.query(SQL_FIND_BY_ID, paramMap, transactionRowMapper);
        return results.stream().findFirst();
    }

    public void save(Transaction transaction) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("transactionId", transaction.getTransactionId().toString());
        paramMap.put("creditAccountId", transaction.getCreditAccountId().toString());
        paramMap.put("debitAccountId", transaction.getDebitAccountId().toString());
        paramMap.put("transactionDate", transaction.getTransactionDate());
        paramMap.put("transactionAmount", transaction.getTransactionAmount());
        paramMap.put("creditRef", transaction.getCreditRef());
        paramMap.put("debitRef", transaction.getDebitRef());
        paramMap.put("transactionStatus", transaction.getTransactionStatus().toString());

        namedParameterJdbcTemplate.update(SQL_SAVE, paramMap);
    }

    public void saveAll(List<Transaction> transactions) {
        MapSqlParameterSource[] batchParams = transactions.stream().map(transaction -> {
            MapSqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("transactionId", transaction.getTransactionId().toString())
                .addValue("creditAccountId", transaction.getCreditAccountId().toString())
                .addValue("debitAccountId", transaction.getDebitAccountId().toString())
                .addValue("transactionDate", transaction.getTransactionDate())
                .addValue("transactionAmount", transaction.getTransactionAmount())
                .addValue("creditRef", transaction.getCreditRef())
                .addValue("debitRef", transaction.getDebitRef())
                .addValue("transactionStatus", transaction.getTransactionStatus().toString());
            return paramMap;
        }).toArray(MapSqlParameterSource[]::new);

        namedParameterJdbcTemplate.batchUpdate(SQL_SAVE_ALL, batchParams);
    }
}


