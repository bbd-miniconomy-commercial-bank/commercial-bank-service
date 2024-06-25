package com.miniconomy.commercial_bank_service.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BasicResponse<T> {

    int status;
    T body;

    // public BasicResponse(T message) {
    //     this.status = HttpStatus.OK.value();
    //     this.body = message;
    // }

    public BasicResponse(T body) {
        this.status = HttpStatus.OK.value();
        this.body = body;
    }
}
