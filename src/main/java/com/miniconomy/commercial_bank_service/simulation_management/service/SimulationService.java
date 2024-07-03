package com.miniconomy.commercial_bank_service.simulation_management.service;

import jakarta.annotation.PostConstruct;

import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.miniconomy.commercial_bank_service.simulation_management.repository.SimulationManagementRepository;
import com.miniconomy.commercial_bank_service.simulation_management.store.SimulationStore;

@Service
public class SimulationService {

    private final SimulationManagementRepository simulationRepository;

    public SimulationService(SimulationManagementRepository simulationRepository) {
        this.simulationRepository = simulationRepository;
        bootupSimulation();
    }

    private void bootupSimulation() {
        fetchAndSetCurrentDate();
    }

    public void initializeSimulation() {
        SimulationStore.setCurrentDate("01|01|01");
        SimulationStore.setSimOnline(true);
    }

    public void resetSimulation() {
        SimulationStore.setSimOnline(false);
        simulationRepository.deleteAllTables();
    }

    @Scheduled(cron = "0 */2 * * * *")
    public void updateSimulationDate() {
        String currentDate = SimulationStore.getCurrentDate();
        String updatedDate = incrementDate(currentDate);
        SimulationStore.setCurrentDate(updatedDate);
    }

    @Scheduled(cron = "0 */15 * * * *")
    public void checkAndUpdateDate() {
        fetchAndSetCurrentDate();
    }

    private void fetchAndSetCurrentDate() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String currentDate = restTemplate.getForObject("https://api.zeus.projects.bbdgrad.com/date", String.class); //NEED TO BE FIXED
            if (currentDate != null && !currentDate.equals(SimulationStore.getCurrentDate())) {
                SimulationStore.setCurrentDate(currentDate);
            }
        } catch (Exception e) {
            SimulationStore.setCurrentDate("01|01|01");
        }
    }

    private String incrementDate(String currentDate) {
        String[] parts = currentDate.split("\\|");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);

        day++;
        if (day > 30) {
            day = 1;
            month++;
            if (month > 12) {
                month = 1;
                year++;

                try {
                    TaxService taxService = new TaxService();
                    taxService.payTax(year - 1);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        }

        return String.format("%02d|%02d|%02d", day, month, year);
    }
}
