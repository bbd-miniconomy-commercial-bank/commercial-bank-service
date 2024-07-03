package com.miniconomy.commercial_bank_service.simulation_management.request;

import com.miniconomy.commercial_bank_service.simulation_management.enumeration.SetupActionEnum;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SetupRequest {
    private SetupActionEnum action;
    private String startTime;
}
