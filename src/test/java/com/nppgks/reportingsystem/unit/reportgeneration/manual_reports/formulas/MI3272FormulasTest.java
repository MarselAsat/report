package com.nppgks.reportingsystem.unit.reportgeneration.manual_reports.formulas;

import com.nppgks.reportingsystem.reportgeneration.manual_reports.formulas.MI3272Formulas;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

public class MI3272FormulasTest {

    @Test
    void calculateV_KP_pr_ij_Formula4() {
        double V_KP_0 = 0.1;
        double alpha_cyl_t = 0.00001;
        double[][] t_KP_ij = new double[][]{{15, 15, 15}, {15, 15, 15}};
        double alpha_st_t = 0.0000015;
        double[][] t_st_ij = new double[][]{{15, 15, 15}, {15, 15, 15}};
        double D = 500;
        double E = 200000;
        double s = 30;
        double[][] P_KP_ij = new double[][]{{1, 1, 1}, {1, 1, 1}};
        double[][] actual = MI3272Formulas.calculateV_KP_pr_ij_Formula4(V_KP_0,
                alpha_cyl_t, t_KP_ij, alpha_st_t,
                t_st_ij, D, E, s, P_KP_ij);
        double[][] expected = new double[][]{{0.1, 0.1, 0.1}, {0.1, 0.1, 0.1}};

        for (int i = 0; i < actual.length; i++) {
            for (int j = 0; j < actual[0].length; j++) {
                assertThat(actual[i][j]).isCloseTo(expected[i][j], within(0.005));
            }
        }
    }

    @Test
    void calculateK_TPR_ij() {
        double[][] N_TPR_ij_avg = new double[][]{{15, 15, 15}, {15, 15, 15}};
        double[][] V_KP_pr_ij = new double[][]{{1, 1, 1}, {1, 1, 1}};
        double[][] actual = MI3272Formulas.calculateK_TPR_ij(N_TPR_ij_avg, V_KP_pr_ij);
        double[][] expected = new double[][]{{15, 15, 15}, {15, 15, 15}};

        for (int i = 0; i < actual.length; i++) {
            for (int j = 0; j < actual[0].length; j++) {
                assertThat(actual[i][j]).isCloseTo(expected[i][j], within(0.005));
            }
        }
    }

    @Test
    void calculateV_KP_pr_ij_Formula7() {
        double V_KP_0 = 0.1;
        double alpha_cyl_t = 0.00001;
        double[][] t_KP_ij_avg = new double[][]{{15, 15, 15}, {15, 15, 15}};
        double D = 500;
        double E = 200000;
        double s = 30;
        double[][] P_KP_ij_avg = new double[][]{{1, 1, 1}, {1, 1, 1}};
        double[][] beta_fluid_ij = new double[][]{{0.0001, 0.0001, 0.0001}, {0.0001, 0.0001, 0.0001}};
        double[][] t_TPR_ij_avg = new double[][]{{15, 15, 15}, {15, 15, 15}};
        double[][] P_TPR_ij_avg = new double[][]{{2.5, 2.5, 2.5}, {2.5, 2.5, 2.5}};
        double[][] gamma_fluid_ij = new double[][]{{0.0001, 0.0001, 0.0001}, {0.0001, 0.0001, 0.0001}};
        double[][] actual = MI3272Formulas.calculateV_KP_pr_ij_Formula7(V_KP_0,
                alpha_cyl_t, t_KP_ij_avg, D, E, s, P_KP_ij_avg, beta_fluid_ij, t_TPR_ij_avg,
                P_TPR_ij_avg, gamma_fluid_ij);
        double[][] expected = new double[][]{{0.1, 0.1, 0.1}, {0.1, 0.1, 0.1}};

        for (int i = 0; i < actual.length; i++) {
            for (int j = 0; j < actual[0].length; j++) {
                assertThat(actual[i][j]).isCloseTo(expected[i][j], within(0.005));
            }
        }
    }

    @Test
    void calculateAlpha_cyl_t() {
        Double alpha_cyl_t = null;
        Double alpha_cyl_t_sq = 0.0002;
        double actual = MI3272Formulas.calculateAlpha_cyl_t(alpha_cyl_t, alpha_cyl_t_sq);
        double expected = 0.0001;

        assertThat(actual).isCloseTo(expected, within(0.005));

        Double alpha_cyl_t2 = 0.0001;
        Double alpha_cyl_t_sq2 = null;
        double actual2 = MI3272Formulas.calculateAlpha_cyl_t(alpha_cyl_t2, alpha_cyl_t_sq2);
        double expected2 = 0.0001;

        assertThat(actual2).isCloseTo(expected2, within(0.005));
    }

    @Test
    void calculatePi_j() {
        double[][] K_TPR_ij = new double[][]{{400.1, 400.2, 400}, {400.2, 400, 400.1}};
        double[] actual = MI3272Formulas.calculatePi_j(K_TPR_ij);
        double[] expected = new double[]{0.025, 0.05, 0.025};

        for (int i = 0; i < actual.length; i++) {
            assertThat(actual[i]).isCloseTo(expected[i], within(0.005));
        }
    }

    @Test
    void calculateK_TPR_j() {
        double[][] K_TPR_ij = new double[][]{{400.1, 400.2, 400}, {400.2, 400, 400.1}};
        double[] actual = MI3272Formulas.calculateK_TPR_j(K_TPR_ij);
        double[] expected = new double[]{400.15, 400.1, 400.05};

        for (int i = 0; i < actual.length; i++) {
            assertThat(actual[i]).isCloseTo(expected[i], within(0.005));
        }
    }

    @Test
    void calculateDelta_K_j() {
        double[] K_TPR_j = new double[]{400.15, 400.1, 400.05};
        double[] K_2TPR_j = new double[]{400.1, 400.15, 400.051};
        double[] actual = MI3272Formulas.calculateDelta_K_j(K_TPR_j, K_2TPR_j);
        double[] expected = new double[]{-0.0125, 0.0125, 0.0025};

        for (int i = 0; i < actual.length; i++) {
            assertThat(actual[i]).isCloseTo(expected[i], within(0.005));
        }
    }

    @Test
    void calculateV_TPR_ij() {
        double[][] N_TPR_ij = new double[][]{{15, 15, 15}, {15, 15, 15}};
        double[] K_TPR_j = new double[]{400.15, 400.1, 400.05};
        double[][] actual = MI3272Formulas.calculateV_TPR_ij(N_TPR_ij, K_TPR_j);
        double[][] expected = new double[][]{{0.0375, 0.0375, 0.0375}, {0.0375, 0.0375, 0.0375}};

        for (int i = 0; i < actual.length; i++) {
            for (int j = 0; j < actual[0].length; j++) {
                assertThat(actual[i][j]).isCloseTo(expected[i][j], within(0.005));
            }
        }
    }

    @Test
    void calculateM_re_ij() {
        double[][] V_ij = new double[][]{{26, 26, 26}, {26, 26, 26}};
        double[][] ro_PP_ij = new double[][]{{800, 800, 800}, {800, 800, 800}};
        double[][] actual = MI3272Formulas.calculateM_re_ij(V_ij, ro_PP_ij);
        double[][] expected = new double[][]{{20.8, 20.8, 20.8}, {20.8, 20.8, 20.8}};

        for (int i = 0; i < actual.length; i++) {
            for (int j = 0; j < actual[0].length; j++) {
                assertThat(actual[i][j]).isCloseTo(expected[i][j], within(0.005));
            }
        }
    }

    @Test
    void calculateRo_PP_pr_ij() {
        double[][] ro_BIK_ij = new double[][]{{800, 800, 800}, {800, 800, 800}};
        double[][] t_PP_ij = new double[][]{{14, 14, 14}, {14, 14, 14}};
        double[][] t_ij = new double[][]{{13, 13, 13}, {13, 13, 13}};
        double[][] beta_fluid_ij = new double[][]{{1, 1, 1}, {1, 1, 1}};
        double[][] gamma_fluid_ij = new double[][]{{1, 1, 1}, {1, 1, 1}};
        double[][] P_ij = new double[][]{{2.5, 2.5, 2.5}, {2.5, 2.5, 2.5}};
        double[][] P_PP_ij = new double[][]{{2.5, 2.5, 2.5}, {2.5, 2.5, 2.5}};
        double[][] actual = MI3272Formulas.calculateRho_PP_pr_ij(ro_BIK_ij, t_PP_ij, t_ij,
                beta_fluid_ij, gamma_fluid_ij, P_ij, P_PP_ij);
        double[][] expected = new double[][]{{1600, 1600, 1600}, {1600, 1600, 1600}};

        for (int i = 0; i < actual.length; i++) {
            for (int j = 0; j < actual[0].length; j++) {
                assertThat(actual[i][j]).isCloseTo(expected[i][j], within(0.005));
            }
        }
    }

    @Test
    void calculateM_mas_ij() {
        double[][] N_mas_ij = new double[][]{{1200000, 1200000, 1200000}, {1200000, 1200000, 1200000}};
        double KF_conf = 65000;
        double[][] actual = MI3272Formulas.calculateM_mas_ij(N_mas_ij, KF_conf);
        double[][] expected = new double[][]{{18.46, 18.46, 18.46}, {18.46, 18.46, 18.46}};

        for (int i = 0; i < actual.length; i++) {
            for (int j = 0; j < actual[0].length; j++) {
                assertThat(actual[i][j]).isCloseTo(expected[i][j], within(0.005));
            }
        }
    }

    @Test
    void calculateMF_ij() {
        double[][] M_re_ij = new double[][]{{20.8, 20.8, 20.8}, {20.8, 20.8, 20.8}};
        double[][] M_mas_ij = new double[][]{{18.46, 18.46, 18.46}, {18.46, 18.46, 18.46}};
        double MF_set_range = 1;
        double[][] actual = MI3272Formulas.calculateMF_ij(M_re_ij, M_mas_ij, MF_set_range);
        double[][] expected = new double[][]{{1.127, 1.127, 1.127}, {1.127, 1.127, 1.127}};

        for (int i = 0; i < actual.length; i++) {
            for (int j = 0; j < actual[0].length; j++) {
                assertThat(actual[i][j]).isCloseTo(expected[i][j], within(0.005));
            }
        }
    }

    @Test
    void calculateMF_j_avg() {
        double[][] MF_ij = new double[][]{{1.0, 1.0, 1.0}, {1.0, 0.999, 0.9}};
        double[] actual = MI3272Formulas.calculateMF_j_avg(MF_ij);
        double[] expected = new double[]{1.0, 0.9995, 0.95};

        for (int i = 0; i < actual.length; i++) {
            assertThat(actual[i]).isCloseTo(expected[i], within(0.005));
        }
    }

    @Test
    void calculateS_MF_range() {
        double[][] MF_ij = new double[][]{{1.0, 1.0, 1.0}, {1.0, 0.999, 0.9}};
        double[] MF_j_avg = new double[]{1.0, 0.9995, 0.95};
        double actual = MI3272Formulas.calculateS_MF_range(MF_ij, MF_j_avg);
        double expected = 3.33;

        assertThat(actual).isCloseTo(expected, within(0.005));
    }

    @Test
    void calculateMF_range() {
        double[] MF_j_avg = new double[]{1.0, 0.9995, 0.95};
        double actual = MI3272Formulas.calculateMF_range(MF_j_avg);
        double expected = 0.98;

        assertThat(actual).isCloseTo(expected, within(0.005));
    }

    @Test
    void calculateK_gr() {
        double K_PEP_gr = 1;
        double MF_range = 0.9;
        double actual = MI3272Formulas.calculateK_gr(K_PEP_gr, MF_range);
        double expected = 0.9;

        assertThat(actual).isCloseTo(expected, within(0.005));
    }

    @Test
    void calculateKF_ij() {
        double[][] M_re_ij = new double[][]{{20.8, 20.8, 20.8}, {20.8, 20.8, 20.8}};
        double[][] M_mas_ij = new double[][]{{18.46, 18.46, 18.46}, {18.46, 18.46, 18.46}};
        double[][] actual = MI3272Formulas.calculateKF_ij(M_mas_ij, M_re_ij);
        double[][] expected = new double[][]{{0.887, 0.887, 0.887}, {0.887, 0.887, 0.887}};

        for (int i = 0; i < actual.length; i++) {
            for (int j = 0; j < actual[0].length; j++) {
                assertThat(actual[i][j]).isCloseTo(expected[i][j], within(0.005));
            }
        }
    }

    @Test
    void calculateKF_j_avg() {
        double[][] KF_ij = new double[][]{{1.0, 1.0, 1.0}, {1.0, 0.999, 0.9}};
        double[] actual = MI3272Formulas.calculateKF_j_avg(KF_ij);
        double[] expected = new double[]{1.0, 0.9995, 0.95};

        for (int i = 0; i < actual.length; i++) {
            assertThat(actual[i]).isCloseTo(expected[i], within(0.005));
        }
    }

    @Test
    void calculateS_KF_range() {
        double[][] KF_ij = new double[][]{{1.0, 1.0, 1.0}, {1.0, 0.999, 0.9}};
        double[] KF_j_avg = new double[]{1.0, 0.9995, 0.95};
        double actual = MI3272Formulas.calculateS_KF_range(KF_ij, KF_j_avg);
        double expected = 3.039;

        assertThat(actual).isCloseTo(expected, within(0.005));
    }

    @Test
    void calculateS_KF_k() {
        double[][] KF_ij = new double[][]{{1.0, 1.0, 1.0}, {1.0, 0.999, 0.9}};
        double[] KF_j_avg = new double[]{1.0, 0.9995, 0.95};
        double[] actual = MI3272Formulas.calculateS_KF_k(KF_ij, KF_j_avg);
        double[] expected = new double[]{0.035, 3.722};

        for (int i = 0; i < actual.length; i++) {
            assertThat(actual[i]).isCloseTo(expected[i], within(0.005));
        }
    }

    @Test
    void calculateKF_range() {
        double[] KF_j_avg = new double[]{1.0, 0.9995, 0.95};
        double actual = MI3272Formulas.calculateKF_range(KF_j_avg);
        double expected = 0.98;

        assertThat(actual).isCloseTo(expected, within(0.005));
    }

    @Test
    void calculateEpsilon_PEP() {
        double t_P_n = 2;
        double S_MF_range = 3;
        double actual = MI3272Formulas.calculateEpsilon_PEP(t_P_n, S_MF_range);
        double expected = 6;

        assertThat(actual).isCloseTo(expected, within(0.005));
    }

    @Test
    void calculateTheta_sigma_PEP() {
        double delta_KP = 0.05;
        double delta_PP = 0.03;
        double theta_t = 0.03;
        double delta_UOI_K = 0.001;
        double theta_MF_range = 3;
        double delta_mas_0 = 0.02;
        double actual = MI3272Formulas.calculateTheta_sigma_PEP(delta_KP, delta_PP,
                theta_t, delta_UOI_K, theta_MF_range, delta_mas_0);
        double expected = 3.3;

        assertThat(actual).isCloseTo(expected, within(0.005));
    }

    @Test
    void calculateDelta_mas_0() {
        double ZS = 0.07;
        double[] Q_j_avg = new double[]{200, 300, 400};
        double actual = MI3272Formulas.calculateDelta_mas_0(ZS, Q_j_avg);
        double expected = 0.023;

        assertThat(actual).isCloseTo(expected, within(0.005));
    }

    @Test
    void calculateQ_j_avg() {
        double[][] Q_ij = new double[][]{{100, 150, 120}, {350, 300, 320}};
        double[] actual = MI3272Formulas.calculateQ_j_avg(Q_ij);
        double[] expected = new double[]{225, 225, 220};

        for (int i = 0; i < actual.length; i++) {
            assertThat(actual[i]).isCloseTo(expected[i], within(0.005));
        }
    }

    @Test
    void calculateTheta_MForKF_range() {
        double[] MForK_j_avg = new double[]{1.0, 0.9995, 0.95};
        double MForK_range = 0.98;
        double actual = MI3272Formulas.calculateTheta_MForKF_range(MForK_j_avg, MForK_range);
        double expected = 3.06;
        assertThat(actual).isCloseTo(expected, within(0.005));
    }

    @Test
    void calculateTheta_t() {
        double delta_t_KP = 0.2;
        double delta_t_PP = 0.2;
        String operatingFluid = "нефть";
        double[][] ro_PP_ij = new double[][]{{800, 800, 800}, {800, 800, 800}};
        double[][] t_PP_ij = new double[][]{{14, 14, 14}, {14, 14, 14}};
        double actual = MI3272Formulas.calculateTheta_t(delta_t_KP, delta_t_PP, new double[][]{{1, 2, 3}, {4, 5, 6}});
        double expected = 0.027;
        assertThat(actual).isCloseTo(expected, within(0.005));
    }

    @Test
    void calculateDelta_PEP() {
        double Z_P = 0.8;
        double theta_sigma = 3;
        double epsilon = 6;
        double S_MF_range = 3;
        double actual = MI3272Formulas.calculateDelta_PEP(Z_P, theta_sigma, epsilon, S_MF_range);
        double expected = 7.2;
        assertThat(actual).isCloseTo(expected, within(0.005));
    }

    @Test
    void calculateEpsilon_SOI_const() {
        double t_P_n = 2;
        double S_KF_range = 3;
        double actual = MI3272Formulas.calculateEpsilon_SOI_const(t_P_n, S_KF_range);
        double expected = 6;
        assertThat(actual).isCloseTo(expected, within(0.005));
    }

    @Test
    void calculateTheta_sigma_SOI_const() {
        double delta_KP = 0.05;
        double delta_PP = 0.03;
        double theta_t = 0.03;
        double delta_UOI_K = 0.001;
        double theta_KF_range = 3;
        double delta_mas_0 = 0.023;
        double actual = MI3272Formulas.calculateTheta_sigma_SOI_const(delta_KP,  delta_PP,  theta_t, delta_UOI_K,  theta_KF_range,  delta_mas_0);
        double expected = 3.3;
        assertThat(actual).isCloseTo(expected, within(0.005));
    }

    @Test
    void calculateDelta_SOI_const() {
        double Z_P = 0.8;
        double theta_sigma = 0.9;
        double epsilon = 0.01;
        double S_KF_range = 0.97;
        double actual = MI3272Formulas.calculateDelta_SOI_const(Z_P, theta_sigma, epsilon, S_KF_range);
        double expected = 0.73;
        assertThat(actual).isCloseTo(expected, within(0.005));
    }

    @Test
    void calculateEpsilon_k() {
        double t_P_n = 2;
        double[] S_KF_k = new double[]{0.006, 0.006, 0.0163};
        double[] actual = MI3272Formulas.calculateEpsilon_k(t_P_n, S_KF_k);
        double[] expected = new double[]{0.012, 0.012, 0.0326};

        for (int i = 0; i < actual.length; i++) {
            assertThat(actual[i]).isCloseTo(expected[i], within(0.005));
        }
    }

    @Test
    void calculateTheta_sigma_k() {
        double delta_KP = 0.05;
        double delta_PP = 0.03;
        double theta_t = 0.03;
        double delta_UOI_K = 0.001;
        double[] theta_KF_k = new double[]{0, 0.003, 0.008};
        double[] delta_mas_0k = new double[]{0, 0.003, 0.008};
        double[] actual = MI3272Formulas.calculateTheta_sigma_k(delta_KP,  delta_PP,  theta_t,
                delta_UOI_K, theta_KF_k, delta_mas_0k);
        double[] expected = new double[]{0.072, 0.072, 0.073};

        for (int i = 0; i < actual.length; i++) {
            assertThat(actual[i]).isCloseTo(expected[i], within(0.005));
        }
    }

    @Test
    void calculateDelta_mas_0k() {
        double ZS = 0.07;
        double[] Q_kmin = new double[]{200, 300, 400};
        double[] Q_kmax = new double[]{300, 400, 500};
        double[] actual = MI3272Formulas.calculateDelta_mas_0k(ZS, Q_kmin, Q_kmax);
        double[] expected = new double[]{0.03, 0.02, 0.015};

        for (int i = 0; i < actual.length; i++) {
            assertThat(actual[i]).isCloseTo(expected[i], within(0.005));
        }
    }

    @Test
    void calculateQ_kmin() {
        double[] Q_j_avg = new double[]{100, 200, 300};
        double[] actual = MI3272Formulas.calculateQ_kmin(Q_j_avg);
        double[] expected = new double[]{100, 200};

        for (int i = 0; i < actual.length; i++) {
            assertThat(actual[i]).isCloseTo(expected[i], within(0.005));
        }
    }

    @Test
    void calculateQ_kmax() {
        double[] Q_j_avg = new double[]{100, 200, 300};
        double[] actual = MI3272Formulas.calculateQ_kmax(Q_j_avg);
        double[] expected = new double[]{200, 300};

        for (int i = 0; i < actual.length; i++) {
            assertThat(actual[i]).isCloseTo(expected[i], within(0.005));
        }
    }

    @Test
    void calculateTheta_KF_k() {
        double[] KF_j_avg = new double[]{1, 2, 3};
        double[] actual = MI3272Formulas.calculateTheta_KF_k(KF_j_avg);
        double[] expected = new double[]{10, 16.666};

        for (int i = 0; i < actual.length; i++) {
            assertThat(actual[i]).isCloseTo(expected[i], within(0.005));
        }
    }

    @Test
    void calculateDelta_k() {
        double[] Z_P_k = new double[]{-1, -1, 0.8};
        double[] theta_sigma_k = new double[]{0.07, 0.07, 0.088};
        double[] epsilon_k = new double[]{0.012, 0.012, 0.034};
        double[] S_KF_k = new double[]{0.006, 0.006, 0.016};
        double[] actual = MI3272Formulas.calculateDelta_k(Z_P_k, theta_sigma_k, epsilon_k, S_KF_k);
        double[] expected = new double[]{0.07, 0.07, 0.0976};

        for (int i = 0; i < actual.length; i++) {
            assertThat(actual[i]).isCloseTo(expected[i], within(0.005));
        }
    }

    @Test
    void calculateZ_P_k() {
        double[] theta_sigma_k = new double[]{0.07, 0.07, 0.088};
        double[] S_KF_k = new double[]{0.006, 0.006, 0.016};
        double[] actual = MI3272Formulas.calculateZ_P_k(theta_sigma_k, S_KF_k);
        double[] expected = new double[]{-1, -1, 0.79};

        for (int i = 0; i < actual.length; i++) {
            assertThat(actual[i]).isCloseTo(expected[i], within(0.005));
        }
    }

    @Test
    void checkDelta_k_j() {
        double[] delta_k_j = new double[]{0.01, -0.011, 0.012};
        boolean actual = MI3272Formulas.checkDelta_k_j(delta_k_j);
        boolean expected = true;
        assertThat(actual).isEqualTo(expected);

        double[] delta_k_j2 = new double[]{0.01, -0.021, 0.012};
        boolean actual2 = MI3272Formulas.checkDelta_k_j(delta_k_j2);
        boolean expected2 = false;
        assertThat(actual2).isEqualTo(expected2);
    }

    @Test
    void checkDeltaAsControl() {
        double delta = -0.1;
        boolean actual = MI3272Formulas.checkDeltaAsControl(delta);
        boolean expected = true;
        assertThat(actual).isEqualTo(expected);

        double delta2 = 0.3;
        boolean actual2 = MI3272Formulas.checkDeltaAsControl(delta2);
        boolean expected2 = false;
        assertThat(actual2).isEqualTo(expected2);
    }

    @Test
    void checkDeltaAsOperating() {
        double delta = -0.1;
        boolean actual = MI3272Formulas.checkDeltaAsOperating(delta);
        boolean expected = true;
        assertThat(actual).isEqualTo(expected);

        double delta2 = 0.26;
        boolean actual2 = MI3272Formulas.checkDeltaAsOperating(delta2);
        boolean expected2 = false;
        assertThat(actual2).isEqualTo(expected2);
    }

    @Test
    void checkDelta_kAsControl() {
        double[] delta_k_j = new double[]{0.1, -0.11, 0.12};
        boolean actual = MI3272Formulas.checkDelta_kAsControl(delta_k_j);
        boolean expected = true;
        assertThat(actual).isEqualTo(expected);

        double[] delta_k_j2 = new double[]{0.1, -0.21, 0.12};
        boolean actual2 = MI3272Formulas.checkDelta_kAsControl(delta_k_j2);
        boolean expected2 = false;
        assertThat(actual2).isEqualTo(expected2);
    }

    @Test
    void checkDelta_kAsOperating() {
        double[] delta_k_j = new double[]{0.1, -0.11, 0.12};
        boolean actual = MI3272Formulas.checkDelta_kAsOperating(delta_k_j);
        boolean expected = true;
        assertThat(actual).isEqualTo(expected);

        double[] delta_k_j2 = new double[]{0.1, -0.26, 0.12};
        boolean actual2 = MI3272Formulas.checkDelta_kAsOperating(delta_k_j2);
        boolean expected2 = false;
        assertThat(actual2).isEqualTo(expected2);
    }
}
