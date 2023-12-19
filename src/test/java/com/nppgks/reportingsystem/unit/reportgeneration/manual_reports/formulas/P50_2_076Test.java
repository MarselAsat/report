package com.nppgks.reportingsystem.unit.reportgeneration.manual_reports.formulas;

import com.nppgks.reportingsystem.reportgeneration.manual_reports.formulas.P50_2_076;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class P50_2_076Test {

    @ParameterizedTest
    @MethodSource
    public void calculateRho_15(String fluidType, double rho_tP, double t, double P, double expected){
        double actual = P50_2_076.calculateRho_15(fluidType, rho_tP, t, P);
        Assertions.assertThat(actual).isCloseTo(expected, Offset.offset(0.001));
    }

    private static Stream<Arguments> calculateRho_15() {
        return Stream.of(
                Arguments.of("нефть", 836.15, 27.3, 2.45, 843.5),
                Arguments.of("нефтепродукт", 836.15, 27.3, 2.45, 843.253),
                Arguments.of("нефтепродукт", 775, 27.3, 2.45, 782.8263),
                Arguments.of("нефтепродукт", 790, 27.3, 2.45, 797.385),
                Arguments.of("нефтепродукт", 840, 27.3, 2.45, 847.1036),
                Arguments.of("смазочное масло", 840, 27.3, 2.45, 847.1036)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void calculateRho_15_shouldThrowException(String fluidType, double rho_tP, double t, double P){
        Assertions.assertThatThrownBy(() -> P50_2_076.calculateRho_15(fluidType, rho_tP, t, P))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Неверная группа нефтепродуктов (%s) или плотность (%s)".formatted(fluidType, rho_tP));
    }

    private static Stream<Arguments> calculateRho_15_shouldThrowException() {
        return Stream.of(
                Arguments.of("blabla", 836.15, 27.3, 2.45),
                Arguments.of("нефтепродукт", 611, 27.3, 2.45, 843.253),
                Arguments.of("нефтепродукт", 1170, 27.3, 2.45, 782.8263),
                Arguments.of("нефть", 611, 27.3, 2.45, 797.385),
                Arguments.of("нефть", 1170, 27.3, 2.45, 847.1036),
                Arguments.of("смазочное масло", 800, 27.3, 2.45, 847.1036)
        );
    }
}
