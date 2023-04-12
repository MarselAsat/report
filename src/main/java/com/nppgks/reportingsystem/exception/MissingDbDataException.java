package com.nppgks.reportingsystem.exception;

public class MissingDbDataException extends RuntimeException {
    public MissingDbDataException(String message) {
        super(message);
    }
}
