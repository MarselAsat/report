package com.nppgks.reportingsystem.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleHttpClientErrorException(
            HttpClientErrorException e) {

        String message = e.getMessage();
        log.error(message, e);

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleNotValidTagDataException(NotValidTagDataException e) {
        String message = e.getMessage();
        log.error(message, e);

        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<String> handleResourceAccessException(
            ResourceAccessException e) {

        String message = "OPC сервис недоступен";
        log.error(message, e);

        return new ResponseEntity<>(message, HttpStatus.SERVICE_UNAVAILABLE);
    }

}
