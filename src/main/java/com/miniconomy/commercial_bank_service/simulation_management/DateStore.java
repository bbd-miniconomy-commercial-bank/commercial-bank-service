package com.miniconomy.commercial_bank_service.simulation_management;

import org.springframework.stereotype.Component;

import com.miniconomy.commercial_bank_service.simulation_management.observer.DateStoreObserver;

import java.util.List;

@Component
public class DateStore {
    

    private String currentDate = "01|01|01"; // Initial date in the format dd|MM|yy
    private List<DateStoreObserver> dateStoreObservers = List.of(); 

    public void attachObserver(DateStoreObserver dateStoreObserver) {
        if (dateStoreObserver != null) {
            dateStoreObservers.add(dateStoreObserver);
        }
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        if (!this.currentDate.equals(currentDate)) {
            this.currentDate = currentDate;
            for (DateStoreObserver dateStoreObserver : dateStoreObservers) {
                dateStoreObserver.update(currentDate);
            }
        }
    }
}
