package com.miniconomy.commercial_bank_service.common.simulation_management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class SimulationManagementStore {
    @Autowired
    private String currentDate = "01|01|01"; // Initial date in the format dd|MM|yy

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Bean
    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        if (!this.currentDate.equals(currentDate)) {
            this.currentDate = currentDate;
            eventPublisher.publishEvent(new DateChangedEvent(currentDate));
        }
    }
}
