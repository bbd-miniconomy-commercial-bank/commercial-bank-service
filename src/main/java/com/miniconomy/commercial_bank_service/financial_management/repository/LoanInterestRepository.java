package com.miniconomy.commercial_bank_service.financial_management.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.miniconomy.commercial_bank_service.financial_management.entity.LoanInterest;

@Repository
public class LoanInterestRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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
        String sql = "SELECT * FROM loan_interest WHERE loan_interest_id = :loanInterestId";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("loanInterestId", loanInterestId);
        List<LoanInterest> results = namedParameterJdbcTemplate.query(sql, paramMap, loanInterestRowMapper);
        return results.stream().findFirst();
    }

    public List<LoanInterest> findAll() {
        String sql = "SELECT * FROM loan_interest";
        return namedParameterJdbcTemplate.query(sql, loanInterestRowMapper);
    }

    public void save(LoanInterest loanInterest) {
        String sql = "INSERT INTO loan_interest (loan_interest_id, loan_type, interest_rate) VALUES (:loanInterestId, :loanType, :interestRate)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("loanInterestId", loanInterest.getLoanInterestId());
        paramMap.put("loanType", loanInterest.getLoanType());
        paramMap.put("interestRate", loanInterest.getInterestRate());
        namedParameterJdbcTemplate.update(sql, paramMap);
    }

    public void update(LoanInterest loanInterest) {
        String sql = "UPDATE loan_interest SET loan_type = :loanType, interest_rate = :interestRate WHERE loan_interest_id = :loanInterestId";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("loanType", loanInterest.getLoanType());
        paramMap.put("interestRate", loanInterest.getInterestRate());
        paramMap.put("loanInterestId", loanInterest.getLoanInterestId());
        namedParameterJdbcTemplate.update(sql, paramMap);
    }

    public void deleteById(UUID loanInterestId) {
        String sql = "DELETE FROM loan_interest WHERE loan_interest_id = :loanInterestId";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("loanInterestId", loanInterestId);
        namedParameterJdbcTemplate.update(sql, paramMap);
    }
}
