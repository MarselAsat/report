package com.nppgks.reportingsystem.exception;

/**
 * Это исключение выбрасывается, когда значение тега, пришедшее с OPC сервера, невалидное.
 * Например, тег MFOrK подразумевает только два значения: либо MF, либо  K.
 * Если придет другое значение, то выбросится это исключение
 */
public class NotValidTagValueException extends RuntimeException {
    public NotValidTagValueException(String message) {
        super(message);
    }

    public NotValidTagValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
