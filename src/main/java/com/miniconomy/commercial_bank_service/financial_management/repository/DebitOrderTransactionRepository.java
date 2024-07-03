package com.miniconomy.commercial_bank_service.financial_management.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.miniconomy.commercial_bank_service.financial_management.entity.DebitOrderTransaction;

import java.util.UUID;
import java.util.Optional;

@Repository
public class DebitOrderTransactionRepository {
    
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final RowMapper<DebitOrderTransaction> debitOrderTransactionRowMapper = (rs, rowNum) -> {
        DebitOrderTransaction debitOrderTransaction = new DebitOrderTransaction();
        debitOrderTransaction.setDebitOrderTransactionId(UUID.fromString(rs.getString("debit_order_transaction_id")));
        debitOrderTransaction.setDebitOrderId(UUID.fromString(rs.getString("debit_order_id")));
        debitOrderTransaction.setTransactionId(UUID.fromString(rs.getString("transaction_id")));
        return debitOrderTransaction;
    };

    public Optional<DebitOrderTransaction> insert(DebitOrderTransaction debitOrderTransaction) {
        String sql = "SELECT * " +
                     "FROM insert_and_return_debit_order_transaction(:debitOrderId, :transactionId)";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("debitOrderId", debitOrderTransaction.getDebitOrderId())
            .addValue("transactionId", debitOrderTransaction.getTransactionId());
        
        try {
            return namedParameterJdbcTemplate.query(sql, paramMap, debitOrderTransactionRowMapper)
                .stream()
                .findFirst();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

}
