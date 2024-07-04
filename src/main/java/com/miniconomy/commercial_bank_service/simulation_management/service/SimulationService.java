package com.miniconomy.commercial_bank_service.simulation_management.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.miniconomy.commercial_bank_service.simulation_management.repository.SimulationRepository;
import com.miniconomy.commercial_bank_service.simulation_management.store.SimulationStore;

@Service
public class SimulationService {

    @Value("${zues.endpoint}")
    String zuesUrl;

    private final SimulationRepository simulationRepository;

    public SimulationService(SimulationRepository simulationRepository) {
        this.simulationRepository = simulationRepository;
        bootupSimulation();
    }

    private void bootupSimulation() {
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
        String updatedDate = incrementDate();
        SimulationStore.setCurrentDate(updatedDate);
    }

    @Scheduled(cron = "0 */15 * * * *")
    public void checkAndUpdateDate() {
        fetchAndSetCurrentDate();
    }

    private void fetchAndSetCurrentDate() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String currentDate = restTemplate.getForObject(zuesUrl + "/date?time=" + System.currentTimeMillis(), String.class);
            if (currentDate != null && !currentDate.equals(SimulationStore.getCurrentDate())) {
                SimulationStore.setCurrentDate(currentDate);
            }
        } catch (Exception e) {
            SimulationStore.setCurrentDate("01|01|01");
        }
    }

    private String incrementDate() {
        String[] parts = SimulationStore.getCurrentDate().split("\\|");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);

        return String.format("%02d|%02d|%02d", day, month, year);
    }
}
