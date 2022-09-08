package com.nppgks.reports.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SingleDateTimeFormatter {

    public static String formatToSinglePattern(LocalDateTime dateTime){
        return dateTime.format(
                DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }
}
