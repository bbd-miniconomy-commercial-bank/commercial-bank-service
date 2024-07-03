package com.miniconomy.commercial_bank_service.financial_management.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.miniconomy.commercial_bank_service.financial_management.entity.Loan;
import com.miniconomy.commercial_bank_service.financial_management.enumeration.LoanTypeEnum;

@Repository
public class LoanRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final RowMapper<Loan> loanRowMapper = (rs, rowNum) -> {
        Loan loan = new Loan();
        loan.setLoanId(UUID.fromString(rs.getString("loan_id")));
        loan.setAccountName(rs.getString("account_name"));
        loan.setLoanAmount(rs.getLong("loan_amount"));
        loan.setLoanType(LoanTypeEnum.valueOf(rs.getString("loan_type")));
        loan.setLoanCreatedDate(rs.getString("loan_created_date"));
        return loan;
    };

    public Optional<Loan> findById(UUID loanId, String accountName) {
        String sql = "SELECT * FROM account_loan_view WHERE loan_id = :loanId AND account_name = :accountName";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("loanId", loanId.toString())
            .addValue("accountName", accountName);
        return namedParameterJdbcTemplate.query(sql, paramMap, loanRowMapper)
            .stream()
            .findFirst();
    }

    public List<Loan> findAll(Pageable pageable) {
        String sql = "SELECT * FROM account_loan_view LIMIT :limit OFFSET :offset";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("limit", pageable.getPageSize())
            .addValue("offset", pageable.getOffset());
        return namedParameterJdbcTemplate.query(sql, paramMap, loanRowMapper);
    }

    public List<Loan> findAllByAccountName(String accountName, Pageable pageable) {
        String sql = "SELECT * FROM account_loan_view WHERE account_name = :accountName LIMIT :limit OFFSET :offset";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("accountName", accountName)
            .addValue("limit", pageable.getPageSize())
            .addValue("offset", pageable.getOffset());
        return namedParameterJdbcTemplate.query(sql, paramMap, loanRowMapper);
    }

    public Optional<Loan> insert(Loan loan) {
        String sql = "SELECT * " +
                     "FROM insert_and_return_loan(:accountName, :loanAmount, :loanType, :loanCreatedDate)";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("accountName", loan.getAccountName())
            .addValue("loanAmount", loan.getLoanAmount())
            .addValue("loanType", loan.getLoanType().toString())
            .addValue("loanCreatedDate", loan.getLoanCreatedDate());
        return namedParameterJdbcTemplate.query(sql, paramMap, loanRowMapper)
            .stream()
            .findFirst();
    }
}
