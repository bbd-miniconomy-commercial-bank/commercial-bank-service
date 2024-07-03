package com.miniconomy.commercial_bank_service.simulation_management;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DateChangeListener {

    @EventListener
    public void handleDateChangedEvent(DateChangedEvent event) {
        // Perform actions based on the updated date
        System.out.println("Date has been changed. New date: " + event.getNewDate());
    }
}
