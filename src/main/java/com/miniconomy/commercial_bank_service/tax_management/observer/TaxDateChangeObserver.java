package com.miniconomy.commercial_bank_service.tax_management.observer;

import java.net.URISyntaxException;

import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.simulation_management.event.EndOfMonthEvent;
import com.miniconomy.commercial_bank_service.simulation_management.event.EndOfYearEvent;
import com.miniconomy.commercial_bank_service.simulation_management.observer.SimulationStoreObserver;
import com.miniconomy.commercial_bank_service.tax_management.service.TaxService;

@Service
public class TaxDateChangeObserver extends SimulationStoreObserver {
    
    private final TaxService taxService;

    public TaxDateChangeObserver(TaxService taxService) {
        this.taxService = taxService;
    }

    @Override
    public void update(EndOfMonthEvent endOfMonthEvent) {
    }

    @Override
    public void update(EndOfYearEvent yearChangedEvent) {
        try
        {
            taxService.processTax();
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }
    }

}
