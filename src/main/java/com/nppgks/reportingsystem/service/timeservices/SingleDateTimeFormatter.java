package com.nppgks.reportingsystem.service.timeservices;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
}
