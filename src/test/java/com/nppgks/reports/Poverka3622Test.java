package com.nppgks.reports;

import com.nppgks.reports.service.poverka3622.InitialData;
import com.nppgks.reports.service.poverka3622.Poverka3622;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Poverka3622Test {

    Poverka3622 poverka3622;

    @BeforeAll
    void prepareData(){
        InitialData initialData = new InitialData();
        double[][] Q = new double[][]{
                {87.5, 87.5, 87.5, 87.5, 87.6},
                {65.7, 64.4, 65.7, 65.5, 65.5},
                {43.9, 43.7, 43.7, 43.7, 43.7},
                {21.9, 21.9, 21.9, 21.9, 21.9},
                {11.1, 11.1, 11.1, 11.1, 11.1}
        };
        Q = transpose(Q);
        double[][] N_e = new double[][]{
                {33002, 33013, 33013, 33012, 33012},
                {33007, 32999, 33007, 33007, 33007},
                {30262, 30262, 30261, 30256, 30262},
                {20626, 20623, 20626, 20625, 20623},
                {10501, 10501, 10499, 10500, 10500}
        };
        N_e = transpose(N_e);
        double[][] N_p = new double[][]{
                {33002, 33012, 33012, 33013, 33013},
                {33007, 32998, 33007, 33007, 33007},
                {30262, 30262, 30261, 30257, 30261},
                {20625, 20623, 20626, 20625, 20624},
                {10501, 10501, 10499, 10501, 10500}
        };
        N_p = transpose(N_p);
        double[][] T = new double[][]{
                {5.7, 5.3, 5.3, 5.3, 5.3},
                {6.9, 7.3, 7.3, 7.2, 7.3},
                {10, 10.3, 10.3, 10.3, 10.3},
                {14.4, 14.3, 14.3, 14.2, 14.3},
                {14.1, 14.3, 14.2, 14.3, 14.3}
        };
        T = transpose(T);
        double[][] M = new double[][]{
                {145.756, 145.8, 145.8, 145.8040, 145.8040},
                {145.778, 145.738, 145.778, 145.778, 145.778},
                {133.654, 133.6540, 133.65, 133.6320, 133.65},
                {91.0918, 91.0829, 91.0962, 91.0918, 91.0974},
                {46.3784, 46.3784, 46.3696, 46.3784, 46.3740}
        };
        M = transpose(M);

        double[][] K_e_ij = new double[][]{
                {226.4151, 226.4151, 226.4151, 226.4151, 226.4151},
                {226.4151, 226.4151, 226.4151, 226.4151, 226.4151},
                {226.4151, 226.4151, 226.4151, 226.4151, 226.4151},
                {226.4151, 226.4151, 226.4151, 226.4151, 226.4151},
                {226.4151, 226.4151, 226.4151, 226.4151, 226.4151}
        };
        double f_p_max = 10000;
        double Q_p_max = 159;
        double MF_prev = 1;
        initialData.setQ(Q);
        initialData.setN_e(N_e);
        initialData.setN_p(N_p);
        initialData.setT(T);
        initialData.setM(M);
        initialData.setF_p_max(f_p_max);
        initialData.setQ_p_max(Q_p_max);
        initialData.setMF_prev(MF_prev);
        initialData.setK_e(K_e_ij);
        poverka3622 = new Poverka3622(initialData);
    }

    @Test
    void checkMe_ijIsCorrect(){
        double[][] Me_ij = poverka3622.calculateM_e_ij();
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
    void checkKnmIsCorrect(){
        double Kpm = poverka3622.calculateKpm();
        double KpmRef = 226415.1;
        assertThat(Kpm).isCloseTo(KpmRef, within(0.05));
    }

    @Test
    void checkMF_jIsCorrect(){
        double[] MF_j = poverka3622.calculateMF_j(null);
        double[] MF_jRef = {1, 1.00001, 1, 1, 0.99998};
        for(int j=0; j<MF_j.length; j++){
            assertThat(MF_j[j]).isCloseTo(MF_jRef[j], within(0.0002));
        }
    }

    @Test
    void checkf_ijIsCorrect(){
        double[][] f_ij = poverka3622.calculatef_ij();
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
    void checkS_jIsCorrect(){
        double[] S_j = poverka3622.calculateS_j();
        double[] S_jRef = new double[]{0.003, 0.001, 0.002, 0.004, 0.004};
        for(int j=0; j<S_j.length; j++){
            assertThat(S_j[j]).isCloseTo(S_jRef[j], within(0.001));
        }
    }

    @Test
    void checkS_0_jIsCorrect(){
        double[] S_0_j = poverka3622.calculateS_0j();
        double[] S_0_jRef = new double[]{0.001, 0.00, 0.001, 0.002, 0.002};
        for(int j=0; j< S_0_j.length; j++){
            assertThat(S_0_j[j]).isCloseTo(S_0_jRef[j], within(0.001) );
        }
    }

    @Test
    void checkEps_jIsCorrect(){
        double[] eps_j = poverka3622.calculateEps_j(null);
        double[] eps_jRef = new double[]{0.003, 0.00, 0.003, 0.005, 0.005};
        for(int j=0; j< eps_j.length; j++){
            assertThat(eps_j[j]).isCloseTo(eps_jRef[j], within(0.002) );
        }
    }

    @Test
    void checkDelta_j(){
        double[] delta_j = poverka3622.calculateDelta_j(0.041, 0.11, 0, 0, 0.02, false);
        double[] delta_jRef = new double[]{0.12446, 0.12446, 0.12446, 0.12446, 0.12446};
        for(int j=0; j< delta_j.length; j++){
            assertThat(delta_j[j]).isCloseTo(delta_jRef[j], within(0.1));
        }
    }

    double[][] transpose(double[][] matrix){
        int length = matrix.length;
        double[][] transposeMatrix = new double[length][length];
        for(int i=0; i<length; i++){
            for(int j=0; j< length; j++){
                transposeMatrix[i][j] = matrix[j][i];
            }
        }
        return transposeMatrix;
    }

}
