package com.miniconomy.commercial_bank_service.simulation_management.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.miniconomy.commercial_bank_service.simulation_management.repository.SimulationRepository;
import com.miniconomy.commercial_bank_service.simulation_management.store.SimulationStore;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Service
public class SimulationService {

    @Value("${zues.endpoint}")
    String zuesUrl;

    private final SimulationRepository simulationRepository;

    public SimulationService(SimulationRepository simulationRepository) {
        this.simulationRepository = simulationRepository;
    }

    public void bootupSimulation() {
        fetchAndSetCurrentDate();
    }

    public void initializeSimulation() {
        SimulationStore.setCurrentDate("01|01|01");
        SimulationStore.setSimOnline(true);
        System.out.println("SIMULATION INITIALIZED");
    }

    public void resetSimulation() {
        System.out.println("SIMULATION RESETING");
        SimulationStore.setSimOnline(false);
        simulationRepository.deleteAllTables();
    }

    @Scheduled(cron = "0 */2 * * * *")
    public void updateSimulationDate() {
        System.out.println("DATE UPDATED");
        String updatedDate = incrementDate();
        SimulationStore.setCurrentDate(updatedDate);
    }

    @Scheduled(cron = "0 */15 * * * *")
    public void checkAndUpdateDate() {
        fetchAndSetCurrentDate();
    }

    private void fetchAndSetCurrentDate() {
        System.out.println("SYNCING DATE");
        try {
            RestTemplate restTemplate = new RestTemplate();
            String currentDate = restTemplate.getForObject(zuesUrl + "/date?time=" + System.currentTimeMillis(), String.class);
            if (currentDate != null && !currentDate.equals(SimulationStore.getCurrentDate())) {
                System.out.println("DATE FROM ZUES: " + currentDate);

                Pattern pattern = Pattern.compile("\\d+\\|\\d+\\|\\d+");

                if (pattern.matcher(currentDate).matches()) {
                    SimulationStore.setCurrentDate(currentDate);
                }
            }
        } catch (Exception e) {
            SimulationStore.setCurrentDate("01|01|01");
        }
    }

    private String incrementDate() {
        String[] parts = SimulationStore.getCurrentDate().split("\\|");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);
 
        day++;
        if (day > 30) {
            day = 1;
            month++;
            if (month > 12) {
                month = 1;
                year++;
            }
        }
 
        return String.format("%02d|%02d|%02d", year, month, day);
    }
}
