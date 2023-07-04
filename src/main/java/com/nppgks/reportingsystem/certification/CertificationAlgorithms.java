package com.nppgks.reportingsystem.certification;

import java.util.Arrays;
import java.util.List;

public class CertificationAlgorithms {

    /**
     * Вычисление плотности нефти/нефтепродуктов,
     * приведенную к стандартным условиям при температуре 15°С
     */
    public static double calculateDensity15(String product, double density, double temp, double overpressure) {
        double result = calculateRo(product, density, density, temp, overpressure);
        double prevResult = density;

        while (Math.abs(result - prevResult) > 0.01) {
            System.out.println("dif: " + Math.abs(result - prevResult));
            prevResult = result;
            result = calculateRo(product, result, density, temp, overpressure);
        }
        System.out.println("dif: " + Math.abs(result - prevResult));
        return result;
    }

    private static double calculateRo(String product, double density15, double densityMeas, double temp, double overpressure) {
        double CTL = calculateCTL(product, density15, temp);
        System.out.println("CTL=" + CTL);
        double CPL = calculateCPL(density15, temp, overpressure);
        System.out.println("CPL=" + CPL);
        return densityMeas / (CTL * CPL);
    }

    /**
     * Вычисление массы нетто нефти
     */
    public static double calculateMass_Net(double M, double W_B, double W_MP, double W_XC) {
        return M * (1 - (W_B + W_MP + W_XC) / 100);
    }

    /**
     * Вычисление массы балласта
     */
    public static double calculateMass_ballast(double M, double W_B, double W_MP, double W_XC) {
        return M * (W_B + W_MP + W_XC) / 100;
    }


    /**
     * Вычисление поправочного коэффициента CTL,
     * учитывающий влияние температуры нефти/нефтепродуктов на их объем
     */
    public static double calculateCTL(String environment, double p15, double temp) {
        double beta = calculateBeta(environment, p15);
        return Math.exp(-beta * (temp - 15) * (1 + 0.8 * beta * (temp - 15)));
    }

    /**
     * Вычисление поправочного коэффициента CPL,
     * учитывающий влияние давления нефти/нефтепродуктов на их объем
     */
    public static double calculateCPL(double p15, double temp, double overpressure) {

        double gamma_t = calculateGamma(p15, temp);
        return 1 / (1 - gamma_t * overpressure);
    }

    /**
     * Вычисление коэффициента объемного расширения нефти/нефтепродуктов - β
     */
    public static double calculateBeta(String environment, double p15) {
        double K0 = 0;
        double K1 = 0;
        double K2 = 0;
        if (environment.equals("нефть")) {
            K0 = 613.9723;
            K1 = 0;
            K2 = 0;
        } else {
            // Бензины
            if (p15 >= 611.2 && p15 < 770.9) {
                K0 = 346.4228;
                K1 = 0.43884;
                K2 = 0;
            }
            // Топлива, занимающие по плотности промежуточное место между бензинами и керосинами
            else if (p15 >= 770.9 && p15 < 788) {
                K0 = 2690.744;
                K1 = 0;
                K2 = -0.0033762;
            }
            // Топлива и керосины для реактивных двигателей. авиационное реактивное топливо ДЖ ЕТ А-1 по ГОСТ 32595
            else if (p15 >= 788 && p15 < 838.7) {
                K0 = 594.5418;
                K1 = 0;
                K2 = 0;
            }
            // Дизельные топлива, мазуты, печные топлива
            else if (p15 >= 838.7 && p15 < 1163.9) {
                K0 = 186.9696;
                K1 = 0.4862;
                K2 = 0;
            }
        }
        return (K0 + K1 * p15) / (p15 * p15) + K2;
    }

    /**
     * Вычисление коэффициента сжимаемости нефти/нефтепродуктов - γ
     */
    public static double calculateGamma(double p15, double temp) {
        return 0.001 * Math.exp(-1.62080 + 0.00021592 * temp + 870960 / (p15 * p15) + 4209.2 * temp / (p15 * p15));
    }

    /**
     * Вычисление массовой доли воды в нефти, %
     */
    public static double calculateWaterFraction(double fi_w, double ro_w, double ro_bik) {
        return fi_w * ro_w / (((1 - fi_w / 100) * ro_bik) + fi_w / 100 * ro_w);
    }

    /**
     * Вычисление массовой доли хлористых солей в обезвоженной дегазированной нефти. %
     */
    public static double calculateChlorideSaltsFraction(double fi_cs, double ro_cs) {
        return 0.1 * fi_cs / ro_cs;
    }

    /**
     * Вычисление массового расхода рабочей жидкости через СРМ, т/ч
     */
    public static double[][] calculateQ_mass_through_CPM(double[][] M_pu_ij, double[][] T_ij) {
        int measureCount = M_pu_ij.length;
        int pointsCount = M_pu_ij[0].length;
        double[][] Q_ij = new double[measureCount][pointsCount];
        for (int i = 0; i < measureCount; i++) {
            for (int j = 0; j < pointsCount; j++) {
                Q_ij[i][j] = M_pu_ij[i][j] * 3600 / T_ij[i][j];
            }
        }
        return Q_ij;
    }

    /**
     * Вычисление нижнего предела массового расхода, т/ч
     */
    public static double calculateQ_min(double[] Q_j) {
        return Arrays.stream(Q_j).min().getAsDouble();
    }

    /**
     * Вычисление верхнего предела массового расхода, т/ч
     */
    public static double calculateQ_max(double[] Q_j) {
        return Arrays.stream(Q_j).max().getAsDouble();
    }

    /**
     * Вычисление массы рабочей жидкости, определеную с помощью СРМ, т
     */
    public static double[][] calculateM_ij(double[][] N_ij, double K_pm) {
        int measureCount = N_ij.length;
        int pointsCount = N_ij[0].length;
        double[][] M_ij = new double[measureCount][pointsCount];
        for (int i = 0; i < measureCount; i++) {
            for (int j = 0; j < pointsCount; j++) {
                M_ij[i][j] = N_ij[i][j] / K_pm;
            }
        }
        return M_ij;
    }

    /**
     * Вычисление массы рабочей жидкости, измеренную с помощью ЭСРМ, т
     */
    public static double[][] calculateM_e_ij(List<Double> K_pmList, List<double[][]> N_ijList) {
        int measureCount = N_ijList.get(0).length;
        int pointsCount = N_ijList.get(0)[0].length;
        int esrmCount = N_ijList.size();
        double[][] M_ij = new double[measureCount][pointsCount];
        for (int k = 0; k < esrmCount; k++) {
            for (int i = 0; i < measureCount; i++) {
                for (int j = 0; j < pointsCount; j++) {
                    M_ij[i][j] = M_ij[i][j] + N_ijList.get(k)[i][j] / K_pmList.get(k);
                }
            }
        }

        return M_ij;
    }

    /**
     * Вычисление массового расхода рабочей жидкости через поверяемый СРМ, т/ч
     */
    public static double[] calculateQ_j(double[][] M_e_ij, double[][] T_ij) {
        int measureCount = M_e_ij.length;
        int pointsCount = M_e_ij[0].length;
        double[] Q_j = new double[pointsCount];
        for (int j = 0; j < pointsCount; j++) {
            for (int i = 0; i < measureCount; i++) {
                Q_j[j] = Q_j[j] + M_e_ij[i][j] * 3600 / T_ij[i][j];
            }
            Q_j[j] = Q_j[j] / measureCount;
        }

        return Q_j;
    }

    /**
     * Вычисление градуировочного коэффициента СРМ, г/с/мкс
     */
    public static double calculateK_M(double[][] M_pu_ij, double[][] M_ij, double K_m_yct) {
        int measureCount = M_pu_ij.length;
        int pointsCount = M_pu_ij[0].length;
        double K_M = 0;
        for (int j = 0; j < pointsCount; j++) {
            double[] K_M_j = new double[pointsCount];
            for (int i = 0; i < measureCount; i++) {
                K_M_j[j] = K_M_j[j] + M_pu_ij[i][j] * K_m_yct / M_ij[i][j];
            }
            K_M = K_M + K_M_j[j] / measureCount;
        }

        return K_M / pointsCount;
    }

    /**
     * Вычисление коэффициента коррекции СРМ
     */
    public static double calculateMF(double[][] M_pu_ij, double[][] M_ij, double MF_yct) {
        int measureCount = M_pu_ij.length;
        int pointsCount = M_pu_ij[0].length;
        double MF = 0;
        for (int j = 0; j < pointsCount; j++) {
            double[] MF_j = new double[pointsCount];
            for (int i = 0; i < measureCount; i++) {
                MF_j[j] = MF_j[j] + M_pu_ij[i][j] * MF_yct / M_ij[i][j];
            }
            MF = MF + MF_j[j] / measureCount;
        }

        return MF / pointsCount;
    }

    /**
     * Вычисление СКО результатов измерений в поверяемых точках
     */
    public static double[] calculateS_j(double[][] MForK_ij, double[] MForK_j) {
        int measureCount = MForK_ij.length;
        int pointsCount = MForK_ij[0].length;
        double[] S_j = new double[pointsCount];
        for (int j = 0; j < pointsCount; j++) {
            for (int i = 0; i < measureCount; i++) {
                S_j[j] = S_j[j] + Math.pow(MForK_ij[i][j] - MForK_j[j], 2);
            }
            S_j[j] = Math.sqrt(S_j[j] / (measureCount - 1)) * 100 / MForK_j[j];
        }

        return S_j;
    }

    /**
     * Вычисление СКО результатов измерений в поверяемых точках
     */
    public static double[] calculateS0_j(double[] S_j, int measureCount) {
        int pointsCount = S_j.length;
        double[] S0j = new double[pointsCount];
        for (int j = 0; j < pointsCount; j++) {
            S0j[j] = S_j[j] / Math.sqrt(measureCount);
        }

        return S0j;
    }

    /**
     * Вычисление границы случайной погрешности СРМ
     */
    public static double calculateEps(double[] t095_j, double[] S0_j) {
        int pointsCount = t095_j.length;
        double epsMax = 0;
        for (int j = 0; j < pointsCount; j++) {
            double eps = t095_j[j] * S0_j[j];
            if (epsMax < eps) {
                epsMax = eps;
            }
        }

        return epsMax;
    }

    /**
     * Вычисление границы относительной погрешности СРМ
     */
    public static double calculateDelta(double eps, double theta, double S0, double theta_sigma0, double theta_v0, double theta_t, double theta_p, double theta_A, double theta_ivk, double theta_Z, double theta_Mt, double theta_MP) {
        double S_theta = Math.sqrt((Math.pow(theta_sigma0, 2) + Math.pow(theta_v0, 2) + Math.pow(theta_t, 2) + Math.pow(theta_p, 2) + Math.pow(theta_A, 2) + Math.pow(theta_ivk, 2) + Math.pow(theta_Z, 2) + Math.pow(theta_Mt, 2) + Math.pow(theta_MP, 2)) / 3);
        double S_sigma = Math.sqrt(Math.pow(S_theta, 2) + Math.pow(S0, 2));
        double K = (eps + theta) / (S0 + S_theta);
        double ratio = theta / S0;
        double delta;
        if (ratio < 0.8) delta = eps;
        else if (ratio <= 8) delta = K * S_sigma;
        else delta = theta;
        return delta;
    }


    /**
     * Вычисление объемнойго расхода продукта в блоке измерительных линий
     * при применении объемных преоразователей расхода
     */
    public static double calculateQ_vol(double f, double K) {
        return f * 3600 / K;
    }

    /**
     * Вычисление массового расхода продукта в блоке измерительных линий
     * при применении объемных преобразователей расхода
     */
    public static double calculateQ_mass(double Q_il, double ro_il) {
        return Q_il * ro_il / 1000;
    }

    /**
     * Вычисление кинематической вязкости
     */
    public static double calculateKinematicViscosity(double dynVisc, double density) {
        return dynVisc / (density * 0.001);
    }
}
