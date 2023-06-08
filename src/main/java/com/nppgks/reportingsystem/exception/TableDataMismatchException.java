package com.nppgks.reportingsystem.exception;

/**
 * Это исключение выбрасывается, когда пользователь пытается редактировать таблицы так,
 * что их данные перестают соответствовать друг другу.
 * Например, есть редактор таблицы со столбцами "Тип отчета" и "Строка в отчете".
 * Строка в отчете тоже содержит тип отчета. Если эти два типа отчета не будут соответствовать,
 * выбросится это исключение
 */
public class TableDataMismatchException extends RuntimeException{

    public TableDataMismatchException(String message) {
        super(message);
    }
}
