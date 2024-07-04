package com.miniconomy.commercial_bank_service.simulation_management.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miniconomy.commercial_bank_service.simulation_management.enumeration.SetupActionEnum;
import com.miniconomy.commercial_bank_service.simulation_management.request.SetupRequest;
import com.miniconomy.commercial_bank_service.simulation_management.service.SimulationService;
import com.miniconomy.commercial_bank_service.simulation_management.store.SimulationStore;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Simulation", description = "Queries related to simulation management")
@RestController
@RequestMapping("/simulation")
public class SimulationController {

    private final SimulationService simulationService;

    public SimulationController(SimulationService simulationService) {
        this.simulationService = simulationService;
    }

    @Operation(
        summary = "Reset or start the Simulation",
        description = "Reset or start the Commercial Bank Service"
    )
    @PostMapping(value = "/setup", produces = "application/json")
    public ResponseEntity<Object> postSetup(@RequestBody SetupRequest setupRequest) {
        
        if (setupRequest.getAction().equals(SetupActionEnum.start) && SimulationStore.getSimOnline()) {
            simulationService.resetSimulation(); 
        }

        if (setupRequest.getAction().equals(SetupActionEnum.reset)) {
            simulationService.resetSimulation(); 
        } else if (setupRequest.getAction().equals(SetupActionEnum.start)) {
            simulationService.initializeSimulation();
        }

        return ResponseEntity.status(HttpStatus.OK.value()).body(null);
    }


}
