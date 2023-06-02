package com.nppgks.reportingsystem.exception;

/**
 *  Это исключение выбрасывается,
 *  когда в OPC сервере нет соответствующих тегов, либо они неактивны
 */
public class MissingOpcTagException extends RuntimeException {

    public MissingOpcTagException(String message) {
        super(message);
    }
}
