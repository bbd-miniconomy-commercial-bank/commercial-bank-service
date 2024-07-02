package com.miniconomy.commercial_bank_service.financial_management.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.miniconomy.commercial_bank_service.financial_management.entity.Loan;
import com.miniconomy.commercial_bank_service.financial_management.entity.LoanType;

@Repository
public class LoanRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final RowMapper<Loan> loanRowMapper = (rs, rowNum) -> {
        Loan loan = new Loan();
        loan.setLoanId(UUID.fromString(rs.getString("loan_id")));
        loan.setAccountId(UUID.fromString(rs.getString("account_id")));
        loan.setLoanAmount(rs.getLong("loan_amount"));
        loan.setLoanType(LoanType.valueOf(rs.getString("loan_type")));
        loan.setLoanCreatedDate(rs.getString("loan_created_date"));
        return loan;
    };

    public Optional<Loan> findById(UUID loanId) {
        String sql = "SELECT * FROM loan WHERE loan_id = :loanId";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("loanId", loanId.toString());
        List<Loan> results = namedParameterJdbcTemplate.query(sql, paramMap, loanRowMapper);
        return results.stream().findFirst();
    }

    public List<Loan> findAll() {
        String sql = "SELECT * FROM loan";
        return namedParameterJdbcTemplate.query(sql, loanRowMapper);
    }

    public Loan save(Loan loan) {
        String sql = "INSERT INTO loan (loan_id, account_id, loan_amount, loan_type, loan_created_date) " +
                     "VALUES (:loanId, :accountId, :loanAmount, :loanType, :loanCreatedDate)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("loanId", loan.getLoanId().toString());
        paramMap.put("accountId", loan.getAccountId().toString());
        paramMap.put("loanAmount", loan.getLoanAmount());
        paramMap.put("loanType", loan.getLoanType().toString());
        paramMap.put("loanCreatedDate", loan.getLoanCreatedDate());
        namedParameterJdbcTemplate.update(sql, paramMap);
        return loan;
    }

    public void update(Loan loan) {
        String sql = "UPDATE loan SET account_id = :accountId, loan_amount = :loanAmount, loan_type = :loanType, loan_created_date = :loanCreatedDate " +
                     "WHERE loan_id = :loanId";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("accountId", loan.getAccountId().toString());
        paramMap.put("loanAmount", loan.getLoanAmount());
        paramMap.put("loanType", loan.getLoanType().toString());
        paramMap.put("loanCreatedDate", loan.getLoanCreatedDate());
        paramMap.put("loanId", loan.getLoanId().toString());
        namedParameterJdbcTemplate.update(sql, paramMap);
    }

    public void deleteById(UUID loanId) {
        String sql = "DELETE FROM loan WHERE loan_id = :loanId";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("loanId", loanId.toString());
        namedParameterJdbcTemplate.update(sql, paramMap);
    }
}
