package com.nppgks.reportingsystem.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<String> handleFlightNotFound(
            ResourceAccessException e) {

        String message = "OPC service is unavailable";
        log.error(message, e);

        return new ResponseEntity<>(message, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
