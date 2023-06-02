package com.nppgks.reportingsystem.exception;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.nppgks.reportingsystem.controller.CalculationsController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = CalculationsController.class)
@Slf4j
public class CalculationsControllerExceptionHandler {


    @ExceptionHandler
    public ResponseEntity<String> handleMismatchedInputException(MismatchedInputException e) {
        String message = """
                    Произошла ошибка во время обработки массива %s.
                    Проверьте, что массив соответствует одному из шаблонов
                    [a1, a2, a3, ...] или [[a11, a12, a13, ...],[a21, a22, a23, ...], ...]
                    и что его длина соответствует количеству точек и измерений
                    """.formatted(e.getLocation());
        log.error(message, e);

        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler
    public ResponseEntity<String> handleArrayIndexOutOfBoundsException(ArrayIndexOutOfBoundsException e) {
        String message = """
                    Произошла ошибка во время прохода по массиву: %s.
                    Возможно, количество измерений и точек не соответствуют длинам массивов
                    """.formatted(e.getMessage());
        log.error(message, e);

        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleMissingOpcTagException(MissingOpcTagException e) {
        log.error(e.getMessage(), e);

        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
