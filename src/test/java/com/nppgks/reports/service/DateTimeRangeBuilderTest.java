package com.nppgks.reports.service;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

class DateTimeRangeBuilderTest {

    @Test
    void buildStartEndDateForHourReport() {
        DateTimeRange dateTimeRange1 = DateTimeRangeBuilder.buildStartEndDateForHourReport(
                LocalDateTime.parse("2022-04-21T20:01:10"));
        assertThat(dateTimeRange1.getStartDateTime()).isEqualTo("2022-04-21T19:00:00");
        assertThat(dateTimeRange1.getEndDateTime()).isEqualTo("2022-04-21T20:00:00");

        DateTimeRange dateTimeRange2 = DateTimeRangeBuilder.buildStartEndDateForHourReport(
                LocalDateTime.parse("2022-09-01T00:01:10"));
        assertThat(dateTimeRange2.getStartDateTime()).isEqualTo("2022-08-31T23:00");
        assertThat(dateTimeRange2.getEndDateTime()).isEqualTo("2022-09-01T00:00");
    }

    @Test
    void buildStartEndDateForDailyReport() {
        DateTimeRange dateTimeRange1 = DateTimeRangeBuilder.buildStartEndDateForDailyReport(
                LocalDateTime.parse("2022-04-21T20:10:00"));
        assertThat(dateTimeRange1.getStartDateTime()).isEqualTo("2022-04-20T20:00");
        assertThat(dateTimeRange1.getEndDateTime()).isEqualTo("2022-04-21T20:00");

        DateTimeRange dateTimeRange2 = DateTimeRangeBuilder.buildStartEndDateForDailyReport(
                LocalDateTime.parse("2022-09-01T00:01:12"));
        assertThat(dateTimeRange2.getStartDateTime()).isEqualTo("2022-08-31T00:00");
        assertThat(dateTimeRange2.getEndDateTime()).isEqualTo("2022-09-01T00:00");
    }

    @Test
    void buildStartEndDateForMonthReport() {
        DateTimeRange dateTimeRange1 = DateTimeRangeBuilder.buildStartEndDateForMonthReport(
                LocalDateTime.parse("2022-04-01T10:10:10"));
        assertThat(dateTimeRange1.getStartDateTime()).isEqualTo("2022-03-01T10:00");
        assertThat(dateTimeRange1.getEndDateTime()).isEqualTo("2022-04-01T10:00");

        DateTimeRange dateTimeRange2 = DateTimeRangeBuilder.buildStartEndDateForMonthReport(
                LocalDateTime.parse("2022-01-01T00:01:01"));
        assertThat(dateTimeRange2.getStartDateTime()).isEqualTo("2021-12-01T00:00");
        assertThat(dateTimeRange2.getEndDateTime()).isEqualTo("2022-01-01T00:00");
    }

    @Test
    void buildStartEndDateForYearReport() {
        DateTimeRange dateTimeRange1 = DateTimeRangeBuilder.buildStartEndDateForYearReport(
                LocalDateTime.parse("2022-01-01T10:10:10"));
        assertThat(dateTimeRange1.getStartDateTime()).isEqualTo("2021-01-01T10:00");
        assertThat(dateTimeRange1.getEndDateTime()).isEqualTo("2022-01-01T10:00");

        DateTimeRange dateTimeRange2 = DateTimeRangeBuilder.buildStartEndDateForYearReport(
                LocalDateTime.parse("2022-01-01T00:01:10"));
        assertThat(dateTimeRange2.getStartDateTime()).isEqualTo("2021-01-01T00:00");
        assertThat(dateTimeRange2.getEndDateTime()).isEqualTo("2022-01-01T00:00");
    }
}