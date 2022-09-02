package com.nppgks.reports.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

public class DateTimeRangeBuilder {
    public static DateTimeRange buildDateRangeForHourReport(String dtCreationStr){
        LocalDateTime dtCreationStart = LocalDateTime.parse(dtCreationStr + "T02:00");
        LocalDate endDay = LocalDate.parse(dtCreationStr).plusDays(1);
        LocalDateTime dtCreationEnd = LocalDateTime.parse(endDay + "T01:59");
        return new DateTimeRange(dtCreationStart, dtCreationEnd);
    }

    public static DateTimeRange buildDateRangeForDailyReport(String dtCreationStr){
        LocalDateTime dtCreationStart = LocalDateTime.parse(dtCreationStr + "T00:00").plusDays(1);
        LocalDateTime dtCreationEnd = LocalDateTime.parse(dtCreationStr + "T23:59").plusDays(1);
        return new DateTimeRange(dtCreationStart, dtCreationEnd);
    }

    public static DateTimeRange buildDateRangeForMonthReport(String dtCreationStr){
        LocalDate dateForMonthReport = LocalDate.parse(dtCreationStr).plusMonths(1);
        Month month = dateForMonthReport.getMonth();
        int year = dateForMonthReport.getYear();
        LocalDateTime dtCreationStart = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime dtCreationEnd = LocalDateTime.of(year, month, month.length(false), 23, 59);
        return new DateTimeRange(dtCreationStart, dtCreationEnd);
    }

    public static DateTimeRange buildDateRangeForYearReport(String dtCreationStr){
        LocalDate dateForYearReport = LocalDate.parse(dtCreationStr).plusYears(1);
        int y = dateForYearReport.getYear();
        LocalDateTime dtCreationStart = LocalDateTime.of(y, 1, 1, 0, 0);
        LocalDateTime dtCreationEnd = LocalDateTime.of(y, 12, 31, 23, 59);
        return new DateTimeRange(dtCreationStart, dtCreationEnd);
    }
}
