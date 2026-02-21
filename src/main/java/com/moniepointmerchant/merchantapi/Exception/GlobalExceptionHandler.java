package com.moniepointmerchant.merchantapi.Exception;

import java.time.LocalDateTime;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.moniepointmerchant.merchantapi.response.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
  //due to the nature of GET being the only HTTP METHOD
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse>
  globalInternalServerErrorHandler(
   Exception exception, WebRequest request
  ){
    ErrorResponse error = new ErrorResponse(
      false,
      request.getDescription(false),
      HttpStatus.INTERNAL_SERVER_ERROR,
      exception.getMessage(),
      LocalDateTime.now()
    );

    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
  public ResponseEntity<ErrorResponse>
  handleNotFoundException(
    ChangeSetPersister.NotFoundException exception, WebRequest request
  ){
    ErrorResponse error = new ErrorResponse(
      false,
      request.getDescription(false),
      HttpStatus.NOT_FOUND,
      exception.getMessage(),
      LocalDateTime.now()
    );

    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }
}
