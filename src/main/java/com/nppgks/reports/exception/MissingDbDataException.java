package com.nppgks.reports.exception;

public class MissingDbDataException extends RuntimeException {
    public MissingDbDataException(String message) {
        super(message);
    }
}
