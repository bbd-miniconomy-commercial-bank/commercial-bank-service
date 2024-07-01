package com.miniconomy.commercial_bank_service.financial_management.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.miniconomy.commercial_bank_service.financial_management.entity.Loan;
import com.miniconomy.commercial_bank_service.financial_management.entity.LoanType;

@Repository
public class LoanRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
        String sql = "SELECT * FROM loan WHERE loan_id = ?";
        List<Loan> results = jdbcTemplate.query(sql, loanRowMapper, loanId.toString());
        return results.stream().findFirst();
    }

    public List<Loan> findAll() {
        String sql = "SELECT * FROM loan";
        return jdbcTemplate.query(sql, loanRowMapper);
    }

    public Loan save(Loan loan) {
        String sql = "INSERT INTO loan (loan_id, account_id, loan_amount, loan_type, loan_created_date) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, loan.getLoanId(), loan.getAccountId(), loan.getLoanAmount(),
                            loan.getLoanType().toString(), loan.getLoanCreatedDate());
        return loan;

    }

    public void update(Loan loan) {
        String sql = "UPDATE loan SET account_id = ?, loan_amount = ?, loan_type = ?, loan_created_date = ? WHERE loan_id = ?";
        jdbcTemplate.update(sql, loan.getAccountId(), loan.getLoanAmount(), loan.getLoanType().toString(),
                            loan.getLoanCreatedDate(), loan.getLoanId());
    }

    public void deleteById(UUID loanId) {
        String sql = "DELETE FROM loan WHERE loan_id = ?";
        jdbcTemplate.update(sql, loanId);
    }
}
