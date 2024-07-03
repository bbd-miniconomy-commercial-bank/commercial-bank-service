package com.miniconomy.commercial_bank_service.simulation_management.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DateChangedEvent {
    private final String newDate;
}
