package com.nppgks.reportingsystem.unit.reportgeneration.calculations.mi3622;

import com.nppgks.reportingsystem.reportgeneration.calculations.mi3622.MI3622Calculation;
import com.nppgks.reportingsystem.reportgeneration.calculations.mi3622.data.InitialData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MI3622CalculationSinglePointTest {

    MI3622Calculation MI3622Calculation;

    @BeforeAll
    void prepareData() {
        InitialData initialData = new InitialData();
        double[][] Q_ij = new double[][]{
                {87.5, 87.5, 87.5, 87.5, 87.6}
        };
        Q_ij = transpose(Q_ij);
        double[][] N_e_ij = new double[][]{
                {33002, 33013, 33013, 33012, 33012}
        };
        N_e_ij = transpose(N_e_ij);
        double[][] N_p_ij = new double[][]{
                {33002, 33012, 33012, 33013, 33013}
        };
        N_p_ij = transpose(N_p_ij);
        double[][] T_ij = new double[][]{
                {5.7, 5.3, 5.3, 5.3, 5.3}
        };
        T_ij = transpose(T_ij);
        double[][] M_ij = new double[][]{
                {145.756, 145.8, 145.8, 145.8040, 145.8040},
        };
        M_ij = transpose(M_ij);

        double[] Q_e_arr = new double[]{21, 65, 87.5, 272.0};

        double[] K_e_arr = new double[]{226.4151, 226.4151, 226.4151, 226.4151};
        double f_p_max = 10000;
        double Q_p_max = 159;
        double MF_p = 1;
        double theta_e = 0.11;
        double theta_N = 0.02;
        double ZS = 0.041;
        initialData.setMeasureCount(Q_ij.length);
        initialData.setPointsCount(Q_ij[0].length);
        initialData.setQ_ij(Q_ij);
        initialData.setN_e_ij(N_e_ij);
        initialData.setN_p_ij(N_p_ij);
        initialData.setT_ij(T_ij);
        initialData.setM_ij(M_ij);
        initialData.setF_p_max(f_p_max);
        initialData.setQ_p_max(Q_p_max);
        initialData.setMF_p(MF_p);
        initialData.setK_e_arr(K_e_arr);
        initialData.setQ_e_arr(Q_e_arr);
        initialData.setTheta_e(theta_e);
        initialData.setTheta_N(theta_N);
        initialData.setZS(ZS);
        initialData.setZeroStabilityCorr(true);
        MI3622Calculation = new MI3622Calculation(initialData);
    }

    @Test
    void checkMe_ij() {
        double[][] Me_ij = MI3622Calculation.calculateM_e_ij();
        double[][] Me_ijRef = new double[][]{
                {145.7560, 145.804, 145.804, 145.8, 145.8}
        };
        Me_ijRef = transpose(Me_ijRef);
        for (int i = 0; i < Me_ij.length; i++) {
            for (int j = 0; j < Me_ij[0].length; j++) {
                assertThat(Me_ij[i][j]).isCloseTo(Me_ijRef[i][j], within(0.005));
            }
        }
    }

    @Test
    void checkKnm() {
        double Kpm = MI3622Calculation.calculateK_pm();
        double KpmRef = 226.4151;
        assertThat(Kpm).isCloseTo(KpmRef, within(0.05));
    }

    @Test
    void checkMF_j() {
        double[] MF_j = MI3622Calculation.calculateMF_j();
        double[] MF_jRef = {1};
        for (int j = 0; j < MF_j.length; j++) {
            assertThat(MF_j[j]).isCloseTo(MF_jRef[j], within(0.0002));
        }
    }

    @Test
    void checkf_ij() {
        double[][] f_ij = MI3622Calculation.calculateF_ij();
        double[][] f_ijRef = new double[][]{
                {5500.334, 6602.4, 6602.4, 6603.6, 6602.6}
        };
        f_ijRef = transpose(f_ijRef);
        for (int i = 0; i < f_ij.length; i++) {
            for (int j = 0; j < f_ij[0].length; j++) {
                assertThat(f_ij[i][j]).isCloseTo(f_ijRef[i][j], within(400.0));
            }
        }
    }

    @Test
    void checkS_j() {
        double[] S_j = MI3622Calculation.calculateS_j();
        double[] S_jRef = new double[]{0.003};
        for (int j = 0; j < S_j.length; j++) {
            assertThat(S_j[j]).isCloseTo(S_jRef[j], within(0.001));
        }
    }

    @Test
    void checkS_0_j() {
        double[] S_0_j = MI3622Calculation.calculateS_0j();
        double[] S_0_jRef = new double[]{0.003};
        for (int j = 0; j < S_0_j.length; j++) {
            assertThat(S_0_j[j]).isCloseTo(S_0_jRef[j], within(0.001));
        }
    }

    @Test
    void checkEps_j() {
        double[] eps_j = MI3622Calculation.calculateEps_j();
        double[] eps_jRef = new double[]{0.008};
        for (int j = 0; j < eps_j.length; j++) {
            assertThat(eps_j[j]).isCloseTo(eps_jRef[j], within(0.002));
        }
    }

    @Test
    void checkDelta_j() {
        double[] delta_j = MI3622Calculation.calculateDelta_j();
        double[] delta_jRef = new double[]{0.12446};
        for (int j = 0; j < delta_j.length; j++) {
            assertThat(delta_j[j]).isCloseTo(delta_jRef[j], within(0.1));
        }
    }

    double[][] transpose(double[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        double[][] transposeMatrix = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                transposeMatrix[i][j] = matrix[j][i];
            }
        }
        return transposeMatrix;
    }
}
