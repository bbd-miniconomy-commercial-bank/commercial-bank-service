package com.miniconomy.commercial_bank_service.simulation_management.store;

import org.springframework.stereotype.Component;

import com.miniconomy.commercial_bank_service.simulation_management.event.EndOfMonthEvent;
import com.miniconomy.commercial_bank_service.simulation_management.observer.SimulationStoreObserver;

import jakarta.validation.constraints.NotNull;

import java.util.List;

@Component
public class SimulationStore {

    private static String currentDate = "01|01|01"; // Initial date in the format dd|MM|yy
    private static boolean simOnline = true;

    private static List<SimulationStoreObserver> SimulationStoreObservers = List.of(); 

    public static void attachObserver(@NotNull SimulationStoreObserver SimulationStoreObserver) {
        SimulationStoreObservers.add(SimulationStoreObserver);
    }

    public static void setSimOnline(boolean newSimOnline) {
        simOnline = newSimOnline;
    }

    public static boolean getSimOnline() {
        return simOnline;
    }

    public static void setCurrentDate(@NotNull String newDate) {
        if (!currentDate.equals(newDate)) {
            currentDate = newDate;

            if (newDate.startsWith("30")) {
                for (SimulationStoreObserver SimulationStoreObserver : SimulationStoreObservers) {
                    SimulationStoreObserver.update(new EndOfMonthEvent());
                }
            }
        }
    }

    public static String getCurrentDate() {
        return currentDate;
    }
}
