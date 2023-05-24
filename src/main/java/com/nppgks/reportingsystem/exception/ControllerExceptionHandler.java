package com.nppgks.reportingsystem.exception;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

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

    @ExceptionHandler
    public ResponseEntity<String> handleMismatchedInputException(MismatchedInputException e) {
        String message = """
                    Произошла ошибка во время обработки массива %s.
                    Проверьте, что массив соответствует одному из шаблонов
                    [a1, a2, a3, ...] or [[a11, a12, a13, ...],[a21, a22, a23, ...], ...]
                    и что его длина соответствует количеству точек и измерений
                    """.formatted(e.getLocation());
        log.error(message, e);

        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
