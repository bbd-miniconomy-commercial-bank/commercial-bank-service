package com.miniconomy.commercial_bank_service.financial_management.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.miniconomy.commercial_bank_service.financial_management.entity.DebitOrder;
import com.miniconomy.commercial_bank_service.financial_management.entity.DebitOrderTransaction;

@Repository
public class DebitOrderRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final RowMapper<DebitOrder> debitOrderRowMapper = (rs, rowNum) -> {
        DebitOrder debitOrder = new DebitOrder();
        debitOrder.setDebitOrderId(UUID.fromString(rs.getString("debit_order_id")));
        debitOrder.setCreditAccountId(UUID.fromString(rs.getString("credit_account_id")));
        debitOrder.setDebitAccountId(UUID.fromString(rs.getString("debit_account_id")));
        debitOrder.setDebitOrderCreatedDate(rs.getString("debit_order_created_date"));
        debitOrder.setDebitOrderAmount(rs.getLong("debit_order_amount"));
        debitOrder.setDebitOrderReceiverRef(rs.getString("debit_order_receiver_ref"));
        debitOrder.setDebitOrderSenderRef(rs.getString("debit_order_sender_ref"));
        debitOrder.setDebitOrderDisabled(rs.getBoolean("debit_order_disabled"));
        return debitOrder;
    };

    private final RowMapper<DebitOrderTransaction> debitOrderTransactionRowMapper = (rs, rowNum) -> {
        DebitOrderTransaction transaction = new DebitOrderTransaction();
        transaction.setDebitOrderTransactionId(UUID.fromString(rs.getString("debit_order_transaction_id")));
        transaction.setDebitOrderId(UUID.fromString(rs.getString("debit_order_id")));
        transaction.setTransactionId(UUID.fromString(rs.getString("transaction_id")));
        transaction.setCreditAccountId(UUID.fromString(rs.getString("credit_account_id")));
        transaction.setCreditAccountName(rs.getString("credit_account_name"));
        transaction.setDebitAccountId(UUID.fromString(rs.getString("debit_account_id")));
        transaction.setDebitAccountName(rs.getString("debit_account_name"));
        transaction.setTransactionDate(rs.getString("transaction_date"));
        transaction.setTransactionAmount(rs.getLong("transaction_amount"));
        transaction.setCreditRef(rs.getString("credit_ref"));
        transaction.setDebitRef(rs.getString("debit_ref"));
        transaction.setTransactionStatus(rs.getString("transaction_status"));
        return transaction;
    };

    public Optional<DebitOrder> findById(UUID debitOrderId) {
        String sql = "SELECT * FROM debit_order WHERE debit_order_id = :debitOrderId";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("debitOrderId", debitOrderId);
        return namedParameterJdbcTemplate.query(sql, paramMap, debitOrderRowMapper)
                .stream()
                .findFirst();
    }

    public DebitOrder save(DebitOrder debitOrder) {
        String sql = "INSERT INTO debit_order (debit_order_id, credit_account_id, debit_account_id, debit_order_created_date, " +
                     "debit_order_amount, debit_order_receiver_ref, debit_order_sender_ref, debit_order_disabled) " +
                     "VALUES (:debitOrderId, :creditAccountId, :debitAccountId, :debitOrderCreatedDate, :debitOrderAmount, :debitOrderReceiverRef, :debitOrderSenderRef, :debitOrderDisabled)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("debitOrderId", debitOrder.getDebitOrderId());
        paramMap.put("creditAccountId", debitOrder.getCreditAccountId());
        paramMap.put("debitAccountId", debitOrder.getDebitAccountId());
        paramMap.put("debitOrderCreatedDate", debitOrder.getDebitOrderCreatedDate());
        paramMap.put("debitOrderAmount", debitOrder.getDebitOrderAmount());
        paramMap.put("debitOrderReceiverRef", debitOrder.getDebitOrderReceiverRef());
        paramMap.put("debitOrderSenderRef", debitOrder.getDebitOrderSenderRef());
        paramMap.put("debitOrderDisabled", debitOrder.isDebitOrderDisabled());
        namedParameterJdbcTemplate.update(sql, paramMap);
        return debitOrder;
    }

    public void update(DebitOrder debitOrder) {
        String sql = "UPDATE debit_order SET credit_account_id = :creditAccountId, debit_account_id = :debitAccountId, debit_order_created_date = :debitOrderCreatedDate, " +
                     "debit_order_amount = :debitOrderAmount, debit_order_receiver_ref = :debitOrderReceiverRef, debit_order_sender_ref = :debitOrderSenderRef, debit_order_disabled = :debitOrderDisabled " +
                     "WHERE debit_order_id = :debitOrderId";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("creditAccountId", debitOrder.getCreditAccountId());
        paramMap.put("debitAccountId", debitOrder.getDebitAccountId());
        paramMap.put("debitOrderCreatedDate", debitOrder.getDebitOrderCreatedDate());
        paramMap.put("debitOrderAmount", debitOrder.getDebitOrderAmount());
        paramMap.put("debitOrderReceiverRef", debitOrder.getDebitOrderReceiverRef());
        paramMap.put("debitOrderSenderRef", debitOrder.getDebitOrderSenderRef());
        paramMap.put("debitOrderDisabled", debitOrder.isDebitOrderDisabled());
        paramMap.put("debitOrderId", debitOrder.getDebitOrderId());
        namedParameterJdbcTemplate.update(sql, paramMap);
    }

    public void deleteById(UUID debitOrderId) {
        String sql = "DELETE FROM debit_order WHERE debit_order_id = :debitOrderId";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("debitOrderId", debitOrderId);
        namedParameterJdbcTemplate.update(sql, paramMap);
    }

    public List<DebitOrder> findByCreditAccount(UUID creditAccountId, Pageable pageable) {
        String sql = "SELECT * FROM debit_order WHERE credit_account_id = :creditAccountId LIMIT :limit OFFSET :offset";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("creditAccountId", creditAccountId);
        paramMap.put("limit", pageable.getPageSize());
        paramMap.put("offset", pageable.getOffset());
        return namedParameterJdbcTemplate.query(sql, paramMap, debitOrderRowMapper);
    }

    public List<DebitOrderTransaction> getDebitOrderTransactions() {
        String sql = "SELECT * FROM debit_order_transactions";
        return namedParameterJdbcTemplate.query(sql, debitOrderTransactionRowMapper);
    }
}
