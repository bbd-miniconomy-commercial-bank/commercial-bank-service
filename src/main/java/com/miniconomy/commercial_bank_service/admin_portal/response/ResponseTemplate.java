package com.miniconomy.commercial_bank_service.admin_portal.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class ResponseTemplate<T> {

    int status;
    T data;
    String message;

    public ResponseTemplate() {
        status = HttpStatus.OK.value();
        data = null;
        message = "";
    }

}
