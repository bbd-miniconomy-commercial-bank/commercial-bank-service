package com.miniconomy.commercial_bank_service.common.simulation_management;

import org.springframework.context.ApplicationEvent;

public class DateChangedEvent extends ApplicationEvent {
    private final String newDate;

    public DateChangedEvent(Object source, String newDate) {
        super(source);
        this.newDate = newDate;
    }

    public String getNewDate() {
        return newDate;
    }
}
