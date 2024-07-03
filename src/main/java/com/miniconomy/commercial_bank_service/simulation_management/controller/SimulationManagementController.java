package com.miniconomy.commercial_bank_service.simulation_management.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miniconomy.commercial_bank_service.financial_management.response.ResponseTemplate;
import com.miniconomy.commercial_bank_service.simulation_management.enumeration.SetupActionEnum;
import com.miniconomy.commercial_bank_service.simulation_management.request.SetupRequest;
import com.miniconomy.commercial_bank_service.simulation_management.service.SimulationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Simulation", description = "Queries related to simulation management")
@RestController
@RequestMapping("/simulation")
public class SimulationManagementController {


    private final SimulationService simulationService;

    public SimulationManagementController(SimulationService simulationService) {
        this.simulationService = simulationService;
    }

    @Operation(
        summary = "Reset or start the Simulation",
        description = "Reset or start the Commercial Bank Service"
    )
    @PostMapping(value = "/setup", produces = "application/json")
    public ResponseEntity<ResponseTemplate<String>> postSetup(@RequestBody SetupRequest setupRequest) {
        ResponseTemplate<String> response = new ResponseTemplate<>();
        int status = HttpStatus.OK.value();

        if (setupRequest.getAction().equals(SetupActionEnum.reset)) {
            simulationService.resetSimulation(); 
        } else {
            simulationService.initializeSimulation();
        }

        response.setMessage("Successfully reset commercial Bank");

        response.setStatus(status);
        return ResponseEntity.status(status).body(response);
    }


}
