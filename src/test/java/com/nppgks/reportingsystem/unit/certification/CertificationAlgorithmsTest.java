package com.nppgks.reportingsystem.unit.certification;

import com.nppgks.reportingsystem.certification.CertificationAlgorithms;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class CertificationAlgorithmsTest {

    @ParameterizedTest
    @MethodSource("provideArgumentsForDensity15")
    @Disabled
    void calculateDensity15(String product, double p, double temp, double overpressure, double expected) {
        double actual = CertificationAlgorithms.calculateDensity15(product, p, temp, overpressure);
        assertThat(actual).isCloseTo(expected, Offset.offset(0.01));
    }

//    @ParameterizedTest
//    @MethodSource("provideArgumentsForDensity15")
//    void calculateDensity15OldReportSystem(String product, double p, double temp, double overpressure, double expected) {
//        float expectedF = (float) expected;
//        float[] actual = CalculationOldReportSystem.calculateDensity15ByChange(product, (float) p, (float) temp, overpressure);
//        Assertions.assertThat(actual[0]).isCloseTo(expectedF, Offset.offset(0.01F));
//    }

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

    @Test
    void calculateMass_Net(){
        double M = 100;
        double W_B = 5;
        double W_MP = 2;
        double W_XC = 2;

        double actual = CertificationAlgorithms.calculateMass_Net(M, W_B, W_MP, W_XC);
        double expected = 91;
        assertThat(actual).isCloseTo(expected, Offset.offset(0.005));
    }

    @Test
    void calculateMass_ballast(){
        double M = 100;
        double W_B = 5;
        double W_MP = 2;
        double W_XC = 2;

        double actual = CertificationAlgorithms.calculateMass_ballast(M, W_B, W_MP, W_XC);
        double expected = 9;
        assertThat(actual).isCloseTo(expected, Offset.offset(0.005));
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForCTL")
    void calculateCTL(String product, double p15, double temp, double expected) {
        double actual = CertificationAlgorithms.calculateCTL(product, p15, temp);
        assertThat(actual).isCloseTo(expected, Offset.offset(0.005));
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForCPL")
    void calculateCPL(double p15, double temp, double overpressure, double expected) {
        double actual = CertificationAlgorithms.calculateCPL(p15, temp, overpressure);
        assertThat(actual).isCloseTo(expected, Offset.offset(0.005));
    }

    private static Stream<Arguments> provideArgumentsForCTL() {
        return Stream.of(
                Arguments.of("нефть", 611.2, 15, 1),
                Arguments.of("нефть", 1163.7999, 15, 1),
                Arguments.of("нефть", 887.5, 15, 1)
        );
    }

    private static Stream<Arguments> provideArgumentsForCPL() {
        return Stream.of(
                Arguments.of(611.2, 15, 0, 1),
                Arguments.of(1163.7999, 15, 0, 1),
                Arguments.of(887.5, 15, 0, 1)
        );
    }



    @ParameterizedTest
    @MethodSource("provideArgumentsForBeta")
    void calculateBeta(String product, double p15, double expected) {
        double actual = CertificationAlgorithms.calculateBeta(product, p15);
        assertThat(actual).isCloseTo(expected, Offset.offset(0.005));
    }

    private static Stream<Arguments> provideArgumentsForBeta() {
        return Stream.of(
                Arguments.of("нефтепродукт", 611.2, 0.00164),
                Arguments.of("нефтепродукт", 1163.7999, 0.00045),
                Arguments.of("нефтепродукт", 887.5, 0.00078)
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForGamma")
    void calculateGamma(double p15, double temp, double expected) {
        double actual = CertificationAlgorithms.calculateGamma(p15, temp);
        assertThat(actual).isCloseTo(expected, Offset.offset(0.005));
    }

    private static Stream<Arguments> provideArgumentsForGamma() {
        return Stream.of(
                Arguments.of(611.2, 15, 0.0001977),
                Arguments.of(1163.7999, 15, 0.0001977),
                Arguments.of(887.5, 15, 0.0001977)
        );
    }

    @Test
    void calculateWaterFraction() {
        double actual = CertificationAlgorithms.calculateWaterFraction(0.5, 1000, 850.5);
        double expected = 0.5879;
        assertThat(actual).isCloseTo(expected, Offset.offset(0.015));
    }

    @Test
    void calculateChlorideSaltsFraction() {
        double actual = CertificationAlgorithms.calculateChlorideSaltsFraction(100, 850.5);
        double expected = 0.0118;
        assertThat(actual).isCloseTo(expected, Offset.offset(0.015));
    }

    @Test
    void calculateQ_mass_through_CPM() {
        double[][] M_pu_ij = new double[][]{
                {145, 133, 91},
                {146, 132, 90}};
        double[][] T_ij = new double[][]{
                {5, 6, 7},
                {5.1, 6.1, 7}};
        double[][] actual = CertificationAlgorithms.calculateQ_mass_through_CPM(M_pu_ij, T_ij);
        double[][] expected = new double[][]{
                {104400, 79800, 46800},
                {103058.823, 77901.639, 46285.714}};

        for (int i = 0; i < M_pu_ij.length; i++) {
            for (int j = 0; j < M_pu_ij[0].length; j++) {
                assertThat(actual[i][j]).isCloseTo(expected[i][j], Offset.offset(0.005));
            }
        }
    }

    @Test
    void calculateQ_min() {
        double[] Q_j = new double[]{450, 560, 345};
        double actual = CertificationAlgorithms.calculateQ_min(Q_j);
        double expected = 345;
        assertThat(actual).isCloseTo(expected, Offset.offset(0.005));
    }

    @Test
    void calculateQ_max() {
        double[] Q_j = new double[]{450, 560, 345};
        double actual = CertificationAlgorithms.calculateQ_max(Q_j);
        double expected = 560;
        assertThat(actual).isCloseTo(expected, Offset.offset(0.005));
    }

    @Test
    void calculateM_ij() {
        double[][] N_ij = new double[][]{
                {33000, 30000, 20000},
                {33500, 30200, 25000}};
        double K_pm = 226;
        double[][] actual = CertificationAlgorithms.calculateM_ij(N_ij, K_pm);
        double[][] expected = new double[][]{
                {146.0176, 132.743, 88.495},
                {148.23, 133.628, 110.619}
        };

        for (int i = 0; i < N_ij.length; i++) {
            for (int j = 0; j < N_ij[0].length; j++) {
                assertThat(actual[i][j]).isCloseTo(expected[i][j], Offset.offset(0.005));
            }
        }
    }

    @Test
    void calculateM_e_ij() {
        double[][] N_ij1 = new double[][]{
                {33000, 30000, 20000},
                {33500, 30200, 25000}};
        double[][] N_ij2 = new double[][]{
                {32000, 29000, 19000},
                {32500, 29200, 24000}};
        List<double[][]> N_ijList = List.of(N_ij1, N_ij2);
        List<Double> K_pmList = List.of(226d, 226d);
        double[][] actual = CertificationAlgorithms.calculateM_e_ij(K_pmList, N_ijList);
        double[][] expected = new double[][]{
                {287.61, 261.06, 172.565},
                {292.035, 262.831, 216.813}
        };

        for (int i = 0; i < N_ij1.length; i++) {
            for (int j = 0; j < N_ij1[0].length; j++) {
                assertThat(actual[i][j]).isCloseTo(expected[i][j], Offset.offset(0.005));
            }
        }
    }

    @Test
    void calculateQ_j() {
        double[][] M_e_ij = new double[][]{
                {145, 133, 91},
                {146, 132, 90}};
        double[][] T_ij = new double[][]{
                {5, 6, 7},
                {5.1, 6.1, 7}};
        double[] actual = CertificationAlgorithms.calculateQ_j(M_e_ij, T_ij);
        double[] expected = new double[]{103729.4115, 78850.8195, 46542.857};

        for (int j = 0; j < M_e_ij[0].length; j++) {
            assertThat(actual[j]).isCloseTo(expected[j], Offset.offset(0.005));
        }
    }

    @Test
    void calculateK_M() {
        double[][] M_pu_ij = new double[][]{
                {145, 133, 91},
                {146, 132, 90}};
        double[][] M_ij = new double[][]{
                {145.01, 133, 91.1},
                {147, 132, 90}};
        double K_m_yct = 226;
        double actual = CertificationAlgorithms.calculateK_M(M_pu_ij, M_ij, K_m_yct);
        double expected = 225.7;

        assertThat(actual).isCloseTo(expected, Offset.offset(0.005));
    }

    @Test
    void calculateMF() {
        double[][] M_pu_ij = new double[][]{
                {145, 133, 91},
                {146, 132, 90}};
        double[][] M_ij = new double[][]{
                {145.01, 133, 91.1},
                {147, 132, 90}};
        double MF_yct = 1;
        double actual = CertificationAlgorithms.calculateMF(M_pu_ij, M_ij, MF_yct);
        double expected = 1;

        assertThat(actual).isCloseTo(expected, Offset.offset(0.005));
    }

    @Test
    void calculateS_j() {
        double[][] MF_ij = new double[][]{
                {1, 1, 0.9998},
                {0.999, 0.9999, 1}};
        double[] MF_j = new double[]{0.9995, 0.99995, 0.9999};
        double[] actual = CertificationAlgorithms.calculateS_j(MF_ij, MF_j);
        double[] expected = new double[]{0.070746, 0.0070714, 0.0141435};

        for (int j = 0; j < MF_ij[0].length; j++) {
            assertThat(actual[j]).isCloseTo(expected[j], Offset.offset(0.005));
        }
    }

    @Test
    void calculateS0_j() {
        double[] S_j = new double[]{0.07074, 0.0070714, 0.01414};
        int measureCount = 2;
        double[] actual = CertificationAlgorithms.calculateS0_j(S_j, measureCount);
        double[] expected = new double[]{0.05002, 0.00500023, 0.009998};

        for (int j = 0; j < S_j.length; j++) {
            assertThat(actual[j]).isCloseTo(expected[j], Offset.offset(0.005));
        }
    }

    @Test
    void calculateEps() {
        double[] t095_j = new double[]{2.57, 2.57, 2.57};
        double[] S0_j = new double[]{0.001, 0, 0.002};
        double actual = CertificationAlgorithms.calculateEps(t095_j, S0_j);
        double expected = 0.00514;

        assertThat(actual).isCloseTo(expected, Offset.offset(0.005));
    }

    @Test
    void calculateDelta() {
        double eps = 0.003;
        double theta = 0.1;
        double S0 = 0.12;
        double actual = CertificationAlgorithms.calculateDelta(eps, theta, S0, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01);
        double expected = 0.09093;

        assertThat(actual).isCloseTo(expected, Offset.offset(0.005));
    }

    @Test
    void calculateQ_mass() {
        double actual = CertificationAlgorithms.calculateQ_mass(1000.001, 850.5001);
        double expected = 850.5001;
        assertThat(actual).isCloseTo(expected, Offset.offset(0.005));
    }

    @Test
    void calculateKinematicViscosity() {
        double actual = CertificationAlgorithms.calculateKinematicViscosity(50.00001, 850.5001);
        double expected = 58.789;
        assertThat(actual).isCloseTo(expected, Offset.offset(0.005));
    }
}