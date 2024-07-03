package com.miniconomy.commercial_bank_service.common.simulation_management;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SimulationService {

    private final RestTemplate restTemplate;

    @Autowired
    private final SimulationManagementStore SimulationManagementConfig;

    @Autowired
    public SimulationService(SimulationManagementStore SimulationManagementConfig) {
        this.restTemplate = new RestTemplate();
        this.SimulationManagementConfig = SimulationManagementConfig;
    }

    @PostConstruct
    public void initialize() {
        fetchAndSetCurrentDate();
    }

    @Scheduled(cron = "0 */2 * * * *")
    public void updateSimulationDate() {
        String currentDate = SimulationManagementConfig.getCurrentDate();
        String updatedDate = incrementDate(currentDate);
        SimulationManagementConfig.setCurrentDate(updatedDate);
    }

    @Scheduled(cron = "0 */15 * * * *")
    public void checkAndUpdateDate() {
        fetchAndSetCurrentDate();
    }

    private void fetchAndSetCurrentDate() {
        try {
            String currentDate = restTemplate.getForObject("https://api.zeus.projects.bbdgrad.com/date", String.class);
            if (currentDate != null && !currentDate.equals(SimulationManagementConfig.getCurrentDate())) {
                SimulationManagementConfig.setCurrentDate(currentDate);
            }
        } catch (Exception e) {
            SimulationManagementConfig.setCurrentDate("10|10|10");
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
        return SimulationManagementConfig.getCurrentDate();
    }
}
