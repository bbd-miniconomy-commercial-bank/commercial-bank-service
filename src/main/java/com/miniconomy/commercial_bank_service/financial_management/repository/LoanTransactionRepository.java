package com.miniconomy.commercial_bank_service.financial_management.repository;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.miniconomy.commercial_bank_service.financial_management.command.TransactionCommand;
import com.miniconomy.commercial_bank_service.financial_management.entity.LoanTransaction;

public class LoanTransactionRepository {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    LoanTransactionRepository(TransactionCommand transactionCommand, LoanTransactionService)
    {

    }

    private final RowMapper<LoanTransaction> LoanTransactionsRowMapper = (rs, rowNum) -> {
        LoanTransaction loanTransaction = new LoanTransaction();
        loanTransaction.setLoanId(UUID.fromString(rs.getString("loan_id")));
        loanTransaction.setLoanTransactionId(UUID.fromString(rs.getString("loan_transaction_id")));
        loanTransaction.setTransactionId(UUID.fromString(rs.getString("transaction_id")));
        return loanTransaction;
    };

    public void insert(UUID transactionId, UUID loanId) {
        String sql = "SELECT * " +
                     "FROM insert_and_return_loan(:transactionId, :loanId)";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("transactionId", transactionId)
            .addValue("loanId", loanId);
          
        namedParameterJdbcTemplate.query(sql, paramMap,LoanTransactionsRowMapper);
    }

}
