package com.nppgks.reportingsystem.exception;

/**
 *  Это исключение выбрасывается,
 *  когда в базе данных не хватает данных (нет необходимых тегов или отчетов)
 */
public class MissingDbDataException extends RuntimeException {
    public MissingDbDataException(String message) {
        super(message);
    }
}
