package com.miniconomy.commercial_bank_service.financial_management.repository;

import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;
import com.miniconomy.commercial_bank_service.financial_management.entity.TransactionStatusType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;

@Repository
public class TransactionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
        String sql = "SELECT * FROM transaction WHERE debit_account_id = ? LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, transactionRowMapper, accountId, page.getPageSize(), page.getOffset());
    }

    public List<Transaction> findByCreditAccount(UUID accountId, Pageable page) {
        String sql = "SELECT * FROM transaction WHERE credit_account_id = ? LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, transactionRowMapper, accountId, page.getPageSize(), page.getOffset());
    }

    public Optional<Transaction> findById(UUID id) {
        String sql = "SELECT * FROM transaction WHERE transaction_id = ?";
        List<Transaction> results = jdbcTemplate.query(sql, transactionRowMapper, id.toString());
        return results.stream().findFirst();
    }

    public void save(Transaction transaction) {
        String sql = "INSERT INTO transaction (transaction_id, credit_account_id, debit_account_id, transaction_date, transaction_amount, credit_ref, debit_ref, transaction_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1, transaction.getTransactionId());
            ps.setObject(2, transaction.getCreditAccountId());
            ps.setObject(3, transaction.getDebitAccountId());
            ps.setString(4, transaction.getTransactionDate());
            ps.setLong(5, transaction.getTransactionAmount());
            ps.setString(6, transaction.getCreditRef());
            ps.setString(7, transaction.getDebitRef());
            ps.setObject(8, transaction.getTransactionStatus(), Types.OTHER);
            return ps;
        });
    }

    public void saveAll(List<Transaction> transactions) {
      String sql = "INSERT INTO transaction (transaction_id, credit_account_id, debit_account_id, transaction_date, transaction_amount, credit_ref, debit_ref, transaction_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

      jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
              Transaction transaction = transactions.get(i);
              ps.setObject(1, transaction.getTransactionId());
              ps.setObject(2, transaction.getCreditAccountId());
              ps.setObject(3, transaction.getDebitAccountId());
              ps.setString(4, transaction.getTransactionDate());
              ps.setLong(5, transaction.getTransactionAmount());
              ps.setString(6, transaction.getCreditRef());
              ps.setString(7, transaction.getDebitRef());
              ps.setObject(8, transaction.getTransactionStatus());
          }

          @Override
          public int getBatchSize() {
              return transactions.size();
          }
      });}
}
