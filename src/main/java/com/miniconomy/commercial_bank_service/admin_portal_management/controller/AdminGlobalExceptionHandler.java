package com.miniconomy.commercial_bank_service.admin_portal_management.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.miniconomy.commercial_bank_service.financial_management.response.ResponseTemplate;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@ControllerAdvice
public class AdminGlobalExceptionHandler
{
  @ExceptionHandler(DataAccessException.class)
  @ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Bad Request")
    })
  public ResponseEntity<ResponseTemplate<Object>> handleDatabaseExceptions(DataAccessException ex)
  {
    ResponseTemplate<Object> response = new ResponseTemplate<>();
    response.setMessage("Required fields not set in request body");

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(Exception.class)
  @ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
  public ResponseEntity<ResponseTemplate<Object>> handleGeneralExceptions(Exception ex)
  {
    ResponseTemplate<Object> response = new ResponseTemplate<>();

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }
}

