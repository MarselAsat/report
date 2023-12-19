package com.nppgks.reportingsystem.unit.reportgeneration.manual_reports.formulas;

import com.nppgks.reportingsystem.reportgeneration.manual_reports.formulas.Gost51858;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class Gost51858Test {

    @ParameterizedTest
    @MethodSource
    void defineOilSymbol(boolean export, double sulfur, double density20, double density15,
                         double out200, double out300, double water, double salt, double hydroSulfide, double meth, String expected) {
        String actual = Gost51858.defineOilSymbol(export, sulfur, density20, density15, out200, out300, water, salt, hydroSulfide, meth);
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> defineOilSymbol() {
        return Stream.of(
                Arguments.of(true, 0.3, 830, 830, 30, 55, 0.4, 90, 15, 30, "1.0э.1.1"),
                Arguments.of(false, 0.3, 830, 830, 30, 55, 0.4, 90, 15, 30, "1.0.1.1"),
                Arguments.of(true, 0.3, 830, 830, 28, 55, 0.4, 90, 15, 30, "1.1э.1.1"),
                Arguments.of(true, 0.3, 900, 830, 30, 55, 0.4, 90, 15, 30, "1.4э.1.1"),
                Arguments.of(true, 0.3, 860, 880, 30, 55, 0.4, 200, 15, 30, "1.3э.2.1"),
                Arguments.of(true, 0.3, 860, 880, 30, 55, 0.4, 200, 15, 80, "1.3э.2.2"),
                Arguments.of(false, 0.9, 860, 830, 30, 55, 0.4, 90, 15, 30, "2.2.1.1"),
                Arguments.of(false, 1.9, 830, 830, 30, 45, 0.4, 200, 15, 30, "3.0.2.1"),
                Arguments.of(true, 1.9, 830, 830, 28, 45, 0.4, 200, 15, 30, "3.2э.2.1")
                );
    }
}