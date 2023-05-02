package com.nppgks.reportingsystem.unit.certification;

import com.nppgks.reportingsystem.certification.CalculationOldReportSystem;
import com.nppgks.reportingsystem.certification.CertificationAlgorithms;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class CertificationAlgorithmsTest {

    @ParameterizedTest
    @MethodSource("provideArgumentsForDensity15")
    void calculateDensity15(String product, double p, double temp, double overpressure, double expected) {
        double actual = CertificationAlgorithms.calculateDensity15(product, p, temp, overpressure);
        Assertions.assertThat(actual).isCloseTo(expected, Offset.offset(0.01));
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForDensity15")
    void calculateDensity15OldReportSystem(String product, double p, double temp, double overpressure, double expected) {
        float expectedF = (float) expected;
        float[] actual = CalculationOldReportSystem.calculateDensity15ByChange(product, (float) p, (float) temp, overpressure);
        Assertions.assertThat(actual[0]).isCloseTo(expectedF, Offset.offset(0.01F));
    }

    private static Stream<Arguments> provideArgumentsForDensity15() {
        return Stream.of(
                Arguments.of("нефтепродукт", 611.2, 15, 0, 611.2),
                Arguments.of("нефтепродукт", 1163.799, 15, 0, 1163.799),
                Arguments.of("нефтепродукт", 887.5, -50, 0, 840.9433194),
                Arguments.of("нефтепродукт", 887.5, 150, 0, 974.3867367),
                Arguments.of("нефтепродукт", 887.5, 15, 0, 887.49995),
                Arguments.of("нефтепродукт", 887.5, 15, 10.34, 881.4419124),
                Arguments.of("нефть", 836.5, 27.3, 1.3, 845.4)
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForCTLandCPL")
    void calculateCTL(String product, double p15, double temp, double overpressure, double expected) {
        double actual = CertificationAlgorithms.calculateCTL(product, p15, temp, overpressure);
        Assertions.assertThat(actual).isCloseTo(expected, Offset.offset(0.005));
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForCTLandCPL")
    void calculateCPL(String product, double p15, double temp, double overpressure, double expected) {
        double actual = CertificationAlgorithms.calculateCPL(product, p15, temp, overpressure);
        Assertions.assertThat(actual).isCloseTo(expected, Offset.offset(0.005));
    }

    private static Stream<Arguments> provideArgumentsForCTLandCPL() {
        return Stream.of(
                Arguments.of("нефтепродукт", 611.2, 15, 0, 1),
                Arguments.of("нефтепродукт", 1163.7999, 15, 0, 1),
                Arguments.of("нефтепродукт", 887.5, 15, 0, 1)
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForBeta")
    void calculateBeta(String product, double p15, double temp, double overpressure, double expected) {
        double actual = CertificationAlgorithms.calculateBeta(product, p15, temp, overpressure);
        Assertions.assertThat(actual).isCloseTo(expected, Offset.offset(0.005));
    }

    private static Stream<Arguments> provideArgumentsForBeta() {
        return Stream.of(
                Arguments.of("нефтепродукт", 611.2, 15, 0, 0.00164),
                Arguments.of("нефтепродукт", 1163.7999, 15, 0, 0.00045),
                Arguments.of("нефтепродукт", 887.5, 15, 0, 0.00078)
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForGamma")
    void calculateGamma(String product, double p15, double temp, double overpressure, double expected) {
        double actual = CertificationAlgorithms.calculateGamma(product, p15, temp, overpressure);
        Assertions.assertThat(actual).isCloseTo(expected, Offset.offset(0.005));
    }

    private static Stream<Arguments> provideArgumentsForGamma() {
        return Stream.of(
                Arguments.of("нефтепродукт", 611.2, 15, 0, 0.0001977),
                Arguments.of("нефть", 1163.7999, 15, 0, 0.0001977),
                Arguments.of("нефтепродукт", 887.5, 15, 0, 0.0001977)
        );
    }

    @Test
    void calculateWaterFraction() {
        double actual = CertificationAlgorithms.calculateWaterFraction(0.5, 1000, 850.5);
        double expected = 0.5879;
        Assertions.assertThat(actual).isCloseTo(expected, Offset.offset(0.015));
    }

    @Test
    void calculateChlorideSaltsFraction() {
        double actual = CertificationAlgorithms.calculateChlorideSaltsFraction(100, 850.5);
        double expected = 0.0118;
        Assertions.assertThat(actual).isCloseTo(expected, Offset.offset(0.015));
    }

    @Test
    void calculateQ_vol() {
        double actual = CertificationAlgorithms.calculateQ_vol(1000.001, 1000.001);
        double expected = 3600.001;
        Assertions.assertThat(actual).isCloseTo(expected, Offset.offset(0.005));
    }

    @Test
    void calculateQ_mass() {
        double actual = CertificationAlgorithms.calculateQ_mass(1000.001, 850.5001);
        double expected = 850.5001;
        Assertions.assertThat(actual).isCloseTo(expected, Offset.offset(0.005));
    }
}