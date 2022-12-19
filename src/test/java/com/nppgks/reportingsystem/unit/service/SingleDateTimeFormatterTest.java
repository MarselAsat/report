package com.nppgks.reportingsystem.unit.service;

import com.nppgks.reportingsystem.service.timeservices.SingleDateTimeFormatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

class SingleDateTimeFormatterTest {

    @Test
    void formatDtToSinglePattern() {
        String formattedDate = SingleDateTimeFormatter
                .formatToSinglePattern(LocalDateTime.parse("2022-01-12T10:12:23.891336800"));
        Assertions.assertEquals(formattedDate, "12.01.2022 10:12");
    }

    @Test
    void formatDateToSinglePattern() {
        String formattedDate = SingleDateTimeFormatter
                .formatToSinglePattern(LocalDate.parse("2022-01-12"));
        Assertions.assertEquals(formattedDate, "12.01.2022");
    }
}