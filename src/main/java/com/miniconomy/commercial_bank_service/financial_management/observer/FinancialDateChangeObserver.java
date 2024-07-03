package com.miniconomy.commercial_bank_service.financial_management.observer;

import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.financial_management.service.LoanService;
import com.miniconomy.commercial_bank_service.simulation_management.event.EndOfMonthEvent;
import com.miniconomy.commercial_bank_service.simulation_management.observer.SimulationStoreObserver;

@Service
public class FinancialDateChangeObserver extends SimulationStoreObserver {
    
    private final LoanService loanService;

    public FinancialDateChangeObserver(LoanService loanService) {
        this.loanService = loanService;
    }

    @Override
    public void update(EndOfMonthEvent endOfMonthEvent) {
        loanService.processLoans();
    }

}
