package com.nppgks.reportingsystem.unit.util.time;

import com.nppgks.reportingsystem.util.time.DateTimeRange;
import com.nppgks.reportingsystem.util.time.DateTimeRangeBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.stream.Stream;

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

    @ParameterizedTest
    @MethodSource("provideArgsForBuildStartEndDateForShiftReport")
    void buildStartEndDateForShiftReport(LinkedHashMap<String, String> shiftNumAndStartTime, String reportName,
                                         String dtCreationStr, String expectedStartDt, String expectedEndDt){

        LocalDateTime dtCreation = LocalDateTime.parse(dtCreationStr);
        DateTimeRange dateTimeRange = DateTimeRangeBuilder.buildStartEndDateForShiftReport(shiftNumAndStartTime, reportName, dtCreation);
        assertThat(dateTimeRange.getStartDateTime()).isEqualTo(LocalDateTime.parse(expectedStartDt));
        assertThat(dateTimeRange.getEndDateTime()).isEqualTo(LocalDateTime.parse(expectedEndDt));
    }

    private static Stream<Arguments> provideArgsForBuildStartEndDateForShiftReport() {
        LinkedHashMap<String, String> shiftSchedule1 = new LinkedHashMap<>();
        shiftSchedule1.put("1", "10:30");
        shiftSchedule1.put("2", "18:30");
        shiftSchedule1.put("3", "22:00");

        LinkedHashMap<String, String> shiftSchedule2 = new LinkedHashMap<>();
        shiftSchedule2.put("1", "10:00");
        shiftSchedule2.put("2", "22:00");

        LinkedHashMap<String, String> shiftSchedule3 = new LinkedHashMap<>();
        shiftSchedule3.put("1", "00:00");
        shiftSchedule3.put("2", "12:00");

        return Stream.of(
                Arguments.of(shiftSchedule1,
                        "1", "2020-04-12T18:30",
                        "2020-04-12T10:30", "2020-04-12T18:30"),
                Arguments.of(shiftSchedule1,
                        "2", "2020-04-12T22:01",
                        "2020-04-12T18:30", "2020-04-12T22:00"),
                Arguments.of(shiftSchedule1,
                        "3", "2020-04-13T10:31",
                        "2020-04-12T22:00", "2020-04-13T10:30"),
                Arguments.of(shiftSchedule2,
                        "1", "2020-04-12T22:01",
                        "2020-04-12T10:00", "2020-04-12T22:00"),
                Arguments.of(shiftSchedule2,
                        "2", "2020-04-13T10:01",
                        "2020-04-12T22:00", "2020-04-13T10:00"),
                Arguments.of(shiftSchedule3,
                        "1", "2020-04-12T12:01",
                        "2020-04-12T00:00", "2020-04-12T12:00"),
                Arguments.of(shiftSchedule3,
                        "2", "2020-04-13T00:00",
                        "2020-04-12T12:00", "2020-04-13T00:00")
        );
    }
}