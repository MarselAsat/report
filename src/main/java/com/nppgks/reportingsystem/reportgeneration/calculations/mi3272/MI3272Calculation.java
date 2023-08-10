package com.nppgks.reportingsystem.reportgeneration.calculations.mi3272;

import com.nppgks.reportingsystem.constants.MI3272Constants;
import com.nppgks.reportingsystem.exception.MissingOpcTagException;
import com.nppgks.reportingsystem.reportgeneration.calculations.CommonFunctions;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class MI3272Calculation {

    // Используется ли ТПР во время поверки
    private final boolean usedTPR;

    // Используют ТПР, входящий в состав компакт-прувера или не входящий
    private final boolean TPRInKP;

    // Реализация градуировочных характеристик:
    // {ПЭП} - реализация в ПЭП,
    // {СОИ рабочий диапазон} - реализация в СОИ в виде постоянного значения К-фактора,
    // {СОИ поддиапазон} - реализация в СОИ в виде кусочно-линейной аппроксимации (по умолчанию)
    private final String calibrCharImpl;

    // Группа, к которой принадлежит рабочая жидковть.
    // Значения могут быть: {нефть}, {бензины}, {реактивные топлива}, {нефтяные топлива}
    // С помощью этого параметра вычисляются beta_fluid_ij и gamma_fluid_ij
    private final String operatingFluid;

    // значения расхода для определения коэффициентов преобразования ТПР (таблица 2 часть I)
    // если ТПР не используется, то этот параметр не используется
    private final double[][] Q_ij_TPR;


    // Параметры ТПР. Если ТПР не используется, то и эти параметры не используются
    private final double[][] N_TPR_ij_avg, t_TPR_ij_avg, P_TPR_ij_avg;

    // параметры компакт-прувера
    private final double[][] t_KP_ij_avg, P_KP_ij_avg;

    private final double[][] t_st_ij;

    private final Double alpha_cyl_t, alpha_st_t, alpha_cyl_t_sq;
    private final double V_KP_0;
    private final double[][] ro_PP_ij, t_PP_ij, P_PP_ij;

    // параметры для повтороного подсчета коэффициента преобразования ТПР
    private final double[][] N2_TPR_ij_avg, t2_KP_ij_avg, P2_KP_ij_avg, t2_TPR_ij_avg, P2_TPR_ij_avg, t2_st_ij;

    // значения расхода для поверки массомера
    private final double[][] Q_ij;

    // параметры для расчета МХ массомера
    private final double[][] N_mas_ij;
    private final double[][] N_TPR_ij_zad, t_TPR_ij, P_TPR_ij;
    private final double[][] ro_BIK_ij;
    private final double KF_conf;
    private final double K_PEP_gr;
    private final double MF_set_range;
    private final double delta_KP, delta_PP;
    private final double delta_UOI_K;
    private final double delta_t_KP, delta_t_PP;
    private final double ZS;
    private final double D, E, s;

    // кол-во измерений для поверки массомера
    private final int measureCount;

    // кол-во серий прохода поршня для определения коэффициентов преобразования ТПР
    private final int seriesCount;
    private final int pointsCount;

    public MI3272Calculation(MI3272InitialData MI3272InitialData) {
        this.usedTPR = MI3272InitialData.isUsedTPR();
        this.TPRInKP = MI3272InitialData.isTPRInKP();
        this.calibrCharImpl = MI3272InitialData.getCalibrCharImpl();
        this.Q_ij_TPR = MI3272InitialData.getQ_ij_TPR();
        this.N_TPR_ij_avg = MI3272InitialData.getN_TPR_ij_avg();
        this.t_TPR_ij_avg = MI3272InitialData.getT_TPR_ij_avg();
        this.P_TPR_ij_avg = MI3272InitialData.getP_TPR_ij_avg();
        this.t_KP_ij_avg = MI3272InitialData.getT_KP_ij_avg();
        this.P_KP_ij_avg = MI3272InitialData.getP_KP_ij_avg();
        this.t_st_ij = MI3272InitialData.getT_st_ij();
        this.operatingFluid = MI3272InitialData.getOperatingFluid();
        this.alpha_cyl_t = MI3272InitialData.getAlpha_cyl_t();
        this.alpha_cyl_t_sq = MI3272InitialData.getAlpha_cyl_t_sq();
        this.alpha_st_t = MI3272InitialData.getAlpha_st_t();
        this.V_KP_0 = MI3272InitialData.getV_KP_0();
        this.ro_PP_ij = MI3272InitialData.getRo_PP_ij();
        this.P_PP_ij = MI3272InitialData.getP_PP_ij();
        this.N2_TPR_ij_avg = MI3272InitialData.getN2_TPR_ij_avg();
        this.t2_KP_ij_avg = MI3272InitialData.getT2_KP_ij_avg();
        this.P2_KP_ij_avg = MI3272InitialData.getP2_KP_ij_avg();
        this.t2_TPR_ij_avg = MI3272InitialData.getT2_TPR_ij_avg();
        this.P2_TPR_ij_avg = MI3272InitialData.getP2_TPR_ij_avg();
        this.t2_st_ij = MI3272InitialData.getT2_st_ij();
        this.Q_ij = MI3272InitialData.getQ_ij();
        this.N_mas_ij = MI3272InitialData.getN_mas_ij();
        this.t_PP_ij = MI3272InitialData.getT_PP_ij();
        this.N_TPR_ij_zad = MI3272InitialData.getN_TPR_ij_zad();
        this.t_TPR_ij = MI3272InitialData.getT_TPR_ij();
        this.P_TPR_ij = MI3272InitialData.getP_TPR_ij();
        this.ro_BIK_ij = MI3272InitialData.getRo_BIK_ij();
        this.KF_conf = MI3272InitialData.getKF_conf();
        this.K_PEP_gr = MI3272InitialData.getK_PEP_gr();
        this.MF_set_range = MI3272InitialData.getMF_set_range();
        this.delta_KP = MI3272InitialData.getDelta_KP();
        this.delta_PP = MI3272InitialData.getDelta_PP();
        this.delta_UOI_K = MI3272InitialData.getDelta_UOI_K();
        this.delta_t_KP = MI3272InitialData.getDelta_t_KP();
        this.delta_t_PP = MI3272InitialData.getDelta_t_PP();
        this.ZS = MI3272InitialData.getZS();
        this.D = MI3272InitialData.getD();
        this.E = MI3272InitialData.getE();
        this.s = MI3272InitialData.getS();
        this.measureCount = MI3272InitialData.getMeasureCount();
        this.seriesCount = MI3272InitialData.getSeriesCount();
        this.pointsCount = MI3272InitialData.getPointsCount();
    }

    public MI3272FinalData calculate() {
        MI3272FinalData MI3272FinalData = new MI3272FinalData();
        double[][] beta_fluid_ij = Appendix.calculateBeta_fluid(operatingFluid, ro_PP_ij, t_PP_ij);
        double[][] gamma_fluid_ij = Appendix.calculateGamma_fluid(ro_PP_ij, t_PP_ij);

        MI3272FinalData.setBeta_fluid_ij(beta_fluid_ij);
        MI3272FinalData.setGamma_fluid_ij(gamma_fluid_ij);
        // определение коэффициента преобразования ТПР
        if (usedTPR) {
            double alpha_cyl_t = calculateAlpha_cyl_t();
            double[][] V_KP_pr_ij = calculateV_KP_pr_ij(alpha_cyl_t, t_KP_ij_avg, P_KP_ij_avg, t_TPR_ij_avg,
                    P_TPR_ij_avg, t_st_ij);
            double[][] K_TPR_ij = calculateK_TPR_ij(N_TPR_ij_avg, V_KP_pr_ij);
            double[] Pi_j = calculatePi_j(K_TPR_ij);
            double[] K_TPR_j = calculateK_TPR_j(K_TPR_ij);

            double[][] V2_KP_pr_ij = calculateV_KP_pr_ij(alpha_cyl_t, t2_KP_ij_avg, P2_KP_ij_avg, t2_TPR_ij_avg, P2_TPR_ij_avg, t2_st_ij);
            double[][] K2_TPR_ij = calculateK_TPR_ij(N2_TPR_ij_avg, V2_KP_pr_ij);
            double[] K2_TPR_j = calculateK_TPR_j(K2_TPR_ij);
            double[] delta_k_j = calculateDelta_K_j(K_TPR_j, K2_TPR_j);

            double[][] V_TPR_ij = calculateV_TPR_ij(N_TPR_ij_zad, K_TPR_j);

            double[][] ro_PP_pr_ij = calculateRo_PP_pr_ij(ro_BIK_ij, t_PP_ij, t_TPR_ij,
                    beta_fluid_ij, gamma_fluid_ij, P_TPR_ij, P_PP_ij);
            double[][] M_re_ij = calculateM_re_ij(V_TPR_ij, ro_PP_pr_ij);
            double[][] M_mas_ij = calculateM_mas_ij(N_mas_ij, KF_conf);
            double[][] MF_ij = calculateMF_ij(M_re_ij, M_mas_ij, MF_set_range);

            MI3272FinalData.setV_KP_pr_ij(V_KP_pr_ij);
            MI3272FinalData.setK_TPR_ij(K_TPR_ij);
            MI3272FinalData.setPi_j(Pi_j);
            MI3272FinalData.setK_TPR_j(K_TPR_j);
            MI3272FinalData.setK2_TPR_j(K2_TPR_j);
            MI3272FinalData.setDelta_K_j(delta_k_j);
            MI3272FinalData.setV_TPR_ij(V_TPR_ij);
            MI3272FinalData.setRo_PP_pr_ij(ro_PP_pr_ij);
            MI3272FinalData.setM_re_ij(M_re_ij);
            MI3272FinalData.setM_mas_ij(M_mas_ij);
            MI3272FinalData.setMF_ij(MF_ij);
            MI3272FinalData.setAlpha_cyl_t(alpha_cyl_t);

        } else {
            double alpha_cyl_t = calculateAlpha_cyl_t();
            double[][] V_KP_pr_ij = calculateV_KP_pr_ij_Formula4(V_KP_0, alpha_cyl_t, t_KP_ij_avg,
                    alpha_st_t, t_st_ij, D, E, s, P_KP_ij_avg);

            // ТПР не используется, поэтому передаем t_KP_ij и P_KP_ij
            double[][] ro_PP_pr_ij = calculateRo_PP_pr_ij(ro_BIK_ij, t_PP_ij, t_KP_ij_avg, beta_fluid_ij,
                    gamma_fluid_ij, P_KP_ij_avg, P_PP_ij);

            double[][] M_re_ij = calculateM_re_ij(V_KP_pr_ij, ro_PP_pr_ij);
            double[][] M_mas_ij = calculateM_mas_ij(N_mas_ij, KF_conf);
            MI3272FinalData.setAlpha_cyl_t(alpha_cyl_t);
            MI3272FinalData.setV_KP_pr_ij(V_KP_pr_ij);
            MI3272FinalData.setRo_PP_pr_ij(ro_PP_pr_ij);
            MI3272FinalData.setM_re_ij(M_re_ij);
            MI3272FinalData.setM_mas_ij(M_mas_ij);
            MI3272FinalData.setMF_ij(calculateMF_ij(M_re_ij, M_mas_ij, MF_set_range));
        }

        double[] Q_j_avg = calculateQ_j_avg(Q_ij);
        MI3272FinalData.setQ_j_avg(Q_j_avg);
        if (calibrCharImpl.equals(MI3272Constants.PEP)) {
            int measureCount = MI3272FinalData.getMF_ij().length;
            // записываются в таблицу 4 - (при реализации ГХ в ПЭП)
            double[] MF_j_avg = calculateMF_j_avg(MI3272FinalData.getMF_ij());
            double S_MF_range = calculateS_MF_range(MI3272FinalData.getMF_ij(), MF_j_avg);
            double delta_mas_0 = calculateDelta_mas_0(ZS, Q_j_avg);
            double MF_range = calculateMF_range(MF_j_avg);
            double K_gr = calculateK_gr(K_PEP_gr, MF_range);
            double t_P_n = Appendix.get_t_P_n(measureCount);
            double epsilon = calculateEpsilon_PEP(t_P_n, S_MF_range);

            // вспомогательные
            double theta_t = calculateTheta_t(delta_t_KP, delta_t_PP);
            double theta_MF_range = calculateTheta_MForKF_range(MF_j_avg, MF_range);

            // записываются в таблицу 4 - (при реализации ГХ в ПЭП)
            double theta_sigma = calculateTheta_sigma_PEP(delta_KP, delta_PP, theta_t, delta_UOI_K, theta_MF_range, delta_mas_0);

            // записываются в таблицу 3
            double Z_P = Appendix.getZ_P(theta_sigma / S_MF_range);

            // записываются в таблицу 4 - (при реализации ГХ в ПЭП)
            double delta = calculateDelta_PEP(Z_P, theta_sigma, epsilon, S_MF_range);

            MI3272FinalData.setMForKF_j_avg(MF_j_avg);
            MI3272FinalData.setS_MForKF_range(S_MF_range);
            MI3272FinalData.setDelta_mas_0(delta_mas_0);
            MI3272FinalData.setMForKF_range(MF_range);
            MI3272FinalData.setK_gr(K_gr);
            MI3272FinalData.setT_P_n(t_P_n);
            MI3272FinalData.setEpsilon(epsilon);
            MI3272FinalData.setTheta_sigma(theta_sigma);
            MI3272FinalData.setZ_P(Z_P);
            MI3272FinalData.setDelta(delta);
        } else if (calibrCharImpl.equals(MI3272Constants.SOI_RANGE)) {
            // записывается в таблицу 2
            double[][] KF_ij = calculateKF_ij(N_mas_ij, MI3272FinalData.getM_re_ij());

            // вспомогательные
            int measureCount = KF_ij.length;

            // записываются в таблицу 4 - (при реализации ГХ в СОИ в виде постоянного значения К-фактора)
            double[] KF_j_avg = calculateKF_j_avg(KF_ij);
            double S_KF_range = calculateS_KF_range(KF_ij, KF_j_avg);
            double delta_mas_0 = calculateDelta_mas_0(ZS, Q_j_avg);
            double KF_range = calculateKF_range(KF_j_avg);
            double theta_KF_range = calculateTheta_MForKF_range(KF_j_avg, KF_range);

            // записываются в таблицу 3
            double t_P_n = Appendix.get_t_P_n(measureCount);

            // записываются в таблицу 4 - (при реализации ГХ в СОИ в виде постоянного значения К-фактора)
            double epsilon = calculateEpsilon_SOI_const(t_P_n, S_KF_range);

            // вспомогательные
            double theta_t = calculateTheta_t(delta_t_KP, delta_t_PP);

            // записываются в таблицу 4 - (при реализации ГХ в СОИ в виде постоянного значения К-фактора)
            double theta_sigma = calculateTheta_sigma_SOI_const(delta_KP, delta_PP, theta_t, delta_UOI_K, theta_KF_range, delta_mas_0);

            // записываются в таблицу 3
            double Z_P = Appendix.getZ_P(theta_sigma / S_KF_range);

            // записываются в таблицу 4 - (при реализации ГХ в СОИ в виде постоянного значения К-фактора)
            double delta = calculateDelta_SOI_const(Z_P, theta_sigma, epsilon, S_KF_range);

            MI3272FinalData.setKF_ij(KF_ij);
            MI3272FinalData.setMForKF_j_avg(KF_j_avg);
            MI3272FinalData.setS_MForKF_range(S_KF_range);
            MI3272FinalData.setDelta_mas_0(delta_mas_0);
            MI3272FinalData.setMForKF_range(KF_range);
            MI3272FinalData.setTheta_KF_range(theta_KF_range);
            MI3272FinalData.setT_P_n(t_P_n);
            MI3272FinalData.setEpsilon(epsilon);
            MI3272FinalData.setTheta_sigma(theta_sigma);
            MI3272FinalData.setZ_P(Z_P);
            MI3272FinalData.setDelta(delta);
        } else {
            // записывается в таблицу 2
            double[][] KF_ij = calculateKF_ij(N_mas_ij, MI3272FinalData.getM_re_ij());

            // вспомогательные
            int measureCount = KF_ij.length;

            // записываются в таблицу 4 - (при реализации ГХ в СОИ в виде кусочно-линейной аппроксимации)
            double[] KF_j_avg = calculateKF_j_avg(KF_ij);

            double[] S_KF_k = calculateS_KF_k(KF_ij, KF_j_avg);
            double[] Q_kmin = calculateQ_kmin(Q_j_avg);
            double[] Q_kmax = calculateQ_kmax(Q_j_avg);
            double[] delta_mas_0k = calculateDelta_mas_0k(ZS, Q_kmin, Q_kmax);
            double[] theta_KF_k = calculateTheta_KF_k(KF_j_avg);

            // записываются в таблицу 3
            double t_P_n = Appendix.get_t_P_n(measureCount * 2);

            // записываются в таблицу 4 - (при реализации ГХ в СОИ в виде кусочно-линейной аппроксимации)
            double[] epsilon_k = calculateEpsilon_k(t_P_n, S_KF_k);

            // вспомогательные
            double theta_t = calculateTheta_t(delta_t_KP, delta_t_PP);

            // записываются в таблицу 4 - (при реализации ГХ в СОИ в виде кусочно-линейной аппроксимации)
            double[] theta_sigma_k = calculateTheta_sigma_k(delta_KP, delta_PP, theta_t, delta_UOI_K, theta_KF_k, delta_mas_0k);

            double[] Z_P_k = calculateZ_P_k(theta_sigma_k, S_KF_k);
            double[] delta_k = calculateDelta_k(Z_P_k, theta_sigma_k, epsilon_k, S_KF_k);

            MI3272FinalData.setKF_ij(KF_ij);
            MI3272FinalData.setZ_P_k(Z_P_k);
            MI3272FinalData.setMForKF_j_avg(KF_j_avg);
            MI3272FinalData.setQ_kmin(Q_kmin);
            MI3272FinalData.setQ_kmax(Q_kmax);
            MI3272FinalData.setS_KF_k(S_KF_k);
            MI3272FinalData.setDelta_mas_0k(delta_mas_0k);
            MI3272FinalData.setTheta_KF_k(theta_KF_k);
            MI3272FinalData.setEpsilon_k(epsilon_k);
            MI3272FinalData.setTheta_sigma_k(theta_sigma_k);
            MI3272FinalData.setT_P_n(t_P_n);
            MI3272FinalData.setDelta_k(delta_k);
        }

        // заключение: {годен}/{не годен}
        // в качестве {рабочего и контрольно-резервного (контрольного)} / {рабочего}
        if (usedTPR && !checkDelta_k_j(MI3272FinalData.getDelta_K_j())) {
            MI3272FinalData.setConclusion(MI3272Constants.UNFIT);
        } else {
            if (calibrCharImpl.equals(MI3272Constants.PEP) || calibrCharImpl.equals(MI3272Constants.SOI_RANGE)) {
                double delta = MI3272FinalData.getDelta();
                if (checkDeltaAsOperating(delta)) {
                    MI3272FinalData.setUsedAs(MI3272Constants.USED_AS_OPERATING);
                    if (checkDeltaAsControl(delta)) {
                        MI3272FinalData.setUsedAs(MI3272Constants.USED_AS_CONTROL_AND_OPERATING);
                    }
                    MI3272FinalData.setConclusion(MI3272Constants.FIT);
                }
                MI3272FinalData.setConclusion(MI3272Constants.UNFIT);
            } else {
                double[] delta_k = MI3272FinalData.getDelta_k();
                if (checkDelta_kAsOperating(delta_k)) {
                    MI3272FinalData.setUsedAs(MI3272Constants.USED_AS_OPERATING);
                    if (checkDelta_kAsControl(delta_k)) {
                        MI3272FinalData.setUsedAs(MI3272Constants.USED_AS_CONTROL_AND_OPERATING);
                    }
                    MI3272FinalData.setConclusion(MI3272Constants.FIT);
                }
                MI3272FinalData.setConclusion(MI3272Constants.UNFIT);
            }
        }

        return MI3272FinalData;
    }

    private double[][] calculateV_KP_pr_ij(double alpha_cyl_t, double[][] t_KP_ij_avg, double[][] P_KP_ij_avg,
                                           double[][] t_TPR_ij_avg, double[][] P_TPR_ij_avg,
                                           double[][] t_st_ij) {
        double[][] V_KP_pr_ij;

        // если ТПР входит в состав компакт-прувера
        if (TPRInKP) {
            V_KP_pr_ij = calculateV_KP_pr_ij_Formula4(V_KP_0, alpha_cyl_t, t_KP_ij_avg,
                    alpha_st_t, t_st_ij, D, E, s, P_KP_ij_avg);
        } else {
            // TODO: 27.07.2023 здесь ro_PP_ij и t_PP_ij кажется нужны другие, т.к. их измерения должны быть длиной
            //  seriesCount, а не measureCount
            double[][] beta_fluid_ij = Appendix.calculateBeta_fluid(operatingFluid, ro_PP_ij, t_PP_ij);
            double[][] gamma_fluid_ij = Appendix.calculateGamma_fluid(ro_PP_ij, t_PP_ij);
            V_KP_pr_ij = calculateV_KP_pr_ij_Formula7(V_KP_0, alpha_cyl_t, t_KP_ij_avg,
                    D, E, s, P_KP_ij_avg, beta_fluid_ij, t_TPR_ij_avg, P_TPR_ij_avg, gamma_fluid_ij);
        }
        return V_KP_pr_ij;
    }

    // формула 6
    public double[][] calculateK_TPR_ij(double[][] N_TPR_ij_avg, double[][] V_KP_pr_ij) {
        int measureCount = V_KP_pr_ij.length;
        double[][] K_TPR_ij = new double[measureCount][pointsCount];
        for (int i = 0; i < measureCount; i++) {
            for (int j = 0; j < pointsCount; j++) {
                K_TPR_ij[i][j] = N_TPR_ij_avg[i][j] / V_KP_pr_ij[i][j];
            }
        }

        return K_TPR_ij;
    }

    public double[][] calculateV_KP_pr_ij_Formula4(double V_KP_0, double alpha_cyl_t,
                                                   double[][] t_KP_ij, double alpha_st_t,
                                                   double[][] t_st_ij, double D, double E,
                                                   double s, double[][] P_KP_ij) {
        int measureCount = t_KP_ij.length;
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

    public double[][] calculateV_KP_pr_ij_Formula7(double V_KP_0, double alpha_cyl_t, double[][] t_KP_ij_avg,
                                                   double D, double E, double s, double[][] P_KP_ij_avg,
                                                   double[][] beta_fluid_ij, double[][] t_TPR_ij_avg,
                                                   double[][] P_TPR_ij_avg, double[][] gamma_fluid_ij) {
        int measureCount = t_KP_ij_avg.length;
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

    public double calculateAlpha_cyl_t() {
        if (alpha_cyl_t != null) {
            return alpha_cyl_t;
        } else if (alpha_cyl_t_sq != null) {
            return 0.5 * alpha_cyl_t_sq;
        } else {
            throw new MissingOpcTagException("В качестве входного параметра поверки необходимо значение тега alpha_cyl_t, либо alpha_cyl_t_sq, которое будет использоваться для расчета alpha_cyl_t");
        }
    }

    // формула 8
    public double[] calculatePi_j(double[][] K_TPR_ij) {
        int measureCount = K_TPR_ij.length;
        double[] Pi_j = new double[pointsCount];
        double[] K_TPR_j_max = new double[pointsCount];
        double[] K_TPR_j_min = new double[pointsCount];
        for (int j = 0; j < pointsCount; j++) {
            double max = K_TPR_ij[0][j];
            double min = K_TPR_ij[0][j];
            for (int i = 0; i < measureCount; i++) {
                if (K_TPR_ij[i][j] > max) {
                    max = K_TPR_ij[i][j];
                }
                if (K_TPR_ij[i][j] < min) {
                    min = K_TPR_ij[i][j];
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
    public double[] calculateK_TPR_j(double[][] K_TPR_ij) {
        int measureCount = K_TPR_ij.length;
        double[] K_TPR_j = new double[pointsCount];
        for (int j = 0; j < pointsCount; j++) {
            double sum = 0;
            for (int i = 0; i < measureCount; i++) {
                sum = sum + K_TPR_ij[i][j];
            }
            K_TPR_j[j] = sum / measureCount;
        }
        return K_TPR_j;
    }

    // формула 10
    public double[] calculateDelta_K_j(double[] K_TPR_j, double[] K_2TPR_j) {
        double[] delta_K_j = new double[pointsCount];
        for (int j = 0; j < pointsCount; j++) {
            delta_K_j[j] = (K_2TPR_j[j] - K_TPR_j[j]) / (K_TPR_j[j]) * 100;
        }
        return delta_K_j;
    }

    // пункт 8.3.2.10
    public double[][] calculateV_TPR_ij(double[][] N_TPR_ij, double[] K_TPR_j) {
        int measureCount = N_TPR_ij.length;
        double[][] V_TPR_ij = new double[measureCount][pointsCount];
        for (int i = 0; i < measureCount; i++) {
            for (int j = 0; j < pointsCount; j++) {
                V_TPR_ij[i][j] = N_TPR_ij[i][j] / K_TPR_j[j];
            }
        }
        return V_TPR_ij;
    }

    // формулы 11а, 11б, 12а, 12б
    public double[][] calculateM_re_ij(double[][] V_ij, double[][] ro_PP_ij) {
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
        double[][] M_re_ij = new double[measureCount][pointsCount];
        for (int i = 0; i < measureCount; i++) {
            for (int j = 0; j < pointsCount; j++) {
                M_re_ij[i][j] = V_ij[i][j] * ro_PP_ij[i][j] * 0.001;
            }
        }
        return M_re_ij;
    }

    // формулы 13а, 13б
    private double[][] calculateRo_PP_pr_ij(double[][] ro_BIK_ij, double[][] t_PP_ij,
                                            double[][] t_ij, double[][] beta_fluid_ij,
                                            double[][] gamma_fluid_ij, double[][] P_ij,
                                            double[][] P_PP_ij) {
        /*
        Здесь в качестве аргмуентов P_ij и t_ij могут быть переданы
        либо P_TPR_ij и t_TPR_ij (используется ТПР) либо P_KP_ij и t_KP_ij (не используется ТПР)
        */
        int measureCount = ro_BIK_ij.length;
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
    public double[][] calculateM_mas_ij(double[][] N_mas_ij, double KF_conf) {
        int measureCount = N_mas_ij.length;
        double[][] M_mas_ij = new double[measureCount][pointsCount];
        for (int i = 0; i < measureCount; i++) {
            for (int j = 0; j < pointsCount; j++) {
                M_mas_ij[i][j] = N_mas_ij[i][j] / KF_conf;
            }
        }
        return M_mas_ij;
    }

    // формула 15
    public double[][] calculateMF_ij(double[][] M_re_ij, double[][] M_mas_ij, double MF_set_range) {
        int measureCount = M_re_ij.length;
        double[][] MF_ij = new double[measureCount][pointsCount];
        for (int i = 0; i < measureCount; i++) {
            for (int j = 0; j < pointsCount; j++) {
                MF_ij[i][j] = M_re_ij[i][j] * MF_set_range / M_mas_ij[i][j];
            }
        }
        return MF_ij;
    }

    // формула 16
    public double[] calculateMF_j_avg(double[][] MF_ij) {
        return CommonFunctions.getAverageForEachColumn(MF_ij);
    }

    // формула 17
    public double calculateS_MF_range(double[][] MF_ij, double[] MF_j_avg) {
        int measureCount = MF_ij.length;
        int sigma_n_j = measureCount * pointsCount;
        double sum = 0;
        for (int j = 0; j < pointsCount; j++) {
            double sum1 = 0;
            for (int i = 0; i < measureCount; i++) {
                sum1 += Math.pow((MF_ij[i][j] - MF_j_avg[j]) / MF_j_avg[j], 2);
            }
            sum += sum1;
        }
        double S_MF_range = Math.sqrt(sum / (sigma_n_j - 1)) * 100;
        return S_MF_range;
    }

    // формула 18
    public double calculateMF_range(double[] MF_j_avg) {
        double sum = 0;
        for (int j = 0; j < pointsCount; j++) {
            sum += MF_j_avg[j];
        }
        return sum / pointsCount;
    }

    // формула 19
    public double calculateK_gr(double K_PEP_gr, double MF_range) {
        return K_PEP_gr * MF_range;
    }

    // формула 20
    public double[][] calculateKF_ij(double[][] N_mas_ij, double[][] M_re_ij) {
        return CommonFunctions.getDivisionOfTwoArrays(N_mas_ij, M_re_ij);
    }

    // формула 21
    public double[] calculateKF_j_avg(double[][] KF_ij) {
        return CommonFunctions.getAverageForEachColumn(KF_ij);
    }

    // формула 22а
    public double calculateS_KF_range(double[][] KF_ij, double[] KF_j_avg) {
        int measureCount = KF_ij.length;
        double sum = 0;
        double sigma_n_j = pointsCount * measureCount;
        for (int j = 0; j < pointsCount; j++) {
            double sum1 = 0;
            for (int i = 0; i < measureCount; i++) {
                sum1 += Math.pow((KF_ij[i][j] - KF_j_avg[j]) / KF_j_avg[j], 2);
            }
            sum += sum1;
        }
        return Math.sqrt(sum / sigma_n_j) * 100;
    }

    // формула 22б
    public double[] calculateS_KF_k(double[][] KF_ij, double[] KF_j_avg) {
        int measureCount = KF_ij.length;
        double[] S_KF_k = new double[pointsCount - 1];
        double sigma_n_j = measureCount * 2;
        for (int k = 0; k < pointsCount - 1; k++) {
            double sum = 0;
            for (int j = k; j <= k + 1; j++) {
                double sum1 = 0;
                for (int i = 0; i < measureCount; i++) {
                    sum1 += Math.pow((KF_ij[i][j] - KF_j_avg[j]) / KF_j_avg[j], 2);
                }
                sum += sum1;
            }
            S_KF_k[k] = Math.sqrt(sum / sigma_n_j) * 100;
        }
        return S_KF_k;
    }

    // формула 23
    public double calculateKF_range(double[] KF_j_avg) {
        double sum = 0;
        for (int j = 0; j < pointsCount; j++) {
            sum += KF_j_avg[j];
        }
        return sum / pointsCount;
    }

    // формула 24
    public double calculateEpsilon_PEP(double t_P_n, double S_MF_range) {
        return t_P_n * S_MF_range;
    }

    // формула 25
    public double calculateTheta_sigma_PEP(double delta_KP, double delta_PP,
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
    private double calculateDelta_mas_0(double ZS, double[] Q_j_avg) {
        double Q_max = Arrays.stream(Q_j_avg).max().getAsDouble();
        double Q_min = Arrays.stream(Q_j_avg).min().getAsDouble();
        return 2 * ZS / (Q_min + Q_max) * 100;
    }

    private double[] calculateQ_j_avg(double[][] Q_ij) {
        return CommonFunctions.getAverageForEachColumn(Q_ij);
    }

    // формула 27
    private double calculateTheta_MForKF_range(double[] MForK_j_avg, double MForK_range) {
        double max = 0;
        for (int j = 0; j < pointsCount; j++) {
            max = Math.max(max, Math.abs((MForK_j_avg[j] - MForK_range) / MForK_range));
        }
        return max * 100;
    }

    // формула 26
    private double calculateTheta_t(double delta_t_KP, double delta_t_PP) {
        double betta_fluid_max = Appendix.getBetta_fluid_max();
        return betta_fluid_max * Math.sqrt(Math.pow(delta_t_KP, 2) + Math.pow(delta_t_PP, 2)) * 100;
    }

    // формула 29
    public double calculateDelta_PEP(double Z_P, double theta_sigma,
                                     double epsilon, double S_MF_range) {
        double ratio = theta_sigma / S_MF_range;
        double delta = -1;
        if (ratio >= 0.8 && ratio <= 8) {
            delta = Z_P * (theta_sigma + epsilon);
        } else if (ratio > 0.8) {
            delta = theta_sigma;
        }
        return delta;
    }

    // формула 30
    public double calculateEpsilon_SOI_const(double t_P_n, double S_KF_range) {
        return t_P_n * S_KF_range;
    }

    // формула 31
    public double calculateTheta_sigma_SOI_const(double delta_KP, double delta_PP, double theta_t,
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
    public double calculateDelta_SOI_const(double Z_P, double theta_sigma, double epsilon,
                                           double S_KF_range) {
        double ratio = theta_sigma / S_KF_range;
        double delta = -1;
        if (ratio >= 0.8 && ratio <= 8) {
            delta = Z_P * (theta_sigma + epsilon);
        } else if (ratio > 0.8) {
            delta = theta_sigma;
        }
        return delta;
    }

    // формула 34
    public double[] calculateEpsilon_k(double t_P_n, double[] S_KF_k) {
        double[] epsilon_k = new double[S_KF_k.length];
        for (int k = 0; k < S_KF_k.length; k++) {
            epsilon_k[k] = t_P_n * S_KF_k[k];
        }
        return epsilon_k;
    }

    // формула 35
    public double[] calculateTheta_sigma_k(double delta_KP, double delta_PP, double theta_t,
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
    private double[] calculateDelta_mas_0k(double ZS, double[] Q_kmin, double[] Q_kmax) {
        int length = pointsCount - 1;
        double[] delta_mas_0k = new double[length];
        for (int k = 0; k < length; k++) {
            delta_mas_0k[k] = 2 * ZS / (Q_kmin[k] + Q_kmax[k]) * 100;
        }

        return delta_mas_0k;
    }

    private double[] calculateQ_kmin(double[] Q_j_avg) {
        int length = pointsCount - 1;
        double[] Q_kmin = new double[length];
        for (int k = 0; k < length; k++) {
            Q_kmin[k] = Math.min(Q_j_avg[k], Q_j_avg[k + 1]);
        }
        return Q_kmin;
    }

    private double[] calculateQ_kmax(double[] Q_j_avg) {
        int length = pointsCount - 1;
        double[] Q_kmax = new double[length];
        for (int k = 0; k < length; k++) {
            Q_kmax[k] = Math.max(Q_j_avg[k], Q_j_avg[k + 1]);
        }
        return Q_kmax;
    }

    // формула 36
    private double[] calculateTheta_KF_k(double[] KF_j_avg) {
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
    public double[] calculateDelta_k(double[] Z_P_k, double[] theta_sigma_k,
                                     double[] epsilon_k, double[] S_KF_k) {
        int length = pointsCount - 1;
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

    public double[] calculateZ_P_k(double[] theta_sigma_k, double[] S_KF_k) {
        int len = theta_sigma_k.length;
        double[] Z_P_k = new double[len];
        for (int k = 0; k < len; k++) {
            double ratio = theta_sigma_k[k] / S_KF_k[k];
            Z_P_k[k] = Appendix.getZ_P(ratio);
        }
        return Z_P_k;
    }

    private boolean checkDelta_k_j(double[] delta_k_j) {
        for (double el : delta_k_j) {
            if (el > 0.02) return false;
        }
        return true;
    }


    public boolean checkDeltaAsControl(double delta) {
        return Math.abs(delta) <= 0.2;
    }

    public boolean checkDeltaAsOperating(double delta) {
        return Math.abs(delta) <= 0.25;
    }

    private boolean checkDelta_kAsControl(double[] delta_k) {
        for (double elem : delta_k) {
            if (Math.abs(elem) > 0.2) return false;
        }
        return true;
    }

    private boolean checkDelta_kAsOperating(double[] delta_k) {
        for (double elem : delta_k) {
            if (Math.abs(elem) > 0.25) return false;
        }
        return true;
    }

}
