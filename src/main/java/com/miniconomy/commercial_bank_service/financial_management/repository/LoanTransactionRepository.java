package com.miniconomy.commercial_bank_service.financial_management.repository;

import java.util.UUID;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.miniconomy.commercial_bank_service.financial_management.entity.LoanTransaction;

public class LoanTransactionRepository {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final RowMapper<LoanTransaction> LoanTransactionsRowMapper = (rs, rowNum) -> {
        LoanTransaction loanTransaction = new LoanTransaction();
        loanTransaction.setLoanId(UUID.fromString(rs.getString("loan_id")));
        loanTransaction.setLoanTransactionId(UUID.fromString(rs.getString("loan_transaction_id")));
        loanTransaction.setTransactionId(UUID.fromString(rs.getString("transaction_id")));
        return loanTransaction;
    };

    public Optional<LoanTransaction> insert(UUID transactionId, UUID loanId) {
        String sql = "SELECT * " +
                     "FROM insert_and_return_loan_transaction(:loanId, :transactionId)";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("loanId", loanId)
            .addValue("transactionId", transactionId);
          
        return namedParameterJdbcTemplate.query(sql, paramMap,LoanTransactionsRowMapper)
            .stream()
            .findFirst();
    }

}
