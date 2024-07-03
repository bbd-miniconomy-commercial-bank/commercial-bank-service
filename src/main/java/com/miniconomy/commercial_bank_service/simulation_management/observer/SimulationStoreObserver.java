package com.miniconomy.commercial_bank_service.simulation_management.observer;

import com.miniconomy.commercial_bank_service.simulation_management.event.EndOfMonthEvent;

public abstract class SimulationStoreObserver {
    
    public abstract void update(EndOfMonthEvent dateChangedEvent);

}
