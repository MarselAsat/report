package com.nppgks.reportingsystem.exception;

/**
 *  Это исключение выбрасывается, когда данные, пришедшие по определенному тегу, невалидные.
 *  Например, тег MFOrK подразумевает только два значения: либо MF, либо  K.
 *  Если придет другое значение, то выбросится это исключение
 */
public class NotValidTagDataException extends RuntimeException{
    public NotValidTagDataException(String message) {
        super(message);
    }
}
