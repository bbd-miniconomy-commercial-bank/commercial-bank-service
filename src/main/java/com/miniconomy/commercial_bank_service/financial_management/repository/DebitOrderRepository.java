package com.miniconomy.commercial_bank_service.financial_management.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.miniconomy.commercial_bank_service.financial_management.entity.DebitOrder;

@Repository
public class DebitOrderRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final RowMapper<DebitOrder> debitOrderRowMapper = (rs, rowNum) -> {
        DebitOrder debitOrder = new DebitOrder();
        debitOrder.setDebitOrderId(UUID.fromString(rs.getString("debit_order_id")));
        debitOrder.setDebitAccountName(rs.getString("debit_account_name"));
        debitOrder.setCreditAccountName(rs.getString("credit_account_name"));
        debitOrder.setDebitOrderCreatedDate(rs.getString("debit_order_created_date"));
        debitOrder.setDebitOrderAmount(rs.getLong("debit_order_amount"));
        debitOrder.setDebitOrderDebitRef(rs.getString("debit_order_debit_ref"));
        debitOrder.setDebitOrderCreditRef(rs.getString("debit_order_credit_ref"));
        debitOrder.setDebitOrderDisabled(rs.getBoolean("debit_order_disabled"));
        return debitOrder;
    };

    public Optional<DebitOrder> findById(UUID debitOrderId, String creditAccountName) {
        String sql = "SELECT * FROM account_debit_order_view WHERE debit_order_id = :debitOrderId AND credit_account_name = :creditAccountName";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("debitOrderId", debitOrderId);
        paramMap.put("creditAccountName", creditAccountName);
        return namedParameterJdbcTemplate.query(sql, paramMap, debitOrderRowMapper)
                .stream()
                .findFirst();
    }

    public Optional<DebitOrder> insert(DebitOrder debitOrder) {
        String sql = "SELECT * " +
                     "FROM insert_and_return_debit_order(:debitOrderId, :creditAccountName, :debitOrderDebitRef, :debitOrderCreditRef, :debitOrderAmount, :debitOrderCreatedDate, :debitOrderDisabled)";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("debitOrderId", debitOrder.getDebitOrderId())
            .addValue("creditAccountName", debitOrder.getCreditAccountName())
            .addValue("debitOrderDebitRef", debitOrder.getDebitOrderDebitRef())
            .addValue("debitOrderCreditRef", debitOrder.getDebitOrderCreditRef())
            .addValue("debitOrderAmount", debitOrder.getDebitOrderAmount())
            .addValue("debitOrderCreatedDate", debitOrder.getDebitOrderCreatedDate())
            .addValue("debitOrderDisabled", debitOrder.isDebitOrderDisabled());
        return namedParameterJdbcTemplate.query(sql, paramMap, debitOrderRowMapper)
                .stream()
                .findFirst();
    }

    public Optional<DebitOrder> update(DebitOrder debitOrder) {
        String sql = "SELECT * " +
                     "FROM update_and_return_debit_order(:debitAccountName, :creditAccountName, :debitOrderDebitRef, :debitOrderCreditRef, :debitOrderAmount, :debitOrderCreatedDate, :debitOrderDisabled)";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("debitAccountName", debitOrder.getDebitAccountName())
            .addValue("creditAccountName", debitOrder.getCreditAccountName())
            .addValue("debitOrderDebitRef", debitOrder.getDebitOrderDebitRef())
            .addValue("debitOrderCreditRef", debitOrder.getDebitOrderCreditRef())
            .addValue("debitOrderAmount", debitOrder.getDebitOrderAmount())
            .addValue("debitOrderCreatedDate", debitOrder.getDebitOrderCreatedDate())
            .addValue("debitOrderDisabled", debitOrder.isDebitOrderDisabled());
        return namedParameterJdbcTemplate.query(sql, paramMap, debitOrderRowMapper)
                .stream()
                .findFirst();
    }

    public List<DebitOrder> findAllByCreditAccount(String creditAccountName, Pageable pageable) {
        String sql = "SELECT * FROM account_debit_order_view WHERE credit_account_name = :creditAccountName LIMIT :limit OFFSET :offset";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("creditAccountName", creditAccountName)
            .addValue("limit", pageable.getPageSize())
            .addValue("offset", pageable.getOffset());
        return namedParameterJdbcTemplate.query(sql, paramMap, debitOrderRowMapper);
    }

    public List<DebitOrder> findAll(Pageable pageable) {
        String sql = "SELECT * FROM account_debit_order_view LIMIT :limit OFFSET :offset";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("limit", pageable.getPageSize())
            .addValue("offset", pageable.getOffset());
        return namedParameterJdbcTemplate.query(sql, paramMap, debitOrderRowMapper);
    }
}
