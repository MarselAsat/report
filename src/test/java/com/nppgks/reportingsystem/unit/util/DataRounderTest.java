package com.nppgks.reportingsystem.unit.util;

import com.nppgks.reportingsystem.util.DataRounder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class DataRounderTest {

    public static Stream<Arguments> roundToSignificantDigits() {
        return Stream.of(Arguments.of(0.0009678, 3, 0.000968),
                Arguments.of(5.0009678, 3, 5.0),
                Arguments.of(5.0209678, 3, 5.02),
                Arguments.of(0.009678, 1, 0.01),
                Arguments.of(554.9209678, 3, 555),
                Arguments.of(554.4209678, 3, 554),
                Arguments.of(4567.009678, 3, 4567));
    }

    public static Stream<Arguments> roundDouble() {
        return Stream.of(Arguments.of(0.0004678, 3, 0),
                Arguments.of(5.0009678, 3, 5.001),
                Arguments.of(5.0209678, 3, 5.021),
                Arguments.of(0.009678, 3, 0.01));
    }

    @ParameterizedTest
    @MethodSource
    public void roundToSignificantDigits(double number, int sgnDigNum, double expected) {
        double actual = DataRounder.roundToSignificantDigits(number, sgnDigNum);
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource
    public void roundDouble(double number, int sgnDigNum, double expected) {
        double actual = DataRounder.roundDouble(number, sgnDigNum);
        Assertions.assertThat(actual).isEqualTo(expected);
    }
}
