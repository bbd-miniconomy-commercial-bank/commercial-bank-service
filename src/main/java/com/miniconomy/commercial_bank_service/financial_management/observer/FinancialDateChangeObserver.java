package com.miniconomy.commercial_bank_service.financial_management.observer;

import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.financial_management.service.DebitOrderService;
import com.miniconomy.commercial_bank_service.financial_management.service.LoanService;
import com.miniconomy.commercial_bank_service.simulation_management.event.EndOfMonthEvent;
import com.miniconomy.commercial_bank_service.simulation_management.observer.SimulationStoreObserver;
import com.miniconomy.commercial_bank_service.simulation_management.store.SimulationStore;

@Service
public class FinancialDateChangeObserver extends SimulationStoreObserver {
    
    private final LoanService loanService;
    private final DebitOrderService debitOrderService;

    public FinancialDateChangeObserver(LoanService loanService, DebitOrderService debitOrderService) {
        this.loanService = loanService;
        this.debitOrderService = debitOrderService;
        SimulationStore.attachObserver(this);
    }

    @Override
    public void update(EndOfMonthEvent endOfMonthEvent) {
        loanService.processLoans();
        debitOrderService.processDebitOrders();
    }

}
