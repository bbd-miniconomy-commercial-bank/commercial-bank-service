package com.miniconomy.commercial_bank_service.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler
{
  @ExceptionHandler(DataAccessException.class)
  public ResponseEntity<?> handleDatabaseExceptions(DataAccessException ex)
  {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input data");
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleGeneralExceptions(Exception ex)
  {
    return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
  }
}

