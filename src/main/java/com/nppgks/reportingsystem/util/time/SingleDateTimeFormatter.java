package com.nppgks.reportingsystem.util.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

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
                DateTimeFormatter.ofPattern("HH:mm"));
    }
    public static String formatToSinglePattern(YearMonth yearMonth){
        return yearMonth.format(
                DateTimeFormatter.ofPattern("MM.yyyy"));
    }

    public static String formatMonthToRussian(LocalDate date){
        Map<Integer, String> monthsInRussian = new HashMap<>();
        monthsInRussian.put(1, "января");
        monthsInRussian.put(2, "февраля");
        monthsInRussian.put(3, "марта");
        monthsInRussian.put(4, "апреля");
        monthsInRussian.put(5, "мая");
        monthsInRussian.put(6, "июня");
        monthsInRussian.put(7, "июля");
        monthsInRussian.put(8, "августа");
        monthsInRussian.put(9, "сентября");
        monthsInRussian.put(10, "октября");
        monthsInRussian.put(11, "ноября");
        monthsInRussian.put(12, "декабря");

        return "\"%s\" %s %sг.".formatted(date.getDayOfMonth(), monthsInRussian.get(date.getMonthValue()), date.getYear());
    }
}
