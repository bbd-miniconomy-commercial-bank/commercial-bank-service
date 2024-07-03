package com.miniconomy.commercial_bank_service.financial_management.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.miniconomy.commercial_bank_service.financial_management.entity.LoanInterest;

@Repository
public class LoanInterestRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final RowMapper<LoanInterest> loanInterestRowMapper = (rs, rowNum) -> {
        LoanInterest loanInterest = new LoanInterest();
        loanInterest.setLoanInterestId(UUID.fromString(rs.getString("loan_interest_id")));
        loanInterest.setLoanType(rs.getString("loan_type"));
        loanInterest.setInterestRate(rs.getDouble("interest_rate"));
        return loanInterest;
    };

    public List<LoanInterest> findAll(Pageable pageable) {
        String sql = "SELECT * FROM loan_interest LIMIT :limit OFFSET :offset";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("limit", pageable.getPageSize())
            .addValue("offset", pageable.getOffset());
        
        try {
            return namedParameterJdbcTemplate.query(sql, paramMap, loanInterestRowMapper);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public void insert(LoanInterest loanInterest) {
        String sql = "INSERT INTO loan_interest " +
                     "(loan_type, interest_rate) " + 
                     "VALUES (:loanType, :interestRate)";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("loanType", loanInterest.getLoanType().toString())
            .addValue("interestRate", loanInterest.getInterestRate());
            
        try {
            namedParameterJdbcTemplate.update(sql, paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
