package com.miniconomy.commercial_bank_service.common.config;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.miniconomy.commercial_bank_service.financial_management.response.ResponseTemplate;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@ControllerAdvice
public class GlobalExceptionHandler
{
  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "400", description = "Bad Request")
  })
  public ResponseEntity<ResponseTemplate<Object>> handleDatabaseExceptions(DataAccessException ex)
  {
    ResponseTemplate<Object> response = new ResponseTemplate<>();
    response.setStatus(HttpStatus.BAD_REQUEST.value());
    response.setMessage("Required fields not set in request body");

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(Exception.class)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "500", description = "Internal Server Error")
  })
  public ResponseEntity<ResponseTemplate<Object>> handleGeneralExceptions(Exception ex)
  {
    ex.printStackTrace();

    ResponseTemplate<Object> response = new ResponseTemplate<>();
    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "404", description = "Not found")
  })
  public ResponseEntity<ResponseTemplate<Object>> handleGeneralExceptions(MethodArgumentTypeMismatchException ex)
  {

    ResponseTemplate<Object> response = new ResponseTemplate<>();
    response.setStatus(HttpStatus.NOT_FOUND.value());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }
}

