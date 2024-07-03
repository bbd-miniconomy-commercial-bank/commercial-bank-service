package com.miniconomy.commercial_bank_service.simulation_management.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class SimulationManagementRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private void deleteAllAccounts() {
        String sql = "DELETE FROM account";

        namedParameterJdbcTemplate.getJdbcOperations().update(sql);

    }

    private void deleteAllAccountsDelegation() {
        String sql = "DELETE FROM account_delegation";

        namedParameterJdbcTemplate.getJdbcOperations().update(sql);

    }

    private void deleteDebitOrderTransactions() {
        String sql = "DELETE FROM debit_order_transaction";

        namedParameterJdbcTemplate.getJdbcOperations().update(sql);

    }

    private void deleteAllDebitOrders() {
        String sql = "DELETE FROM debit_order";

        namedParameterJdbcTemplate.getJdbcOperations().update(sql);

    }

    private void deleteAllInterbankTransactions() {
        String sql = "DELETE FROM interbank_transaction";

        namedParameterJdbcTemplate.getJdbcOperations().update(sql);

    }

    private void deleteAllLoanInterests() {
        String sql = "DELETE FROM loan_interest";

        namedParameterJdbcTemplate.getJdbcOperations().update(sql);

    }

    private void deleteAllLoanTransactions() {
        String sql = "DELETE FROM loan_transaction";

        namedParameterJdbcTemplate.getJdbcOperations().update(sql);

    }

    private void deleteAllLoans() {
        String sql = "DELETE FROM loan";

        namedParameterJdbcTemplate.getJdbcOperations().update(sql);

    }
    private void deleteAllTransactions() {
        String sql = "DELETE FROM transaction";

        namedParameterJdbcTemplate.getJdbcOperations().update(sql);

    }
    
    public void deleteAllTables() {
        deleteAllLoanInterests();
        deleteDebitOrderTransactions();
        deleteAllInterbankTransactions();
        deleteAllLoanTransactions();
        deleteAllTransactions();
        deleteAllDebitOrders();
        deleteAllLoans();
        deleteAllAccountsDelegation();
        deleteAllAccounts();     
    }
      
}
