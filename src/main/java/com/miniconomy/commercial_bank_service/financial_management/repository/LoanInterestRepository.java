package com.miniconomy.commercial_bank_service.financial_management.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.miniconomy.commercial_bank_service.financial_management.entity.LoanInterest;

@Repository
public class LoanInterestRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<LoanInterest> loanInterestRowMapper = new RowMapper<LoanInterest>() {
        @Override
        public LoanInterest mapRow(ResultSet rs, int rowNum) throws SQLException {
            LoanInterest loanInterest = new LoanInterest();
            loanInterest.setLoanInterestId(UUID.fromString(rs.getString("loan_interest_id")));
            loanInterest.setLoanType(rs.getString("loan_type"));
            loanInterest.setInterestRate(rs.getDouble("interest_rate"));
            return loanInterest;
        }
    };

    public Optional<LoanInterest> findById(UUID loanInterestId) {
        String sql = "SELECT * FROM loan_interest WHERE loan_interest_id = ?";
        List<LoanInterest> results = jdbcTemplate.query(sql, loanInterestRowMapper, loanInterestId);
        return results.stream().findFirst();
    }

    public List<LoanInterest> findAll() {
        String sql = "SELECT * FROM loan_interest";
        return jdbcTemplate.query(sql, loanInterestRowMapper);
    }

    public void save(LoanInterest loanInterest) {
        String sql = "INSERT INTO loan_interest (loan_interest_id, loan_type, interest_rate) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, loanInterest.getLoanInterestId(), loanInterest.getLoanType(), loanInterest.getInterestRate());
    }

    public void update(LoanInterest loanInterest) {
        String sql = "UPDATE loan_interest SET loan_type = ?, interest_rate = ? WHERE loan_interest_id = ?";
        jdbcTemplate.update(sql, loanInterest.getLoanType(), loanInterest.getInterestRate(), loanInterest.getLoanInterestId());
    }

    public void deleteById(UUID loanInterestId) {
        String sql = "DELETE FROM loan_interest WHERE loan_interest_id = ?";
        jdbcTemplate.update(sql, loanInterestId);
    }
}
