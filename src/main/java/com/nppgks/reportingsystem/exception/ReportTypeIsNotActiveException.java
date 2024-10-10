package com.nppgks.reportingsystem.exception;

public class ReportTypeIsNotActiveException extends RuntimeException{
    public ReportTypeIsNotActiveException(String reportType) {
        super(reportType + " тип отчета не активен. Невозможно сгенерировать отчет");
    }
}
