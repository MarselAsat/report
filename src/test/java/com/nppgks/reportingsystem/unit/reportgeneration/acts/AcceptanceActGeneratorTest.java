package com.nppgks.reportingsystem.unit.reportgeneration.acts;

import com.nppgks.reportingsystem.reportgeneration.manual_reports.acts_passport.AcceptanceActGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class AcceptanceActGeneratorTest {

    @ParameterizedTest
    @MethodSource("provideArgumentsForGetCurrentShift")
    void getCurrentShift(LinkedHashMap<String, String> shiftNumToSTartTime, LocalTime currentTime, int expected) {
        int actual = AcceptanceActGenerator.getCurrentShift(currentTime, shiftNumToSTartTime);
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> provideArgumentsForGetCurrentShift() {
        LinkedHashMap<String, String> shiftNumToStartTime1 = new LinkedHashMap<>();
        shiftNumToStartTime1.put("1", "10:00");
        shiftNumToStartTime1.put("2", "18:00");
        shiftNumToStartTime1.put("3", "02:00");

        LinkedHashMap<String, String> shiftNumToStartTime2 = new LinkedHashMap<>();
        shiftNumToStartTime2.put("1", "10:00");
        shiftNumToStartTime2.put("2", "22:00");

        LinkedHashMap<String, String> shiftNumToStartTime3 = new LinkedHashMap<>();
        shiftNumToStartTime3.put("1", "10:00");

        LinkedHashMap<String, String> shiftNumToStartTime4 = new LinkedHashMap<>();
        shiftNumToStartTime4.put("1", "00:00");
        shiftNumToStartTime4.put("2", "12:00");
        shiftNumToStartTime4.put("3", "18:00");

        return Stream.of(
                Arguments.of(
                        shiftNumToStartTime1,
                        LocalTime.parse("09:00"), 3),
                Arguments.of(
                        shiftNumToStartTime1,
                        LocalTime.parse("16:00"), 1),
                Arguments.of(
                        shiftNumToStartTime1,
                        LocalTime.parse("22:00"), 2),
                Arguments.of(
                        shiftNumToStartTime2,
                        LocalTime.parse("09:00"), 2),
                Arguments.of(
                        shiftNumToStartTime2,
                        LocalTime.parse("15:00"), 1),
                Arguments.of(
                        shiftNumToStartTime3,
                        LocalTime.parse("09:00"), 1),
                Arguments.of(
                        shiftNumToStartTime3,
                        LocalTime.parse("22:00"), 1),
                Arguments.of(
                        shiftNumToStartTime4,
                        LocalTime.parse("23:59"), 3),
                Arguments.of(
                        shiftNumToStartTime4,
                        LocalTime.parse("23:59"), 3),
                Arguments.of(
                        shiftNumToStartTime4,
                        LocalTime.parse("00:01"), 1),
                Arguments.of(
                        shiftNumToStartTime4,
                        LocalTime.parse("11:59"), 1),
                Arguments.of(
                        shiftNumToStartTime4,
                        LocalTime.parse("12:59"), 2),
                Arguments.of(
                        shiftNumToStartTime4,
                        LocalTime.parse("17:59"), 2),
                Arguments.of(
                        shiftNumToStartTime4,
                        LocalTime.parse("18:59"), 3)
        );
    }
}