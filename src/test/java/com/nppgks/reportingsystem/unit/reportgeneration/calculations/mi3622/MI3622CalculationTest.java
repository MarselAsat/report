package com.nppgks.reportingsystem.unit.reportgeneration.calculations.mi3622;

import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3622.calculations.MI3622InitialData;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3622.calculations.MI3622Calculation;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MI3622CalculationTest {

    MI3622Calculation MI3622Calculation;

    @BeforeAll
    void prepareData(){
        MI3622InitialData MI3622InitialData = new MI3622InitialData();
        double[][] Q_ij = new double[][]{
                {87.5, 87.5, 87.5, 87.5, 87.6},
                {65.7, 64.4, 65.7, 65.5, 65.5},
                {43.9, 43.7, 43.7, 43.7, 43.7},
                {21.9, 21.9, 21.9, 21.9, 21.9},
                {11.1, 11.1, 11.1, 11.1, 11.1}
        };
        Q_ij = transpose(Q_ij);
        double[][] N_e_ij = new double[][]{
                {33002, 33013, 33013, 33012, 33012},
                {33007, 32999, 33007, 33007, 33007},
                {30262, 30262, 30261, 30256, 30262},
                {20626, 20623, 20626, 20625, 20623},
                {10501, 10501, 10499, 10500, 10500}
        };
        N_e_ij = transpose(N_e_ij);
        double[][] N_p_ij = new double[][]{
                {33002, 33012, 33012, 33013, 33013},
                {33007, 32998, 33007, 33007, 33007},
                {30262, 30262, 30261, 30257, 30261},
                {20625, 20623, 20626, 20625, 20624},
                {10501, 10501, 10499, 10501, 10500}
        };
        N_p_ij = transpose(N_p_ij);
        double[][] T_ij = new double[][]{
                {5.7, 5.3, 5.3, 5.3, 5.3},
                {6.9, 7.3, 7.3, 7.2, 7.3},
                {10, 10.3, 10.3, 10.3, 10.3},
                {14.4, 14.3, 14.3, 14.2, 14.3},
                {14.1, 14.3, 14.2, 14.3, 14.3}
        };
        T_ij = transpose(T_ij);
        double[][] M_ij = new double[][]{
                {145.756, 145.8, 145.8, 145.8040, 145.8040},
                {145.778, 145.738, 145.778, 145.778, 145.778},
                {133.654, 133.6540, 133.65, 133.6320, 133.65},
                {91.0918, 91.0829, 91.0962, 91.0918, 91.0974},
                {46.3784, 46.3784, 46.3696, 46.3784, 46.3740}
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
        MI3622InitialData.setMeasureCount(Q_ij.length);
        MI3622InitialData.setPointsCount(Q_ij[0].length);
        MI3622InitialData.setQ_ij(Q_ij);
        MI3622InitialData.setN_e_ij(N_e_ij);
        MI3622InitialData.setN_p_ij(N_p_ij);
        MI3622InitialData.setT_ij(T_ij);
        MI3622InitialData.setM_ij(M_ij);
        MI3622InitialData.setF_p_max(f_p_max);
        MI3622InitialData.setQ_p_max(Q_p_max);
        MI3622InitialData.setMF_p(MF_p);
        MI3622InitialData.setK_e_arr(K_e_arr);
        MI3622InitialData.setQ_e_arr(Q_e_arr);
        MI3622InitialData.setTheta_e(theta_e);
        MI3622InitialData.setTheta_N(theta_N);
        MI3622InitialData.setZS(ZS);
        MI3622InitialData.setZeroStabilityCorr(true);
        MI3622Calculation = new MI3622Calculation(MI3622InitialData);
    }

    @Test
    void checkMe_ij(){
        double[][] Me_ij = MI3622Calculation.calculateM_e_ij();
        double[][] Me_ijRef = new double[][]{
                {145.7560, 145.804, 145.804, 145.8, 145.8},
                {145.7780, 145.7420, 145.7780, 145.7780, 145.778},
                {133.654, 133.654, 133.65, 133.628, 133.654},
                {91.0962, 91.0829, 91.0962, 91.0918, 91.0829},
                {46.3784, 46.3784, 46.3696, 46.374, 46.374}
        };
        Me_ijRef = transpose(Me_ijRef);
        for(int i=0; i<Me_ij.length; i++){
            for(int j=0; j< Me_ij.length; j++){
                assertThat(Me_ij[i][j]).isCloseTo(Me_ijRef[i][j], within(0.005));
            }
        }
    }

    @Test
    void checkKpm(){
        double Kpm = MI3622Calculation.calculateK_pm();
        double KpmRef = 226.4151;
        assertThat(Kpm).isCloseTo(KpmRef, within(0.05));
    }

    @Test
    void checkMF_j(){
        double[] MF_j = MI3622Calculation.calculateMF_j();
        double[] MF_jRef = {1, 1.00001, 1, 1, 0.99998};
        for(int j=0; j<MF_j.length; j++){
            assertThat(MF_j[j]).isCloseTo(MF_jRef[j], within(0.0002));
        }
    }

    @Test
    void checkf_ij(){
        double[][] f_ij = MI3622Calculation.calculateF_ij();
        double[][] f_ijRef = new double[][]{
                {5500.334, 6602.4, 6602.4, 6603.6, 6602.6},
                {4715.286, 4714, 4715.286, 4715.286, 4715.286},
                {3026.2, 3026.2, 3026.1, 3025.7, 3026.1},
                {1473.214, 1473.071, 1473.286, 1473.214, 1473.143},
                {750.071, 750.071, 749.929, 750.071, 750}
        };
        f_ijRef = transpose(f_ijRef);
        for(int i=0; i<f_ij.length; i++){
            for(int j=0; j< f_ij.length; j++){
                assertThat(f_ij[i][j]).isCloseTo(f_ijRef[i][j], within(400.0));
            }
        }
    }

    @Test
    void checkS_j(){
        double[] S_j = MI3622Calculation.calculateS_j();
        double[] S_jRef = new double[]{0.003, 0.001, 0.002, 0.004, 0.004};
        for(int j=0; j<S_j.length; j++){
            assertThat(S_j[j]).isCloseTo(S_jRef[j], within(0.001));
        }
    }

    @Test
    void checkS_0_j(){
        double[] S_0_j = MI3622Calculation.calculateS_0j();
        double[] S_0_jRef = new double[]{0.001, 0.00, 0.001, 0.002, 0.002};
        for(int j=0; j< S_0_j.length; j++){
            assertThat(S_0_j[j]).isCloseTo(S_0_jRef[j], within(0.001) );
        }
    }

    @Test
    void checkEps_j(){
        double[] eps_j = MI3622Calculation.calculateEps_j();
        double[] eps_jRef = new double[]{0.003, 0.00, 0.003, 0.005, 0.005};
        for(int j=0; j< eps_j.length; j++){
            assertThat(eps_j[j]).isCloseTo(eps_jRef[j], within(0.002) );
        }
    }

    @Test
    void checkDelta_j(){
        double[] delta_j = MI3622Calculation.calculateDelta_j();
        double[] delta_jRef = new double[]{0.12446, 0.12446, 0.12446, 0.12446, 0.12446};
        for(int j=0; j< delta_j.length; j++){
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
