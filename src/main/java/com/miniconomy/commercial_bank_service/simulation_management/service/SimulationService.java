package com.miniconomy.commercial_bank_service.simulation_management.service;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.miniconomy.commercial_bank_service.simulation_management.repository.SimulationManagementRepository;
import com.miniconomy.commercial_bank_service.simulation_management.store.DateStore;

@Service
public class SimulationService {

    private final RestTemplate restTemplate;

    private final DateStore SimulationManagementStore;

    private final SimulationManagementRepository simulationRepository;

    public void resetTables() {
        simulationRepository.deleteAllTables();
    }

    @Autowired
    public SimulationService(DateStore SimulationManagementStore, SimulationManagementRepository simulationRepository) {
        
        this.restTemplate = new RestTemplate();
        this.SimulationManagementStore = SimulationManagementStore;
        this.simulationRepository = simulationRepository;
        initialize();
    }

    @PostConstruct
    public void initialize() {
        fetchAndSetCurrentDate();
    }

    @Scheduled(cron = "0 */2 * * * *")
    public void updateSimulationDate() {
        String currentDate = SimulationManagementStore.getCurrentDate();
        String updatedDate = incrementDate(currentDate);
        SimulationManagementStore.setCurrentDate(updatedDate);
    }

    @Scheduled(cron = "0 */15 * * * *")
    public void checkAndUpdateDate() {
        fetchAndSetCurrentDate();
    }

    private void fetchAndSetCurrentDate() {
        try {
            String currentDate = restTemplate.getForObject("https://api.zeus.projects.bbdgrad.com/date", String.class); //NEED TO BE FIXED
            if (currentDate != null && !currentDate.equals(SimulationManagementStore.getCurrentDate())) {
                SimulationManagementStore.setCurrentDate(currentDate);
            }
        } catch (Exception e) {
            SimulationManagementStore.setCurrentDate("10|10|10");
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
            }
        }

        return String.format("%02d|%02d|%02d", day, month, year);
    }

    public String getSimulationDate() {
        return SimulationManagementStore.getCurrentDate();
    }
}
