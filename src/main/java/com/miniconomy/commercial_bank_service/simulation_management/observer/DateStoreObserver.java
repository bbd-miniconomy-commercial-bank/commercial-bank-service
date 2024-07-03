package com.miniconomy.commercial_bank_service.simulation_management.observer;

import com.miniconomy.commercial_bank_service.simulation_management.DateChangedEvent;

public abstract class DateStoreObserver {
    
    public abstract void update(DateChangedEvent dateChangedEvent);

}
