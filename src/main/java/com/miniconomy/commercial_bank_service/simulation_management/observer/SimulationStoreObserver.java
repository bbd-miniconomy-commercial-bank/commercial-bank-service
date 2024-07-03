package com.miniconomy.commercial_bank_service.simulation_management.observer;

import com.miniconomy.commercial_bank_service.simulation_management.event.EndOfMonthEvent;
import com.miniconomy.commercial_bank_service.simulation_management.event.EndOfYearEvent;

public abstract class SimulationStoreObserver {
    
    public abstract void update(EndOfMonthEvent dateChangedEvent);
    public abstract void update(EndOfYearEvent yearChangedEvent);
}
