package com.miniconomy.commercial_bank_service.simulation_management.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.miniconomy.commercial_bank_service.financial_management.observer.FinancialDateChangeObserver;
import com.miniconomy.commercial_bank_service.simulation_management.event.EndOfMonthEvent;

import jakarta.validation.constraints.NotNull;

@Component
public class SimulationStore {

    private static String currentDate = "01|01|01";
    private static boolean simOnline = true;

    @Autowired
    private static FinancialDateChangeObserver financialDateChangeObserver;

    public static void setSimOnline(boolean newSimOnline) {
        simOnline = newSimOnline;
    }

    public static boolean getSimOnline() {
        return simOnline;
    }

    public static void setCurrentDate(@NotNull String newDate) {
        if (!currentDate.equals(newDate)) {
            currentDate = newDate;

            if (newDate.endsWith("30")) {
                financialDateChangeObserver.update(new EndOfMonthEvent());
            }
        }
    }

    public static String getCurrentDate() {
        return currentDate;
    }
}
