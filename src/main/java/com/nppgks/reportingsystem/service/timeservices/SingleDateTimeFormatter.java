package com.nppgks.reportingsystem.service.timeservices;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class SingleDateTimeFormatter {

    public static String formatToSinglePattern(LocalDateTime dateTime){
        return dateTime.format(
                DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

    public static String formatToSinglePattern(LocalDate date){
        return date.format(
                DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String formatToSinglePattern(LocalTime time){
        return time.format(
                DateTimeFormatter.ofPattern("hh:mm"));
    }
    public static String formatToSinglePattern(YearMonth yearMonth){
        return yearMonth.format(
                DateTimeFormatter.ofPattern("MM.yyyy"));
    }
}
