package com.miniconomy.commercial_bank_service.simulation_management.store;

import org.springframework.stereotype.Component;

import com.miniconomy.commercial_bank_service.simulation_management.event.DateChangedEvent;
import com.miniconomy.commercial_bank_service.simulation_management.observer.DateStoreObserver;

import java.util.List;

@Component
public class SimulationStore {

    private static String currentDate = "01|01|01"; // Initial date in the format dd|MM|yy
    private static boolean simOnline = true;

    private static List<DateStoreObserver> dateStoreObservers = List.of(); 

    public static void attachObserver(DateStoreObserver dateStoreObserver) {
        if (dateStoreObserver != null) {
            dateStoreObservers.add(dateStoreObserver);
        }
    }

    public static void setSimOnline(boolean newSimOnline) {
        simOnline = newSimOnline;
    }

    public static boolean getSimOnline() {
        return simOnline;
    }

    public static void setCurrentDate(String newDate) {
        if (!currentDate.equals(newDate)) {
            currentDate = newDate;

            DateChangedEvent dateChangedEvent = new DateChangedEvent(
                currentDate
            );

            for (DateStoreObserver dateStoreObserver : dateStoreObservers) {
                dateStoreObserver.update(dateChangedEvent);
            }
        }
    }

    public static String getCurrentDate() {
        return currentDate;
    }
}
