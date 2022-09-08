package com.nppgks.reports.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class SingleDateTimeFormatterTest {

    @Test
    void formatToSinglePattern() {
        String formattedDate = SingleDateTimeFormatter
                .formatToSinglePattern(LocalDateTime.parse("2022-01-12T10:12:23.891336800"));
        Assertions.assertEquals(formattedDate, "12.01.2022 10:12");
    }
}