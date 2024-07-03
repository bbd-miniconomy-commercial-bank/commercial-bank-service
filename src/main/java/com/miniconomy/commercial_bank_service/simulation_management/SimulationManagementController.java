package com.miniconomy.commercial_bank_service.simulation_management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miniconomy.commercial_bank_service.financial_management.response.ResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Simulation", description = "Queries related to simulation management")
@RestController
public class SimulationManagementController {


    private final SimulationService simulationService;

    @Autowired
    public SimulationManagementController(SimulationService simulationService) {
        this.simulationService = simulationService;
    }

    @Operation(
        summary = "Get Hand of Zeus Time",
        description = "Get the current time"
    )
    @GetMapping(value = "/getdate", produces = "application/json")
    public ResponseEntity<ResponseTemplate<String>> getCurrentDate() {
        ResponseTemplate<String> response = new ResponseTemplate<>();
        int status = HttpStatus.OK.value();

        try {
            String currentDate = simulationService.getSimulationDate();
            response.setData(currentDate);
            response.setMessage("Current date retrieved successfully");
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            response.setMessage("Internal Server Error: " + e.getMessage());
        }

        response.setStatus(status);
        return ResponseEntity.status(status).body(response);
    }

    //resetiing / starting asap
    
}
