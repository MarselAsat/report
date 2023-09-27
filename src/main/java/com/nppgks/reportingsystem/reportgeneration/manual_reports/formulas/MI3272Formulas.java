package com.nppgks.reportingsystem.reportgeneration.manual_reports.formulas;

import com.nppgks.reportingsystem.exception.MissingOpcTagException;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.CommonFunctions;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3272.calculations.Appendix;

import java.util.Arrays;

public class MI3272Formulas {

    // формула 4
    public static double[][] calculateV_KP_pr_ij_Formula4(double V_KP_0, double alpha_cyl_t,
                                                          double[][] t_KP_ij, double alpha_st_t,
                                                          double[][] t_st_ij, double D, double E,
                                                          double s, double[][] P_KP_ij) {
        int measureCount = t_KP_ij.length;
        int pointsCount = t_KP_ij[0].length;
        double[][] V_KP_pr_ij = new double[measureCount][pointsCount];
        for (int i = 0; i < measureCount; i++) {
            for (int j = 0; j < pointsCount; j++) {
                V_KP_pr_ij[i][j] = V_KP_0
                        * (1 + 2 * alpha_cyl_t * (t_KP_ij[i][j] - 20) + alpha_st_t * (t_st_ij[i][j] - 20))
                        * (1 + 0.95 * D / (E * s) * P_KP_ij[i][j]);
            }
        }
        return V_KP_pr_ij;
    }

    // формула 6
    public static double[][] calculateK_TPR_ij(double[][] N_TPR_ij_avg, double[][] V_KP_pr_ij) {
        return CommonFunctions.getDivisionOfTwoArrays(N_TPR_ij_avg, V_KP_pr_ij);
    }

    // формула 7
    public static double[][] calculateV_KP_pr_ij_Formula7(double V_KP_0, double alpha_cyl_t, double[][] t_KP_ij_avg,
                                                   double D, double E, double s, double[][] P_KP_ij_avg,
                                                   double[][] beta_fluid_ij, double[][] t_TPR_ij_avg,
                                                   double[][] P_TPR_ij_avg, double[][] gamma_fluid_ij) {
        int measureCount = t_KP_ij_avg.length;
        int pointsCount = t_KP_ij_avg[0].length;
        double[][] V_KP_pr_ij = new double[measureCount][pointsCount];
        for (int i = 0; i < measureCount; i++) {
            for (int j = 0; j < pointsCount; j++) {
                V_KP_pr_ij[i][j] = V_KP_0 * (1 + 2 * alpha_cyl_t * (t_KP_ij_avg[i][j] - 20))
                        * (1 + (0.95 * D) / (E * s) * P_KP_ij_avg[i][j])
                        * (1 + beta_fluid_ij[i][j] * (t_TPR_ij_avg[i][j] - t_KP_ij_avg[i][j]))
                        * (1 - gamma_fluid_ij[i][j] * (P_TPR_ij_avg[i][j] - P_KP_ij_avg[i][j]));

            }
        }
        return V_KP_pr_ij;
    }

    public static double calculateAlpha_cyl_t(Double alpha_cyl_t, Double alpha_cyl_t_sq) {
        if (alpha_cyl_t != null) {
            return alpha_cyl_t;
        } else if (alpha_cyl_t_sq != null) {
            return 0.5 * alpha_cyl_t_sq;
        } else {
            throw new MissingOpcTagException("В качестве входного параметра поверки необходимо значение тега alpha_cyl_t, либо alpha_cyl_t_sq, которое будет использоваться для расчета alpha_cyl_t");
        }
    }

    // формула 8
    public static double[] calculatePi_j(double[][] K_TPR_ij) {
        int pointsCount = K_TPR_ij[0].length;
        double[] Pi_j = new double[pointsCount];
        double[] K_TPR_j_max = new double[pointsCount];
        double[] K_TPR_j_min = new double[pointsCount];
        for (int j = 0; j < pointsCount; j++) {
            double max = K_TPR_ij[0][j];
            double min = K_TPR_ij[0][j];
            for (double[] doubles : K_TPR_ij) {
                if (doubles[j] > max) {
                    max = doubles[j];
                }
                if (doubles[j] < min) {
                    min = doubles[j];
                }
            }
            K_TPR_j_max[j] = max;
            K_TPR_j_min[j] = min;
        }
        for (int j = 0; j < pointsCount; j++) {
            Pi_j[j] = (K_TPR_j_max[j] - K_TPR_j_min[j]) / K_TPR_j_min[j] * 100;
        }
        return Pi_j;
    }

    // формула 9
    public static double[] calculateK_TPR_j(double[][] K_TPR_ij) {
        return CommonFunctions.getAverageForEachColumn(K_TPR_ij);
    }

    // формула 10
    public static double[] calculateDelta_K_j(double[] K_TPR_j, double[] K_2TPR_j) {
        int pointsCount = K_TPR_j.length;
        double[] delta_K_j = new double[pointsCount];
        for (int j = 0; j < pointsCount; j++) {
            delta_K_j[j] = (K_2TPR_j[j] - K_TPR_j[j]) / (K_TPR_j[j]) * 100;
        }
        return delta_K_j;
    }

    // пункт 8.3.2.10
    public static double[][] calculateV_TPR_ij(double[][] N_TPR_ij, double[] K_TPR_j) {
        int measureCount = N_TPR_ij.length;
        int pointsCount = N_TPR_ij[0].length;
        double[][] V_TPR_ij = new double[measureCount][pointsCount];
        for (int i = 0; i < measureCount; i++) {
            for (int j = 0; j < pointsCount; j++) {
                V_TPR_ij[i][j] = N_TPR_ij[i][j] / K_TPR_j[j];
            }
        }
        return V_TPR_ij;
    }

    // формулы 11а, 11б, 12а, 12б
    public static double[][] calculateM_re_ij(double[][] V_ij, double[][] ro_PP_ij) {
        /*
        Если импользуется ТПР, то в качестве аргумента в этот метод передается V_ij = V_TPR_ij - объем рабочей жидкости,
        а если ТПР не используется, то в виде аргумента V_ij передается V_KP_pr_ij - вместимость калиброванного участка компакт-прувера

        Если применятеся поточный ПП, установленный в БИК (в составе СИКН) и ТПР по любому из вариантов пункта 4.2.3,
        то в виде аргумента ro_PP_ij передается ro_PP_pr_ij - плотность рабочей жидкости, измеренная поточным ПП
        и приведенная к рабочим условиям в ТПР.
        Если применяется поточный ПП и ТПР, входящие в состав компакт-прувера,
        то в виде аргумента ro_PP_ij передается ro_PP_ij - плотность рабочей жидкости, измеренная поточным ПП, установленном на компакт-прувер
        */
        int measureCount = V_ij.length;
        int pointsCount = V_ij[0].length;
        double[][] M_re_ij = new double[measureCount][pointsCount];
        for (int i = 0; i < measureCount; i++) {
            for (int j = 0; j < pointsCount; j++) {
                M_re_ij[i][j] = V_ij[i][j] * ro_PP_ij[i][j] * 0.001;
            }
        }
        return M_re_ij;
    }

    // формулы 13а, 13б
    public static double[][] calculateRo_PP_pr_ij(double[][] ro_BIK_ij, double[][] t_PP_ij,
                                            double[][] t_ij, double[][] beta_fluid_ij,
                                            double[][] gamma_fluid_ij, double[][] P_ij,
                                            double[][] P_PP_ij) {
        /*
        Здесь в качестве аргмуентов P_ij и t_ij могут быть переданы
        либо P_TPR_ij и t_TPR_ij (используется ТПР) либо P_KP_ij и t_KP_ij (не используется ТПР)
        */
        int measureCount = ro_BIK_ij.length;
        int pointsCount = ro_BIK_ij[0].length;
        double[][] ro_PP_pr_ij = new double[measureCount][pointsCount];
        for (int i = 0; i < measureCount; i++) {
            for (int j = 0; j < pointsCount; j++) {
                ro_PP_pr_ij[i][j] = ro_BIK_ij[i][j] *
                        (1 + beta_fluid_ij[i][j] * (t_PP_ij[i][j] - t_ij[i][j])) *
                        (1 + gamma_fluid_ij[i][j] * (P_ij[i][j] - P_PP_ij[i][j]));
            }
        }
        return ro_PP_pr_ij;
    }

    // формула 14
    public static double[][] calculateM_mas_ij(double[][] N_mas_ij, double KF_conf) {
        int measureCount = N_mas_ij.length;
        int pointsCount = N_mas_ij[0].length;
        double[][] M_mas_ij = new double[measureCount][pointsCount];
        for (int i = 0; i < measureCount; i++) {
            for (int j = 0; j < pointsCount; j++) {
                M_mas_ij[i][j] = N_mas_ij[i][j] / KF_conf;
            }
        }
        return M_mas_ij;
    }

    // формула 15
    public static double[][] calculateMF_ij(double[][] M_re_ij, double[][] M_mas_ij, double MF_set_range) {
        int measureCount = M_re_ij.length;
        int pointsCount = M_re_ij[0].length;
        double[][] MF_ij = new double[measureCount][pointsCount];
        for (int i = 0; i < measureCount; i++) {
            for (int j = 0; j < pointsCount; j++) {
                MF_ij[i][j] = M_re_ij[i][j] * MF_set_range / M_mas_ij[i][j];
            }
        }
        return MF_ij;
    }

    // формула 16
    public static double[] calculateMF_j_avg(double[][] MF_ij) {
        return CommonFunctions.getAverageForEachColumn(MF_ij);
    }

    // формула 17
    public static double calculateS_MF_range(double[][] MF_ij, double[] MF_j_avg) {
        int measureCount = MF_ij.length;
        int pointsCount = MF_ij[0].length;
        int sigma_n_j = measureCount * pointsCount;
        double sum = 0;
        for (int j = 0; j < pointsCount; j++) {
            double sum1 = 0;
            for (double[] doubles : MF_ij) {
                sum1 += Math.pow((doubles[j] - MF_j_avg[j]) / MF_j_avg[j], 2);
            }
            sum += sum1;
        }
        return Math.sqrt(sum / (sigma_n_j - 1)) * 100;
    }

    // формула 18
    public static double calculateMF_range(double[] MF_j_avg) {
        int pointsCount = MF_j_avg.length;
        double sum = 0;
        for (double v : MF_j_avg) {
            sum += v;
        }

        return sum / pointsCount;
    }

    // формула 19
    public static double calculateK_gr(double K_PEP_gr, double MF_range) {
        return K_PEP_gr * MF_range;
    }

    // формула 20
    public static double[][] calculateKF_ij(double[][] N_mas_ij, double[][] M_re_ij) {
        return CommonFunctions.getDivisionOfTwoArrays(N_mas_ij, M_re_ij);
    }

    // формула 21
    public static double[] calculateKF_j_avg(double[][] KF_ij) {
        return CommonFunctions.getAverageForEachColumn(KF_ij);
    }

    // формула 22а
    public static double calculateS_KF_range(double[][] KF_ij, double[] KF_j_avg) {
        int measureCount = KF_ij.length;
        int pointsCount = KF_ij[0].length;
        double sum = 0;
        double sigma_n_j = pointsCount * measureCount;
        for (int j = 0; j < pointsCount; j++) {
            double sum1 = 0;
            for (double[] doubles : KF_ij) {
                sum1 += Math.pow((doubles[j] - KF_j_avg[j]) / KF_j_avg[j], 2);
            }
            sum += sum1;
        }
        return Math.sqrt(sum / sigma_n_j) * 100;
    }

    // формула 22б
    public static double[] calculateS_KF_k(double[][] KF_ij, double[] KF_j_avg) {
        int measureCount = KF_ij.length;
        int pointsCount = KF_ij[0].length;
        double[] S_KF_k = new double[pointsCount - 1];
        double sigma_n_j = measureCount * 2;
        for (int k = 0; k < pointsCount - 1; k++) {
            double sum = 0;
            for (int j = k; j <= k + 1; j++) {
                double sum1 = 0;
                for (double[] doubles : KF_ij) {
                    sum1 += Math.pow((doubles[j] - KF_j_avg[j]) / KF_j_avg[j], 2);
                }
                sum += sum1;
            }
            S_KF_k[k] = Math.sqrt(sum / sigma_n_j) * 100;
        }
        return S_KF_k;
    }

    // формула 23
    public static double calculateKF_range(double[] KF_j_avg) {
        int pointsCount = KF_j_avg.length;
        double sum = 0;
        for (double v : KF_j_avg) {
            sum += v;
        }
        return sum / pointsCount;
    }

    // формула 24
    public static double calculateEpsilon_PEP(double t_P_n, double S_MF_range) {
        return t_P_n * S_MF_range;
    }

    // формула 25
    public static double calculateTheta_sigma_PEP(double delta_KP, double delta_PP,
                                           double theta_t, double delta_UOI_K,
                                           double theta_MF_range, double delta_mas_0) {
        return 1.1 * Math.sqrt(Math.pow(delta_KP, 2) +
                Math.pow(delta_PP, 2) +
                Math.pow(theta_t, 2) +
                Math.pow(delta_UOI_K, 2) +
                Math.pow(theta_MF_range, 2) +
                Math.pow(delta_mas_0, 2));
    }

    // формула 28
    public static double calculateDelta_mas_0(double ZS, double[] Q_j_avg) {
        double Q_max = Arrays.stream(Q_j_avg).max().orElseThrow();
        double Q_min = Arrays.stream(Q_j_avg).min().orElseThrow();
        return 2 * ZS / (Q_min + Q_max) * 100;
    }

    public static double[] calculateQ_j_avg(double[][] Q_ij) {
        return CommonFunctions.getAverageForEachColumn(Q_ij);
    }

    // формула 27
    public static double calculateTheta_MForKF_range(double[] MForK_j_avg, double MForK_range) {
        double max = 0;
        for (double v : MForK_j_avg) {
            max = Math.max(max, Math.abs((v - MForK_range) / MForK_range));
        }
        return max * 100;
    }

    // формула 26
    public static double calculateTheta_t(double delta_t_KP, double delta_t_PP, String opFluid, double[][] ro_PP, double[][] t_PP) {
        double betta_fluid_max = Appendix.calculateBeta_fluid_max(opFluid, ro_PP, t_PP);
        return betta_fluid_max * Math.sqrt(Math.pow(delta_t_KP, 2) + Math.pow(delta_t_PP, 2)) * 100;
    }

    // формула 29
    public static double calculateDelta_PEP(double Z_P, double theta_sigma,
                                     double epsilon, double S_MF_range) {
        double ratio = theta_sigma / S_MF_range;
        double delta = -1;
        if (ratio >= 0.8 && ratio <= 8) {
            delta = Z_P * (theta_sigma + epsilon);
        } else if (ratio > 8) {
            delta = theta_sigma;
        }
        return delta;
    }

    // формула 30
    public static double calculateEpsilon_SOI_const(double t_P_n, double S_KF_range) {
        return t_P_n * S_KF_range;
    }

    // формула 31
    public static double calculateTheta_sigma_SOI_const(double delta_KP, double delta_PP, double theta_t,
                                                 double delta_UOI_K, double theta_KF_range, double delta_mas_0) {

        return 1.1 * Math.sqrt(
                Math.pow(delta_KP, 2) +
                        Math.pow(delta_PP, 2) +
                        Math.pow(theta_t, 2) +
                        Math.pow(delta_UOI_K, 2) +
                        Math.pow(theta_KF_range, 2) +
                        Math.pow(delta_mas_0, 2));
    }

    // формула 33
    public static double calculateDelta_SOI_const(double Z_P, double theta_sigma, double epsilon,
                                           double S_KF_range) {
        double ratio = theta_sigma / S_KF_range;
        double delta = -1;
        if (ratio >= 0.8 && ratio <= 8) {
            delta = Z_P * (theta_sigma + epsilon);
        } else if (ratio > 8) {
            delta = theta_sigma;
        }
        return delta;
    }

    // формула 34
    public static double[] calculateEpsilon_k(double t_P_n, double[] S_KF_k) {
        double[] epsilon_k = new double[S_KF_k.length];
        for (int k = 0; k < S_KF_k.length; k++) {
            epsilon_k[k] = t_P_n * S_KF_k[k];
        }
        return epsilon_k;
    }

    // формула 35
    public static double[] calculateTheta_sigma_k(double delta_KP, double delta_PP, double theta_t,
                                           double delta_UOI_K, double[] theta_KF_k, double[] delta_mas_0k) {
        int length = theta_KF_k.length;
        double[] theta_sigma_k = new double[length];
        for (int k = 0; k < length; k++) {
            theta_sigma_k[k] = 1.1 * Math.sqrt(
                    Math.pow(delta_KP, 2) +
                            Math.pow(delta_PP, 2) +
                            Math.pow(theta_t, 2) +
                            Math.pow(delta_UOI_K, 2) +
                            Math.pow(theta_KF_k[k], 2) +
                            Math.pow(delta_mas_0k[k], 2));
        }
        return theta_sigma_k;
    }

    // формула 37
    public static double[] calculateDelta_mas_0k(double ZS, double[] Q_kmin, double[] Q_kmax) {
        int length = Q_kmin.length;
        double[] delta_mas_0k = new double[length];
        for (int k = 0; k < length; k++) {
            delta_mas_0k[k] = 2 * ZS / (Q_kmin[k] + Q_kmax[k]) * 100;
        }

        return delta_mas_0k;
    }

    public static double[] calculateQ_kmin(double[] Q_j_avg) {
        int pointsCount = Q_j_avg.length;
        int length = pointsCount - 1;
        double[] Q_kmin = new double[length];
        for (int k = 0; k < length; k++) {
            Q_kmin[k] = Math.min(Q_j_avg[k], Q_j_avg[k + 1]);
        }
        return Q_kmin;
    }

    public static double[] calculateQ_kmax(double[] Q_j_avg) {
        int pointsCount = Q_j_avg.length;
        int length = pointsCount - 1;
        double[] Q_kmax = new double[length];
        for (int k = 0; k < length; k++) {
            Q_kmax[k] = Math.max(Q_j_avg[k], Q_j_avg[k + 1]);
        }
        return Q_kmax;
    }

    // формула 36
    public static double[] calculateTheta_KF_k(double[] KF_j_avg) {
        int pointsCount = KF_j_avg.length;
        int length = pointsCount - 1;
        double[] theta_KF_k = new double[length];
        double[] absKF = new double[length];
        for (int j = 0; j < length; j++) {
            absKF[j] = Math.abs((KF_j_avg[j] - KF_j_avg[j + 1]) / (KF_j_avg[j] + KF_j_avg[j + 1]));
        }
        Arrays.sort(absKF);
        for (int k = 0; k < length; k++) {
            theta_KF_k[k] = 0.5 * absKF[k] * 100;
        }
        return theta_KF_k;
    }

    // формула 38
    public static double[] calculateDelta_k(double[] Z_P_k, double[] theta_sigma_k,
                                     double[] epsilon_k, double[] S_KF_k) {
        int length = Z_P_k.length;
        double[] delta_k = new double[length];
        for (int k = 0; k < length; k++) {

            double ratio = theta_sigma_k[k] / S_KF_k[k];
            if (ratio >= 0.8 && ratio <= 8) {
                if (Z_P_k[k] == -1) {
                    delta_k[k] = theta_sigma_k[k];
                } else {
                    delta_k[k] = Z_P_k[k] * (theta_sigma_k[k] + epsilon_k[k]);
                }

            } else if (ratio > 0.8) {
                delta_k[k] = theta_sigma_k[k];
            }
        }
        return delta_k;
    }

    public static double[] calculateZ_P_k(double[] theta_sigma_k, double[] S_KF_k) {
        int len = theta_sigma_k.length;
        double[] Z_P_k = new double[len];
        for (int k = 0; k < len; k++) {
            double ratio = theta_sigma_k[k] / S_KF_k[k];
            Z_P_k[k] = Appendix.getZ_P(ratio);
        }
        return Z_P_k;
    }

    public static boolean checkDelta_k_j(double[] delta_k_j) {
        for (double el : delta_k_j) {
            if (Math.abs(el) > 0.02) return false;
        }
        return true;
    }


    public static boolean checkDeltaAsControl(double delta) {
        return Math.abs(delta) <= 0.2;
    }

    public static boolean checkDeltaAsOperating(double delta) {
        return Math.abs(delta) <= 0.25;
    }

    public static boolean checkDelta_kAsControl(double[] delta_k) {
        for (double elem : delta_k) {
            if (Math.abs(elem) > 0.2) return false;
        }
        return true;
    }

    public static boolean checkDelta_kAsOperating(double[] delta_k) {
        for (double elem : delta_k) {
            if (Math.abs(elem) > 0.25) return false;
        }
        return true;
    }
}
