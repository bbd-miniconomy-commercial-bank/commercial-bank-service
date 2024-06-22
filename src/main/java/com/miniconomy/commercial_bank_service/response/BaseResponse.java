package com.miniconomy.commercial_bank_service.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseResponse {

    int status;
    String message;

    public BaseResponse(String message) {
        this.status = HttpStatus.OK.value();
        this.message = message;
    }
}
