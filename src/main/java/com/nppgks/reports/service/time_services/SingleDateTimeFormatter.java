package com.nppgks.reports.service.time_services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SingleDateTimeFormatter {

    public static String formatToSinglePattern(LocalDateTime dateTime){
        return dateTime.format(
                DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }
}
