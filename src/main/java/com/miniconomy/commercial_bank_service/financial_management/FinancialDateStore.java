package com.miniconomy.commercial_bank_service.financial_management;

import com.miniconomy.commercial_bank_service.simulation_management.event.DateChangedEvent;
import com.miniconomy.commercial_bank_service.simulation_management.observer.DateStoreObserver;

public class FinancialDateStore extends DateStoreObserver {
    
    static String currentDate;

    @Override
    public void update(DateChangedEvent dateChangedEvent)
    {
        FinancialDateStore.currentDate = dateChangedEvent.getNewDate();
    }

    public static String getDate()
    {
        return currentDate;
    }

}
