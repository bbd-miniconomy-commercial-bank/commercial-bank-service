package com.miniconomy.commercial_bank_service.financial_management.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.miniconomy.commercial_bank_service.financial_management.entity.InterbankTransaction;
import com.miniconomy.commercial_bank_service.financial_management.enumeration.InterbankTransactionStatusEnum;

@Repository
public class InterbankTransactionRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final RowMapper<InterbankTransaction> interbankTransactionRowMapper = (rs, rowNum) -> {
        InterbankTransaction interbankTransaction = new InterbankTransaction();
        interbankTransaction.setInterbankTransactionId(UUID.fromString(rs.getString("interbank_transaction_id")));
        interbankTransaction.setTransactionId(UUID.fromString(rs.getString("transaction_id")));
        interbankTransaction.setExternalAccountId(rs.getString("external_account_id"));
        interbankTransaction.setInterbankTransactionStatus(InterbankTransactionStatusEnum.valueOf(rs.getString("interbank_transaction_status")));
        return interbankTransaction;
    };
    
    public Optional<InterbankTransaction> insert(InterbankTransaction interbankTransaction) {
        String sql = "SELECT * " +
                     "FROM insert_and_return_interbank_transaction(:transactionId, :externalAccountId, :interbankTransactionStatus)";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("transactionId", interbankTransaction.getTransactionId().toString())
            .addValue("externalAccountId", interbankTransaction.getExternalAccountId().toString())
            .addValue("interbankTransactionStatus", interbankTransaction.getInterbankTransactionStatus().toString());
        
        try {
            return namedParameterJdbcTemplate.query(sql, paramMap, interbankTransactionRowMapper)
                .stream()
                .findFirst();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<InterbankTransaction> update(InterbankTransaction interbankTransaction) {
        String sql = "SELECT * " +
                     "FROM update_and_return_interbank_transaction(:interbankTransactionId, :transactionId, :externalAccountId, :interbankTransactionStatus)";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("interbankTransactionId", interbankTransaction.getInterbankTransactionId().toString())
            .addValue("transactionId", interbankTransaction.getTransactionId().toString())
            .addValue("externalAccountId", interbankTransaction.getExternalAccountId().toString())
            .addValue("interbankTransactionStatus", interbankTransaction.getInterbankTransactionStatus().toString());
        
        try {
            return namedParameterJdbcTemplate.query(sql, paramMap, interbankTransactionRowMapper)
                .stream()
                .findFirst();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<InterbankTransaction> getByTransactionId(UUID transactionId) {
        String sql = "SELECT * " +
                     "FROM interbank_transaction " +
                     "WHERE transaction_id = :transactionId::UUID";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("transactionId", transactionId.toString());
        
        try {
            return namedParameterJdbcTemplate.query(sql, paramMap, interbankTransactionRowMapper)
                .stream()
                .findFirst();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

}
