package com.acarreno.poc.video.streaming.exception.handle;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.acarreno.poc.video.streaming.exception.CustomError;
import com.acarreno.poc.video.streaming.exception.CustomException;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<CustomError> handleException(CustomException ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(CustomError.builder().message(ex.getMessage()).description(ex.getError()).build());
  }

  @ExceptionHandler(NullPointerException.class)
  public ResponseEntity<CustomError> handleException(NullPointerException ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CustomError.builder()
        .message(ex.getMessage()).description(ex.getLocalizedMessage()).build());
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<CustomError> handleException(DataIntegrityViolationException ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CustomError.builder()
        .message(ex.getMessage()).description("Data Integrity Violation").build());
  }

}
