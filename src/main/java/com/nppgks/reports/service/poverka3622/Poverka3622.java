package com.nppgks.reports.service.poverka3622;

import com.nppgks.reports.service.poverka3622.data.InitialData;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class Poverka3622 {

    private final double[][] Q;
    private final double[][] N_e;
    private final double[][] N_p;
    private final double[][] T;
    private final double f_r_max;
    private final double Q_r_max;
    private final double MF_p;
    private final double[][] K_e;
    private final int measureCount;
    private final int pointsCount;
    private final double ZS;
    private final double theta_e;
    private final double theta_t;
    private final double theta_p;
    private final double theta_N;
    private final double theta_Dt;
    private final double theta_Dp;
    private final double theta_PDt;
    private final double theta_PDp;
    private boolean theta_zjIsRequired;

    public Poverka3622(InitialData data) {
        this.Q = data.getQ_ij();
        this.N_e = data.getN_e_ij();
        this.N_p = data.getN_p_ij();
        this.T = data.getT_ij();
        this.f_r_max = data.getF_p_max();
        this.Q_r_max = data.getQ_p_max();
        this.K_e = data.getK_e_ij();
        this.MF_p = data.getMF_p();
        this.ZS = data.getZS();
        this.theta_e = data.getTheta_e();
        this.theta_t = data.getTheta_t();
        this.theta_p = data.getTheta_p();
        this.theta_N = data.getTheta_N();
        this.theta_PDt = data.getTheta_PDt();
        this.theta_PDp = data.getTheta_PDp();
        this.theta_Dt = data.getTheta_Dt();
        this.theta_Dp = data.getTheta_Dp();
        measureCount = data.getMeasureCount();
        pointsCount = data.getPointsCount();
    }

    public double calculateK_pm() {
        log.info("Рассчет коэффициента преобразования поверяемого СРМ (K_pm, имп/т) согласно п.7.1 по формуле (1) МИ3622-2020");
        log.debug("Максимальное значение частоты, установленное в СРМ (f_r_max, Гц) {}", f_r_max);
        log.debug("Максимальное значение расхода (Q_r_max, т/ч) {}", Q_r_max);
        double Kpm = f_r_max * 3600 / Q_r_max;
        log.info("K_pm = {}", Kpm);

        return Kpm;
    }

    public double[][] calculateM_e_ij() {
        log.info("Рассчет массы измеряемой среды, измеренная преобразователем массового расхода (M_e, т) согласно п.7.2 по формуле (3) МИ3622-2020");
        log.debug("Кол-во импульсов, поступившее с ПР (N_e, имп) {}", Arrays.deepToString(N_e));
        log.debug("Коэффициент преобразования ПР, вычисленный по градуировочной характеристике (K_e, имп/т) {}", Arrays.deepToString(K_e));
        double[][] M_e = new double[measureCount][pointsCount];
        for (int i = 0; i < measureCount; i++) {
            for (int j = 0; j < pointsCount; j++) {
                M_e[i][j] = N_e[i][j] / K_e[i][j];
            }
        }
        log.info("M_e = {}", Arrays.deepToString(M_e));
        return M_e;
    }

    public double[][] calculateMF_ij() {
        double Kpm = calculateK_pm();
        double[][] M_e = calculateM_e_ij();
        double[][] M = new double[measureCount][pointsCount];
        for (int i = 0; i < measureCount; i++) {
            for (int j = 0; j < pointsCount; j++) {
                M[i][j] = N_p[i][j] * MF_p / Kpm;
            }
        }
        log.info("Рассчет коэффициента коррекции поверяемого СРМ (MF_ij) согласно п.7.2 по формуле (2) МИ3622-2020");
        log.debug("Масса измеряемой среды, измеренная преобразователем массового расхода (M_e, т) {}", Arrays.deepToString(M_e));
        log.debug("Масса измеряемой среды, измеренная поверяемым СРМ (M_ij, т) {}", Arrays.deepToString(M));
        double[][] MF = new double[measureCount][pointsCount];
        for (int i = 0; i < measureCount; i++) {
            for (int j = 0; j < pointsCount; j++) {
                MF[i][j] = M_e[i][j] / M[i][j];
            }
        }
        log.info("MF_ij = {}", Arrays.deepToString(MF));
        return MF;
    }

    public double calculateMF() {
        double[] MF_j = calculateMF_j();
        log.info("Рассчет среднего значения коэффициента коррекции поверяемого СРМ (MF) согласно п.7.4 по формуле (7) МИ3622-2020");
        log.debug("Значения коэффициентов коррекции поверяемого СРМ (MF_j) {}", Arrays.toString(MF_j));
        log.debug("Количество точек (m) {}", pointsCount);
        double MF = 0;
        for (int j = 0; j < pointsCount; j++) {
            MF = MF + MF_j[j];
        }
        MF = MF / pointsCount;

        log.info("MF = {}", MF);
        return MF;
    }

    public double[] calculateMF_j() {
        double[][] MF_ij = calculateMF_ij();
        if (MF_ij == null) {
            MF_ij = calculateMF_ij();
        }
        log.info("Рассчет значения коэффициента коррекции поверяемого СРМ в j-й точке (MF) согласно п.7.3 по формуле (6) МИ3622-2020");
        log.debug("Значения коэффициентов коррекции поверяемого СРМ (MF) {}", Arrays.deepToString(MF_ij));
        log.debug("Количество измерений (n) {}", measureCount);
        double[] MF_j = new double[pointsCount];
        for (int j = 0; j < pointsCount; j++) {
            double sum = 0;
            for (int i = 0; i < measureCount; i++) {
                sum = sum + MF_ij[i][j];
            }
            MF_j[j] = sum / measureCount;
        }
        log.info("MF_j = {}", Arrays.toString(MF_j));
        return MF_j;
    }

    public double calculateK() {
        double[] Kj = calculateK_j();
        double K = 0;
        for (int j = 0; j < pointsCount; j++) {
            K = K + Kj[j];
        }
        K = K / pointsCount;

        return K;
    }

    public double[] calculateK_j() {
        double[][] K_ij = calculateK_ij();
        double[] Kj = new double[pointsCount];
        for (int j = 0; j < pointsCount; j++) {
            double sum = 0;
            for (int i = 0; i < measureCount; i++) {
                sum = sum + K_ij[i][j];
            }
            Kj[j] = sum / measureCount;
        }
        return Kj;
    }

    public double[][] calculateK_ij() {
        double[][] K_ij = new double[measureCount][pointsCount];
        double[][] M_e_ij = calculateM_e_ij();
        for (int i = 0; i < measureCount; i++) {
            for (int j = 0; j < pointsCount; j++) {
                K_ij[i][j] = N_p[i][j] / M_e_ij[i][j];
            }
        }
        return K_ij;
    }

    public double calculateMF_prime() {
        return MF_p * calculateMF();
    }

    public double[][] calculateF_ij() {
        log.info("Рассчет частоты выходного сигнала поверяемого СРМ (f, Гц) согласно п.7.9 по формуле (12) МИ3622-2020");
        log.debug("кол-во импульсов, поступившее с поверяемого СРМ (N_r, имп) {}", Arrays.deepToString(N_p));
        log.debug("время измерения (T, с) {}", Arrays.deepToString(T));
        double[][] f_ij = new double[measureCount][pointsCount];
        for (int i = 0; i < measureCount; i++) {
            for (int j = 0; j < pointsCount; j++) {
                f_ij[i][j] = N_p[i][j] / T[i][j];
            }
        }
        log.info("f_ij = {}", Arrays.deepToString(f_ij));
        return f_ij;
    }

    public double[] calculateF_j() {
        double[][] fij = calculateF_ij();
        double[] fj = new double[pointsCount];
        for (int j = 0; j < pointsCount; j++) {
            double sum = 0;
            for (int i = 0; i < measureCount; i++) {
                sum = sum + fij[i][j];
            }
            fj[j] = sum / measureCount;
        }
        return fj;
    }

    public double[] calculateS_j() {
        double[][] MF_ij = calculateMF_ij();
        double[] MF_j = calculateMF_j();
        log.info("Рассчет СКО результатов измерений в j-й точке (S_j) согласно п.7.11 по формуле (14) МИ3622-2020");
        log.debug("Значения коэффициентов преобразования/коррекции поверяемого СРМ (MF_ij or K_ij) {}", Arrays.deepToString(MF_ij));
        log.debug("Среднее значение коэффициента преобразования/коррекции поверяемого СРМ в j-й точке (MF_j) {}", Arrays.toString(MF_j));
        log.debug("Кол-во измерений (n) {}", measureCount);
        double[] S_j = new double[pointsCount];
        for (int j = 0; j < pointsCount; j++) {
            double sum = 0;
            for (int i = 0; i < measureCount; i++) {
                sum = sum + (Math.pow(MF_ij[i][j] - MF_j[j], 2));
            }
            S_j[j] = 1 / MF_j[j] * Math.sqrt(sum / (measureCount - 1)) * 100;
        }
        log.info("S_j = {}", Arrays.toString(S_j));
        return S_j;
    }

    public boolean checkS_j() {
        double[] S_j = calculateS_j();
        return Arrays.stream(S_j).max().getAsDouble() <= 0.03;
    }

    public double[] calculateS_0j() {
        double[] S_0j = new double[pointsCount];
        double[] S_j = calculateS_j();
        log.info("Рассчет СКО среднего значения результатов измерений в j-й точке (S0) согласно п.7.12 по формуле (16) МИ3622-2020");
        log.debug("Значение СКО в j-й точке (S_j) {}", Arrays.toString(S_j));
        log.debug("Кол-во измерений (n) {}", measureCount);
        for (int j = 0; j < pointsCount; j++) {
            S_0j[j] = S_j[j] / Math.sqrt(pointsCount);
        }
        log.info("S_0j = {}", Arrays.toString(S_0j));
        return S_0j;
    }

    public double[] calculateEps_j() {
        double[] S_0j = calculateS_0j();
        double[] t095Arr = calculateT_095();
        double t095 = t095Arr[pointsCount - 5];
        if (S_0j == null) {
            S_0j = calculateS_0j();
        }
        double[] eps_j = new double[pointsCount];

        log.info("Рассчет границы случайной составляющей погрешности СРМ в j-й точке (eps_j) согласно п.7.13 по формуле (17) МИ3622-2020");
        log.debug("СКО среднего значения результатов измерений в j-й точке (S_0j) {}", Arrays.toString(S_0j));
        log.debug("Квантиль распределения Стъюдента при доверительной вероятности Р=0.95 (t_0_95) {}", t095);
        for (int j = 0; j < pointsCount; j++) {
            eps_j[j] = t095 * S_0j[j];
        }
        log.info("eps_j = {}", Arrays.toString(eps_j));
        return eps_j;
    }

    public double[] calculateT_095() {
        return new double[]{2.776, 2.571, 2.447, 2.365, 2.306, 2.262};
    }

    public double calculateEps_D() {
        double[] eps_j = calculateEps_j();
        log.info("Рассчет границы случайной составляющей погрешности СРМ в диапазоне измерений (E_d) согласно п.7.14 по формуле (18) МИ3622-2020");
        log.debug("Значения границ случайной составляющей погрешности СРМ (eps_j) {}", eps_j);
        double eps_d = Arrays.stream(eps_j).max().getAsDouble();
        log.info("eps_d = {}", eps_d);
        return eps_d;
    }

    public double[] calculateQ_j() {
        double[] Q_j = new double[pointsCount];
        for (int j = 0; j < pointsCount; j++) {
            double sum = 0;
            for (int i = 0; i < measureCount; i++) {
                sum = sum + Q[i][j];
            }
            Q_j[j] = sum / measureCount;
        }
        return Q_j;
    }

    public double[] calculateTheta_sigma_j() {
        double[] theta_sigma_j = new double[pointsCount];
        double[] theta_zj = new double[pointsCount];
        if (theta_zjIsRequired) {
            theta_zj = calculateTheta_zj();
        }
        log.info("Рассчет границы неисключенной систематической погрешности в j-й точке (Theta_sigma_j) согласно п.7.16 по формуле (20) МИ3622-2020");
        log.debug("Граница составляющей неисключенной систематической погрешности, обусловленной нестабильностью нуля СРМ в j-ой точке (Theta_jz, %) {}", Arrays.toString(theta_zj));
        log.debug("Другие параметры: ZS = {}, theta_e = {}, theta_t = {}, theta_P = {}, theta_N = {}", ZS, theta_e, theta_t, theta_p, theta_N);
        for (int j = 0; j < pointsCount; j++) {
            theta_sigma_j[j] = 1.1 * Math.sqrt(
                            Math.pow(theta_e, 2) +
                            Math.pow(theta_t, 2) +
                            Math.pow(theta_p, 2) +
                            Math.pow(theta_N, 2) +
                            Math.pow(theta_zj[j], 2));
        }
        log.info("theta_sigma_j = {}", Arrays.toString(theta_sigma_j));
        return theta_sigma_j;
    }

    public double[] calculateEps_PDk() {
        double[] eps_j = calculateEps_j();
        log.info("Рассчет границы случайной составляющей погрешности СРМ в к-м поддиапазоне рабочего диапазона измерений (E_PD) согласно п.7.15 по формуле (19) МИ3622-2020");
        log.debug("значения границ случайной составляющей погрешности СРМ (eps_j) {}", Arrays.toString(eps_j));
        int subrangeCount = pointsCount - 1;
        double[] eps_pdk = new double[subrangeCount];
        for (int k = 0; k < subrangeCount; k++) {
            eps_pdk[k] = Math.max(eps_j[k], eps_j[k + 1]);
        }
        log.info("eps_pdk = {}", Arrays.toString(eps_pdk));
        return eps_pdk;
    }

    public double calculateTheta_sigma_D() {
        double theta_Dz = calculateTheta_Dz();
        double theta_D = calculateTheta_D();
        log.info("Рассчет границы неисключенной систематической погрешности (Theta_sigma_D, %) согласно п.7.17 по формуле (22) МИ3622-2020");
        log.debug("Граница составляющей неисключенной систематической погрешности, обусловленной нестабильностью нуля СРМ (theta_D_z, %) {}", theta_Dz);
        log.debug("Граница составляющей неисключенной систематической погрешности, обусловленной погрешностью аппроксимации градуировочной характеристики (theta_D, %) {}", theta_D);
        log.debug("Дргуие параметры: ZS = {}, theta_e = {}, theta_Dt = {}, theta_Dp ={}, theta_N = {}", ZS, theta_e, theta_Dt, theta_Dp, theta_N);
        double theta_sigma_D = 1.1 * Math.sqrt(
                        Math.pow(theta_e, 2) +
                        Math.pow(theta_Dt, 2) +
                        Math.pow(theta_Dp, 2) +
                        Math.pow(theta_N, 2) +
                        Math.pow(theta_Dz, 2) +
                        Math.pow(theta_D, 2));
        log.info("theta_sigma_D = {}", theta_sigma_D);
        return theta_sigma_D;
    }

    public double[] calculateTheta_sigma_PDk() {
        int subrangeCount = pointsCount - 1;
        double[] theta_sigma_PDk = new double[subrangeCount];
        double[] theta_PDz = calculateTheta_PDz();
        double[] theta_PDk = calculateTheta_PDk();
        log.info("Рассчет границы неисключенной систематической погрешности в к-м поддиапазоне (theta_sigma_PDk) согласно п.7.17 по формуле (22) МИ3622-2020");
        log.debug("Используемые данные: ZS = {}, theta_e = {}, theta_PDt = {}, theta_PDp = {}, theta_N = {}", ZS, theta_e, theta_PDt, theta_PDp, theta_N);
        for (int k = 0; k < subrangeCount; k++) {
            theta_sigma_PDk[k] = 1.1 * Math.sqrt(
                            Math.pow(theta_e, 2) +
                            Math.pow(theta_PDt, 2) +
                            Math.pow(theta_PDp, 2) +
                            Math.pow(theta_N, 2) +
                            Math.pow(theta_PDz[k], 2) +
                            Math.pow(theta_PDk[k], 2));
        }
        log.info("theta_sigma_PDk = {}", Arrays.toString(theta_sigma_PDk));
        return theta_sigma_PDk;
    }

    public double[] calculateDelta_j() {
        double[] delta_j = new double[pointsCount];
        double[] theta_sigma_j = calculateTheta_sigma_j();
        double[] S_0j = calculateS_0j();
        double[] eps_j = calculateEps_j();

        log.info("Рассчет относительной погрешности СРМ (контрольного) в j-й точке диапазона измерений (delta_j) согласно п.7.21 по формуле (28) МИ3622-2020");
        log.debug("Данные: theta_sigma_j = {}, S_0j = {}", Arrays.toString(theta_sigma_j), Arrays.toString(S_0j));
        double[] S_theta_j = calculateS_theta_j();
        double[] S_sigma_j = calculateS_sigma_j();
        double[] t_sigma_j = calculateT_sigma_j();

        for (int j = 0; j < pointsCount; j++) {
            double ratio = theta_sigma_j[j] / S_0j[j];
            if (ratio <= 8 && ratio >= 0.8) {
                t_sigma_j[j] = (eps_j[j] + theta_sigma_j[j]) / (S_theta_j[j] + S_0j[j]);
                delta_j[j] = t_sigma_j[j] * S_sigma_j[j];
            } else if (ratio > 8) {
                delta_j[j] = theta_sigma_j[j];
            }
        }
        log.info("delta_j = {}", delta_j);
        return delta_j;
    }

    public double[] calculateT_sigma_j() {
        double[] theta_sigma_j = calculateTheta_sigma_j();
        double[] S_0j = calculateS_0j();
        double[] eps_j = calculateEps_j();
        double[] S_theta_j = calculateS_theta_j();
        double[] t_sigma_j = new double[pointsCount];

        for (int j = 0; j < pointsCount; j++) {
            t_sigma_j[j] = (eps_j[j] + theta_sigma_j[j]) / (S_theta_j[j] + S_0j[j]);
        }
        return t_sigma_j;
    }

    public double[] calculateS_sigma_j() {
        double[] S_0j = calculateS_0j();
        double[] S_theta_j = calculateS_theta_j();
        double[] S_sigma_j = new double[pointsCount];
        for (int j = 0; j < pointsCount; j++) {
            S_sigma_j[j] = Math.sqrt(
                    Math.pow(S_theta_j[j], 2) +
                            Math.pow(S_0j[j], 2));
        }
        return S_sigma_j;
    }

    public double[] calculateS_theta_j() {
        double[] theta_zj = calculateTheta_zj();
        double[] S_theta_j = new double[pointsCount];
        for (int j = 0; j < pointsCount; j++) {
            S_theta_j[j] = Math.sqrt(
                    Math.pow(theta_e, 2) +
                            Math.pow(theta_t, 2) +
                            Math.pow(theta_p, 2) +
                            Math.pow(theta_N, 2) +
                            Math.pow(theta_zj[j], 2));
        }
        return S_theta_j;
    }

    public double calculateDelta_D() {
        double delta_D = 0;
        double theta_sigma_D = calculateTheta_sigma_D();
        double S_D = Arrays.stream(calculateS_0j()).max().getAsDouble();
        double ratio = theta_sigma_D / S_D;
        if (ratio <= 8 && ratio >= 0.8) {
            double theta_Dz = calculateTheta_Dz();
            double theta_D = calculateTheta_D();
            double S_theta_D = Math.sqrt(
                            Math.pow(theta_e, 2) +
                            Math.pow(theta_Dt, 2) +
                            Math.pow(theta_Dp, 2) +
                            Math.pow(theta_N, 2) +
                            Math.pow(theta_Dz, 2) +
                            Math.pow(theta_D, 2));
            double t_sigma_D = (calculateEps_D() + theta_sigma_D) / (S_D + S_theta_D);
            double S_sigma_D = Math.sqrt(
                    Math.pow(S_theta_D, 2) +
                            Math.pow(S_D, 2));
            delta_D = t_sigma_D * S_sigma_D;
        } else if (ratio > 8) {
            delta_D = theta_sigma_D;
        }
        log.info("Рассчет относительной погрешности СРМ (рабочего) в рабочем диапазоне измерений (delta_D) согласно п.7.22 по формуле (32) МИ3622-2020");
        log.info("delta_D = {}", delta_D);
        return delta_D;
    }

    public double[] calculateDelta_PDk() {
        int subrangeCount = pointsCount - 1;
        double[] delta_PDk = new double[subrangeCount];
        double[] theta_sigma_PDk = calculateTheta_sigma_PDk();
        double[] S_0_j = calculateS_0j();
        double[] S_PDk = calculateS_PDk();
        double[] theta_PDk = calculateTheta_PDk();
        double[] theta_PDz = calculateTheta_PDz();
        double[] S_theta_PDk = calculateS_theta_PDk();
        double[] S_sigma_PDk = calculateS_sigma_PDk();

        double[] t_sigma_PDk = calculateT_sigma_PDk();

        for (int k = 0; k < subrangeCount; k++) {
            S_theta_PDk[k] = Math.sqrt(
                            Math.pow(theta_e, 2) +
                            Math.pow(theta_PDt, 2) +
                            Math.pow(theta_PDp, 2) +
                            Math.pow(theta_N, 2) +
                            Math.pow(theta_PDz[k], 2) +
                            Math.pow(theta_PDk[k], 2));
            S_PDk[k] = Math.max(S_0_j[k], S_0_j[k + 1]);
            double ratio = theta_sigma_PDk[k] / S_PDk[k];
            if (ratio <= 8 && ratio >= 0.8) {
                delta_PDk[k] = t_sigma_PDk[k] * S_sigma_PDk[k];
            } else if (ratio > 8) {
                delta_PDk[k] = theta_sigma_PDk[k];
            }
        }

        log.info("Рассчет относительной погрешности СРМ (рабочего) в к-м поддиапазоне рабочего диапазона измерений (delta_PDk, %) согласно п.7.23 по формуле (36) МИ3622-2020");
        log.info("delta_PDk = {}", Arrays.toString(delta_PDk));
        return delta_PDk;
    }

    public double[] calculateT_sigma_PDk() {
        double[] theta_sigma_PDk = calculateTheta_sigma_PDk();
        double[] eps_PDk = calculateEps_PDk();
        double[] S_PDk = calculateS_PDk();
        double[] S_theta_PDk = calculateS_theta_PDk();
        int subrangeCount = pointsCount - 1;
        double[] t_sigma_PDk = new double[subrangeCount];
        for (int k = 0; k < subrangeCount; k++) {
            t_sigma_PDk[k] = (eps_PDk[k] + theta_sigma_PDk[k]) / (S_theta_PDk[k] + S_PDk[k]);
        }
        return t_sigma_PDk;
    }

    public double[] calculateS_sigma_PDk() {
        double[] S_PDk = calculateS_PDk();
        double[] S_theta_PDk = calculateS_theta_PDk();
        int subrangeCount = pointsCount - 1;
        double[] S_sigma_PDk = new double[subrangeCount];
        for (int k = 0; k < subrangeCount; k++) {
            S_sigma_PDk[k] = Math.sqrt(
                                Math.pow(S_theta_PDk[k], 2) +
                            Math.pow(S_PDk[k], 2));
        }
        return S_sigma_PDk;
    }

    public double[] calculateS_theta_PDk() {
        double[] theta_PDk = calculateTheta_PDk();
        double[] theta_PDz = calculateTheta_PDz();
        int subrangeCount = pointsCount - 1;
        double[] S_theta_PDk = new double[subrangeCount];

        for (int k = 0; k < subrangeCount; k++) {
            S_theta_PDk[k] = Math.sqrt(
                    Math.pow(theta_e, 2) +
                            Math.pow(theta_PDt, 2) +
                            Math.pow(theta_PDp, 2) +
                            Math.pow(theta_N, 2) +
                            Math.pow(theta_PDz[k], 2) +
                            Math.pow(theta_PDk[k], 2));
        }
        return S_theta_PDk;
    }

    public double[] calculateS_PDk() {
        double[] S_0_j = calculateS_0j();
        int subrangeCount = pointsCount - 1;
        double[] S_PDk = new double[subrangeCount];
        for (int k = 0; k < subrangeCount; k++) {
            S_PDk[k] = Math.max(S_0_j[k], S_0_j[k + 1]);
        }
        return S_PDk;
    }

    public boolean checkIfWorkingSRMIsFit() {
        double delta_D = calculateDelta_D();
        double[] delta_Pdk = calculateDelta_PDk();
        return Math.max(Arrays.stream(delta_Pdk).max().getAsDouble(), delta_D) <= 0.25;
    }

    public boolean checkIfControlSRMIsFit(boolean theta_zjIsRequired) {
        double[] delta_j = calculateDelta_j();
        return Arrays.stream(delta_j).max().getAsDouble() <= 0.2;
    }

    public double[] calculateTheta_zj() {
        double[] Q_j = calculateQ_j();
        double[] theta_zj = new double[pointsCount];
        for (int j = 0; j < pointsCount; j++) {
            theta_zj[j] = ZS / Q_j[j] * 100;
        }
        return theta_zj;
    }

    public double calculateTheta_Dz() {
        double Q_min = calculateQ_min();
        return ZS / Q_min * 100;
    }

    public double calculateQ_min() {
        double[] Q_j = calculateQ_j();
        return Arrays.stream(Q_j).min().getAsDouble();
    }

    public double[] calculateQ_min_k() {
        double[] Q_j = calculateQ_j();
        int subrangeCount = pointsCount - 1;
        double[] Q_min_k =  new double[subrangeCount];

        for(int k = 0; k<subrangeCount; k++){
            Q_min_k[k] = Math.min(Q_j[k], Q_j[k+1]);
        }
        return Q_min_k;
    }

    public double calculateQ_max() {
        double[] Q_j = calculateQ_j();
        return Arrays.stream(Q_j).max().getAsDouble();
    }

    public double[] calculateQ_max_k() {
        double[] Q_j = calculateQ_j();
        int subrangeCount = pointsCount - 1;
        double[] Q_max_k =  new double[subrangeCount];

        for(int k = 0; k<subrangeCount; k++){
            Q_max_k[k] = Math.max(Q_j[k], Q_j[k+1]);
        }
        return Q_max_k;
    }

    public double calculateTheta_D() {
        double[] MF_j = calculateMF_j();
        double MF = calculateMF();
        return Arrays.stream(MF_j)
                .map(mf_j -> Math.abs((mf_j - MF) / MF))
                .max().getAsDouble() * 100;
    }

    public double[] calculateTheta_PDz() {
        int subrangeCount = pointsCount - 1;
        double[] theta_PDz = new double[subrangeCount];
        double[] Q_j = calculateQ_j();
        for (int k = 0; k < subrangeCount; k++) {
            double Q_PDmin = Math.min(Q_j[k], Q_j[k + 1]);
            theta_PDz[k] = ZS / Q_PDmin * 100;
        }
        return theta_PDz;
    }

    public double[] calculateTheta_PDk() {
        int subrangeCount = pointsCount - 1;
        double[] theta_PDk = new double[subrangeCount];
        double[] MF_j = calculateMF_j();
        for (int k = 0; k < subrangeCount; k++) {
            theta_PDk[k] = 0.5 * Math.abs((MF_j[k] - MF_j[k + 1]) / (MF_j[k] + MF_j[k + 1])) * 100;
        }
        return theta_PDk;
    }
}
