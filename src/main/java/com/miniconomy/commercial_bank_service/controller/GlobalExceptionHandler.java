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
  public ResponseEntity<String> handleDatabaseExceptions(DataAccessException ex)
  {
    return new ResponseEntity<>("Invalid input data", HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGeneralExceptions(Exception ex)
  {
    return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
  }
}

