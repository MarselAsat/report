package com.nppgks.reportingsystem.unit.reportgeneration.manual_reports.formulas;

import com.nppgks.reportingsystem.reportgeneration.manual_reports.formulas.MI3313Formulas;
import com.nppgks.reportingsystem.unit.Double2DimArrayAssert;
import com.nppgks.reportingsystem.unit.DoubleArrayAssert;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


public class MI3313FormulasTest {

    @Test
    public void calculateM_eji_multipleEsrm() {
        List<double[][]> M_ejik = new ArrayList<>();
        double[][] array1 = {{1.0, 2.0}, {3.0, 4.0}};
        double[][] array2 = {{5.0, 6.0}, {7.0, 8.0}};
        M_ejik.add(array1);
        M_ejik.add(array2);

        double[][] expected = {{6.0, 8.0}, {10.0, 12.0}};
        double[][] actual = MI3313Formulas.calculateM_eji_multipleEsrm(M_ejik);

        Double2DimArrayAssert.assertThat(actual).isCloseTo(expected, 0.001);
    }


    @Test
    public void calculateM_ejik() {
        List<double[][]> N_ejik = Arrays.asList(
                new double[][]{{1, 2, 3}, {4, 5, 6}},
                new double[][]{{7, 8, 9}, {10, 11, 12}}
        );
        double[] K_PMEk = {2, 3};
        List<double[][]> expected = Arrays.asList(
                new double[][]{{0.5, 1, 1.5}, {2, 2.5, 3}},
                new double[][]{{2.333, 2.667, 3}, {3.333, 3.667, 4}}
        );

        List<double[][]> actual = MI3313Formulas.calculateM_ejik(N_ejik, K_PMEk);

        for (int k = 0; k < N_ejik.size(); k++) {
            Double2DimArrayAssert.assertThat(actual.get(k)).isCloseTo(expected.get(k), 0.001);
        }
    }

    @Test
    void calculateQ_jik() {
        List<double[][]> M_ejik = Arrays.asList(
                new double[][]{{1, 2}, {4, 5}},
                new double[][]{{3, 4}, {10, 5}}
        );
        double[][] T_ji = {{1.0, 2.0}, {2.0, 2.5}};
        List<double[][]> expected = Arrays.asList(
                new double[][]{{3600, 3600}, {7200, 7200}},
                new double[][]{{10800, 7200}, {18000, 7200}}
        );

        List<double[][]> actual = MI3313Formulas.calculateQ_jik(M_ejik, T_ji);
        for (int k = 0; k < M_ejik.size(); k++) {
            Double2DimArrayAssert.assertThat(actual.get(k)).isCloseTo(expected.get(k), 0.001);
        }
    }

    @Test
    public void calculateMForK() {
        double[] MForKj = {1.0, 2.0, 3.0, 4.0, 5.0};
        double expected = 3.0;
        double actual = MI3313Formulas.calculateMForK(MForKj);
        assertThat(actual).isCloseTo(expected, Offset.offset(0.001));
    }

    @Test
    public void calculateMForK_ji() {
        double[][] M_eji = {{1, 2}, {3, 4}};
        double[][] M_ji = {{2, 2}, {2, 2}};
        double MForK_set = 2.0;

        double[][] expected = {{1, 2}, {3, 4}};
        double[][] actual = MI3313Formulas.calculateMForK_ji(M_eji, M_ji, MForK_set);

        Double2DimArrayAssert.assertThat(actual).isCloseTo(expected, 0.001);
    }

    // формула (16)
    @Test
    public void calculateS_j() {
        double[][] MForK_ji = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        double[] MForK_j = MI3313Formulas.calculateMForK_j(MForK_ji);
        double[] expected = {50, 20, 12.5};

        double[] actual = MI3313Formulas.calculateS_j(MForK_ji, MForK_j);

        DoubleArrayAssert.assertThat(actual).isCloseTo(expected, 0.001);
    }

    // формула (18)
    @Test
    public void calculateTheta_sigma() {
        double actual = MI3313Formulas.calculateTheta_sigma(1.0, 2.0, 3.0, 4.0, 5.0, 6.0);
        assertThat(actual).isCloseTo(10.493, Offset.offset(0.001));
    }

    // формула (21)
    @Test
    public void calculateTheta_A() {
        double[] MForK_j = {10.0, 20.0, 30.0, 40.0, 50.0};
        double MForK = MI3313Formulas.calculateMForK(MForK_j);
        double expected = 66.666;

        double actual = MI3313Formulas.calculateTheta_A(MForK_j, MForK);

        assertThat(actual).isCloseTo(expected, Offset.offset(0.001));
    }

    // формула (22)
    @Test
    public void calculateTheta_Z() {
        double ZS = 10.0;
        double Q_min = 2.0;
        double expected = 500.0;
        double actual = MI3313Formulas.calculateTheta_Z(ZS, Q_min);
        assertThat(actual).isCloseTo(expected, Offset.offset(0.001));
    }

    // формула (23)
    @Test
    public void testCalculateTheta_Mt() {
        double delta_tdop = 2.5;
        double Q_t = 0.8;
        double delta_t = 1.2;
        double Q_min = 0.5;
        double expected = 4.8;
        double actual = MI3313Formulas.calculateTheta_Mt(delta_tdop, Q_t, delta_t, Q_min);
        assertThat(actual).isCloseTo(expected, Offset.offset(0.001));
    }

    // формула (25) и (27)
    @Test
    public void calculateDelta_tOrP() {
        double tOrP_max = 10.0;
        double tOrP_P = 5.0;
        double tOrP_min = 2.0;

        double actual = MI3313Formulas.calculateDelta_tOrP(tOrP_max, tOrP_P, tOrP_min);

        assertThat(actual).isCloseTo(5, Offset.offset(0.001));
    }

    // формула (26)
    @Test
    public void calculateTheta_MP() {
        double delta_Pdop = 2.5;
        double deltaP = 0.05;
        double expected = 1.25;
        double actual = MI3313Formulas.calculateTheta_MP(delta_Pdop, deltaP);
        assertThat(actual).isCloseTo(expected, Offset.offset(0.001));
    }

    // формула (28)
    @Test
    public void testCalculateS_0j_PositiveCase() {
        double[] S_j = {1, 2, 3, 4, 5};
        int measureCount = 4;
        double[] expected = {0.5, 1, 1.5, 2, 2.5};
        double[] actual = MI3313Formulas.calculateS_0j(S_j, measureCount);
        DoubleArrayAssert.assertThat(actual).isCloseTo(expected, 0.001);
    }

    // формула (31)
    @ParameterizedTest
    @MethodSource
    public void testCalculateDelta(double epsilon, double theta_sigma, double S_0, double t_sigma, double S_sigma, double expected) {
        double actual = MI3313Formulas.calculateDelta(epsilon, theta_sigma, S_0, t_sigma, S_sigma);
        assertThat(actual).isCloseTo(expected, Offset.offset(0.001));
    }

    public static Stream<Arguments> testCalculateDelta() {
        return Stream.of(Arguments.of(2, 10, 20, 5, 5, 2),
                Arguments.of(2, 10, 10, 5, 5, 25),
                Arguments.of(2, 100, 2, 5, 5, 100));
    }

    @Test
    public void calculateS_0() {
        double[] epsilon_j = {1.0, 2.0, 3.0, 4.0};
        double[] S_0j = {10.0, 50.0, 30.0, 40.0};
        double expected = 40.0;

        double actual = MI3313Formulas.calculateS_0(epsilon_j, S_0j);

        assertThat(actual).isCloseTo(expected, Offset.offset(0.001));
    }

    // формула (32)
    @Test
    public void testCalculateT_sigma_positiveCase() {
        double epsilon = 0.5;
        double theta_sigma = 0.2;
        double S_0 = 100;
        double S_theta = 50;

        double expected = (epsilon + theta_sigma) / (S_0 + S_theta);
        double actual = MI3313Formulas.calculateT_sigma(epsilon, theta_sigma, S_0, S_theta);

        assertThat(actual).isCloseTo(expected, Offset.offset(0.001));
    }

    // формула (33)
    @Test
    public void testCalculateS_sigma_positive() {
        double S_theta = 3.0;
        double S_0 = 4.0;
        double expected = 5.0;
        double actual = MI3313Formulas.calculateS_sigma(S_theta, S_0);
        assertThat(actual).isCloseTo(expected, Offset.offset(0.001));
    }

    // формула (34)
    @Test
    public void testCalculateS_theta_positive() {
        double theta_M = 1.0;
        double theta_IVK = 2.0;
        double theta_A = 3.0;
        double theta_Z = 4.0;
        double theta_Mt = 5.0;
        double theta_MP = 6.0;

        double expected = 5.5075;

        double actual = MI3313Formulas.calculateS_theta(theta_M, theta_IVK, theta_A, theta_Z, theta_Mt, theta_MP);

        assertThat(actual).isCloseTo(expected, Offset.offset(0.001));
    }

    @Test
    public void testCalculateT_095j() {
        // кол-во измерений
        int n = 5;
        // кол-во точек
        int m = 3;
        double[] actual = MI3313Formulas.calculateT_095j(n, m);
        double[] expected = new double[]{2.776, 2.776, 2.776};
        DoubleArrayAssert.assertThat(actual).isCloseTo(expected, 0.001);
    }

}
