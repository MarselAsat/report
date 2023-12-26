package com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3272.calculations;

import com.nppgks.reportingsystem.constants.MI3272Constants;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.formulas.MI3272Formulas;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class MI3272Calculator {

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
    // Значения могут быть: {нефть}, {нефтепродукт}, {смазочное масло}
    // С помощью этого параметра вычисляются beta_fluid_ij и gamma_fluid_ij
    private final String workingFluid;

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
    private final double[][] rho_TPR_ij;
    private final double[][] rho_PP_ij, t_PP_ij, P_PP_ij;

    // параметры для повтороного подсчета коэффициента преобразования ТПР
    private final double[][] rho2_TPR_ij, N2_TPR_ij_avg, t2_KP_ij_avg, P2_KP_ij_avg, t2_TPR_ij_avg, P2_TPR_ij_avg, t2_st_ij;

    // значения расхода для поверки массомера
    private final double[][] Q_ij;

    // параметры для расчета МХ массомера
    private final double[][] N_mas_ij;
    private final double[][] N_TPR_ij_zad, t_TPR_ij, P_TPR_ij;
    private final double[][] rho_BIK_ij;
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

    public MI3272Calculator(MI3272InitialData MI3272InitialData) {
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
        this.workingFluid = MI3272InitialData.getWorkingFluid();
        this.alpha_cyl_t = MI3272InitialData.getAlpha_cyl_t();
        this.alpha_cyl_t_sq = MI3272InitialData.getAlpha_cyl_t_sq();
        this.alpha_st_t = MI3272InitialData.getAlpha_st_t();
        this.V_KP_0 = MI3272InitialData.getV_KP_0();
        this.rho_TPR_ij = MI3272InitialData.getRho_TPR_ij();
        this.rho2_TPR_ij = MI3272InitialData.getRho2_TPR_ij();
        this.rho_PP_ij = MI3272InitialData.getRho_PP_ij();
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
        this.rho_BIK_ij = MI3272InitialData.getRho_BIK_ij();
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
        log.info("----- МИ3272 -----");
        MI3272FinalData mi3272FinalData = new MI3272FinalData();

        // определение коэффициента преобразования ТПР
        if (usedTPR) {
            double alpha_cyl_t = MI3272Formulas.calculateAlpha_cyl_t(this.alpha_cyl_t, alpha_cyl_t_sq);
            double[][] V_KP_pr_ij = calculateV_KP_pr_ij(alpha_cyl_t, t_KP_ij_avg, P_KP_ij_avg, t_TPR_ij_avg,
                    P_TPR_ij_avg, t_st_ij, rho_TPR_ij);
            double[][] K_TPR_ij = MI3272Formulas.calculateK_TPR_ij(N_TPR_ij_avg, V_KP_pr_ij);
            double[] Pi_j = MI3272Formulas.calculatePi_j(K_TPR_ij);
            double[] K_TPR_j = MI3272Formulas.calculateK_TPR_j(K_TPR_ij);

            double[][] V2_KP_pr_ij = calculateV_KP_pr_ij(alpha_cyl_t, t2_KP_ij_avg, P2_KP_ij_avg, t2_TPR_ij_avg, P2_TPR_ij_avg, t2_st_ij, rho2_TPR_ij);
            double[][] K2_TPR_ij = MI3272Formulas.calculateK_TPR_ij(N2_TPR_ij_avg, V2_KP_pr_ij);
            double[] K2_TPR_j = MI3272Formulas.calculateK_TPR_j(K2_TPR_ij);

            log.info("t_КП_ij(повторное измерение)={}", Arrays.deepToString(t2_KP_ij_avg));
            log.info("P_КП_ij(повторное измерение)={}", Arrays.deepToString(P2_KP_ij_avg));
            log.info("t_ТПР_ij(повторное измерение)={}", Arrays.deepToString(t2_TPR_ij_avg));
            log.info("P_ТПР_ij(повторное измерение)={}", Arrays.deepToString(P2_TPR_ij_avg));
            log.info("t_ст_ij(повторное измерение)={}", Arrays.deepToString(t2_st_ij));

            log.info("V_КП_пр_ij(повторное измерение)={}", Arrays.deepToString(V2_KP_pr_ij));
            log.info("K_ТПР_ij(повторное измерение)={}", Arrays.deepToString(K2_TPR_ij));
            log.info("K_ТПР_j(повторное измерение)={}", K2_TPR_j);

            double[] delta_k_j = MI3272Formulas.calculateDelta_K_j(K_TPR_j, K2_TPR_j);

            double[][] V_TPR_ij = MI3272Formulas.calculateV_TPR_ij(N_TPR_ij_zad, K_TPR_j);

            double[][] rho_15 = Appendix.calculateRho_15(workingFluid, rho_PP_ij, t_TPR_ij, P_PP_ij);
            double[][] beta_fluid_ij = Appendix.calculateBeta_fluid(workingFluid, rho_15, t_TPR_ij);
            double[][] gamma_fluid_ij = Appendix.calculateGamma_fluid(rho_15, t_TPR_ij);
            log.info("rho_15={}", Arrays.deepToString(rho_15));
            log.info("beta_ж={}", Arrays.deepToString(beta_fluid_ij));

            log.info("gamma_ж={}",Arrays.deepToString(gamma_fluid_ij));

            double[][] rho_PP_pr_ij = MI3272Formulas.calculateRho_PP_pr_ij(rho_BIK_ij, t_PP_ij, t_TPR_ij,
                    beta_fluid_ij, gamma_fluid_ij, P_TPR_ij, P_PP_ij);
            double[][] M_re_ij = MI3272Formulas.calculateM_re_ij(V_TPR_ij, rho_PP_pr_ij);
            double[][] M_mas_ij = MI3272Formulas.calculateM_mas_ij(N_mas_ij, KF_conf);
            double[][] MF_ij = MI3272Formulas.calculateMF_ij(M_re_ij, M_mas_ij, MF_set_range);

            mi3272FinalData.setBeta_fluid_ij(beta_fluid_ij);
            mi3272FinalData.setGamma_fluid_ij(gamma_fluid_ij);
            mi3272FinalData.setV_KP_pr_ij(V_KP_pr_ij);
            mi3272FinalData.setK_TPR_ij(K_TPR_ij);
            mi3272FinalData.setPi_j(Pi_j);
            mi3272FinalData.setK_TPR_j(K_TPR_j);
            mi3272FinalData.setK2_TPR_j(K2_TPR_j);
            mi3272FinalData.setDelta_K_j(delta_k_j);
            mi3272FinalData.setV_TPR_ij(V_TPR_ij);
            mi3272FinalData.setRho_PP_pr_ij(rho_PP_pr_ij);
            mi3272FinalData.setM_re_ij(M_re_ij);
            mi3272FinalData.setM_mas_ij(M_mas_ij);
            mi3272FinalData.setMF_ij(MF_ij);
            mi3272FinalData.setAlpha_cyl_t(alpha_cyl_t);

        } else {
            double alpha_cyl_t = MI3272Formulas.calculateAlpha_cyl_t(this.alpha_cyl_t, alpha_cyl_t_sq);
            double[][] V_KP_pr_ij = MI3272Formulas.calculateV_KP_pr_ij_Formula4(V_KP_0, alpha_cyl_t, t_KP_ij_avg,
                    alpha_st_t, t_st_ij, D, E, s, P_KP_ij_avg);

            // ТПР не используется, поэтому передаем t_KP_ij и P_KP_ij
            double[][] rho_15 = Appendix.calculateRho_15(workingFluid, rho_PP_ij, t_KP_ij_avg, P_PP_ij);
            double[][] beta_fluid_ij = Appendix.calculateBeta_fluid(workingFluid, rho_15, t_KP_ij_avg);
            double[][] gamma_fluid_ij = Appendix.calculateGamma_fluid(rho_15, t_KP_ij_avg);
            log.info("rho_15={}", Arrays.deepToString(rho_15));
            log.info("beta_ж={}", Arrays.deepToString(beta_fluid_ij));

            log.info("gamma_ж={}",Arrays.deepToString(gamma_fluid_ij));

            double[][] rho_PP_pr_ij = MI3272Formulas.calculateRho_PP_pr_ij(rho_BIK_ij, t_PP_ij, t_KP_ij_avg, beta_fluid_ij,
                    gamma_fluid_ij, P_KP_ij_avg, P_PP_ij);

            double[][] M_re_ij = MI3272Formulas.calculateM_re_ij(V_KP_pr_ij, rho_PP_pr_ij);
            double[][] M_mas_ij = MI3272Formulas.calculateM_mas_ij(N_mas_ij, KF_conf);
            mi3272FinalData.setAlpha_cyl_t(alpha_cyl_t);
            mi3272FinalData.setV_KP_pr_ij(V_KP_pr_ij);
            mi3272FinalData.setRho_PP_pr_ij(rho_PP_pr_ij);
            mi3272FinalData.setM_re_ij(M_re_ij);
            mi3272FinalData.setM_mas_ij(M_mas_ij);
            mi3272FinalData.setMF_ij(MI3272Formulas.calculateMF_ij(M_re_ij, M_mas_ij, MF_set_range));
            mi3272FinalData.setBeta_fluid_ij(beta_fluid_ij);
            mi3272FinalData.setGamma_fluid_ij(gamma_fluid_ij);
        }

        double[] Q_j_avg = MI3272Formulas.calculateQ_j_avg(Q_ij);
        mi3272FinalData.setQ_j_avg(Q_j_avg);
        if (calibrCharImpl.equals(MI3272Constants.PEP)) {
            int measureCount = mi3272FinalData.getMF_ij().length;
            // записываются в таблицу 4 - (при реализации ГХ в ПЭП)
            double[] MF_j_avg = MI3272Formulas.calculateMF_j_avg(mi3272FinalData.getMF_ij());
            double S_MF_range = MI3272Formulas.calculateS_MF_range(mi3272FinalData.getMF_ij(), MF_j_avg);
            double delta_mas_0 = MI3272Formulas.calculateDelta_mas_0(ZS, Q_j_avg);
            double MF_range = MI3272Formulas.calculateMF_range(MF_j_avg);
            double K_gr = MI3272Formulas.calculateK_gr(K_PEP_gr, MF_range);
            double t_P_n = Appendix.get_t_P_n(measureCount);
            double epsilon = MI3272Formulas.calculateEpsilon_PEP(t_P_n, S_MF_range);

            // вспомогательные
            double theta_t = MI3272Formulas.calculateTheta_t(delta_t_KP, delta_t_PP, mi3272FinalData.getBeta_fluid_ij());
            double theta_MF_range = MI3272Formulas.calculateTheta_MForKF_range(MF_j_avg, MF_range);
            log.info("theta_t={}", theta_t);
            log.info("theta_MF_диап={}", theta_MF_range);

            // записываются в таблицу 4 - (при реализации ГХ в ПЭП)
            double theta_sigma = MI3272Formulas.calculateTheta_sigma_PEP(delta_KP, delta_PP, theta_t, delta_UOI_K, theta_MF_range, delta_mas_0);

            // записываются в таблицу 3
            double Z_P = Appendix.getZ_P(theta_sigma / S_MF_range);

            // записываются в таблицу 4 - (при реализации ГХ в ПЭП)
            double delta = MI3272Formulas.calculateDelta_PEP(Z_P, theta_sigma, epsilon, S_MF_range);

            mi3272FinalData.setMForKF_j_avg(MF_j_avg);
            mi3272FinalData.setS_MForKF_range(S_MF_range);
            mi3272FinalData.setDelta_mas_0(delta_mas_0);
            mi3272FinalData.setMForKF_range(MF_range);
            mi3272FinalData.setK_gr(K_gr);
            mi3272FinalData.setT_P_n(t_P_n);
            mi3272FinalData.setEpsilon(epsilon);
            mi3272FinalData.setTheta_sigma(theta_sigma);
            mi3272FinalData.setZ_P(Z_P);
            mi3272FinalData.setDelta(delta);
        } else if (calibrCharImpl.equals(MI3272Constants.SOI_RANGE)) {
            // записывается в таблицу 2
            double[][] KF_ij = MI3272Formulas.calculateKF_ij(N_mas_ij, mi3272FinalData.getM_re_ij());

            // вспомогательные
            int measureCount = KF_ij.length;

            // записываются в таблицу 4 - (при реализации ГХ в СОИ в виде постоянного значения К-фактора)
            double[] KF_j_avg = MI3272Formulas.calculateKF_j_avg(KF_ij);
            double S_KF_range = MI3272Formulas.calculateS_KF_range(KF_ij, KF_j_avg);
            double delta_mas_0 = MI3272Formulas.calculateDelta_mas_0(ZS, Q_j_avg);
            double KF_range = MI3272Formulas.calculateKF_range(KF_j_avg);
            double theta_KF_range = MI3272Formulas.calculateTheta_MForKF_range(KF_j_avg, KF_range);

            // записываются в таблицу 3
            double t_P_n = Appendix.get_t_P_n(measureCount);

            // записываются в таблицу 4 - (при реализации ГХ в СОИ в виде постоянного значения К-фактора)
            double epsilon = MI3272Formulas.calculateEpsilon_SOI_const(t_P_n, S_KF_range);

            // вспомогательные
            double theta_t = MI3272Formulas.calculateTheta_t(delta_t_KP, delta_t_PP, mi3272FinalData.getBeta_fluid_ij());
            log.info("theta_t={}", theta_t);

            // записываются в таблицу 4 - (при реализации ГХ в СОИ в виде постоянного значения К-фактора)
            double theta_sigma = MI3272Formulas.calculateTheta_sigma_SOI_const(delta_KP, delta_PP, theta_t, delta_UOI_K, theta_KF_range, delta_mas_0);

            // записываются в таблицу 3
            double Z_P = Appendix.getZ_P(theta_sigma / S_KF_range);

            // записываются в таблицу 4 - (при реализации ГХ в СОИ в виде постоянного значения К-фактора)
            double delta = MI3272Formulas.calculateDelta_SOI_const(Z_P, theta_sigma, epsilon, S_KF_range);

            mi3272FinalData.setKF_ij(KF_ij);
            mi3272FinalData.setMForKF_j_avg(KF_j_avg);
            mi3272FinalData.setS_MForKF_range(S_KF_range);
            mi3272FinalData.setDelta_mas_0(delta_mas_0);
            mi3272FinalData.setMForKF_range(KF_range);
            mi3272FinalData.setTheta_KF_range(theta_KF_range);
            mi3272FinalData.setT_P_n(t_P_n);
            mi3272FinalData.setEpsilon(epsilon);
            mi3272FinalData.setTheta_sigma(theta_sigma);
            mi3272FinalData.setZ_P(Z_P);
            mi3272FinalData.setDelta(delta);
        } else {
            // записывается в таблицу 2
            double[][] KF_ij = MI3272Formulas.calculateKF_ij(N_mas_ij, mi3272FinalData.getM_re_ij());

            // вспомогательные
            int measureCount = KF_ij.length;

            // записываются в таблицу 4 - (при реализации ГХ в СОИ в виде кусочно-линейной аппроксимации)
            double[] KF_j_avg = MI3272Formulas.calculateKF_j_avg(KF_ij);

            double[] S_KF_k = MI3272Formulas.calculateS_KF_k(KF_ij, KF_j_avg);
            double[] Q_kmin = MI3272Formulas.calculateQ_kmin(Q_j_avg);
            double[] Q_kmax = MI3272Formulas.calculateQ_kmax(Q_j_avg);
            double[] delta_mas_0k = MI3272Formulas.calculateDelta_mas_0k(ZS, Q_kmin, Q_kmax);
            double[] theta_KF_k = MI3272Formulas.calculateTheta_KF_k(KF_j_avg);

            // записываются в таблицу 3
            double t_P_n = Appendix.get_t_P_n(measureCount * 2);

            // записываются в таблицу 4 - (при реализации ГХ в СОИ в виде кусочно-линейной аппроксимации)
            double[] epsilon_k = MI3272Formulas.calculateEpsilon_k(t_P_n, S_KF_k);

            // вспомогательные
            double theta_t = MI3272Formulas.calculateTheta_t(delta_t_KP, delta_t_PP, mi3272FinalData.getBeta_fluid_ij());
            log.info("theta_t={}", theta_t);

            // записываются в таблицу 4 - (при реализации ГХ в СОИ в виде кусочно-линейной аппроксимации)
            double[] theta_sigma_k = MI3272Formulas.calculateTheta_sigma_k(delta_KP, delta_PP, theta_t, delta_UOI_K, theta_KF_k, delta_mas_0k);

            double[] Z_P_k = MI3272Formulas.calculateZ_P_k(theta_sigma_k, S_KF_k);
            double[] delta_k = MI3272Formulas.calculateDelta_k(Z_P_k, theta_sigma_k, epsilon_k, S_KF_k);

            mi3272FinalData.setKF_ij(KF_ij);
            mi3272FinalData.setZ_P_k(Z_P_k);
            mi3272FinalData.setMForKF_j_avg(KF_j_avg);
            mi3272FinalData.setQ_kmin(Q_kmin);
            mi3272FinalData.setQ_kmax(Q_kmax);
            mi3272FinalData.setS_KF_k(S_KF_k);
            mi3272FinalData.setDelta_mas_0k(delta_mas_0k);
            mi3272FinalData.setTheta_KF_k(theta_KF_k);
            mi3272FinalData.setEpsilon_k(epsilon_k);
            mi3272FinalData.setTheta_sigma_k(theta_sigma_k);
            mi3272FinalData.setT_P_n(t_P_n);
            mi3272FinalData.setDelta_k(delta_k);
        }

        // заключение: {годен}/{не годен}
        // в качестве {рабочего и контрольно-резервного (контрольного)} / {рабочего}
        if (usedTPR && !MI3272Formulas.checkDelta_k_j(mi3272FinalData.getDelta_K_j())) {
            mi3272FinalData.setConclusion(MI3272Constants.UNFIT);
        } else {
            if (calibrCharImpl.equals(MI3272Constants.PEP) || calibrCharImpl.equals(MI3272Constants.SOI_RANGE)) {
                double delta = mi3272FinalData.getDelta();
                if (MI3272Formulas.checkDeltaAsOperating(delta)) {
                    mi3272FinalData.setUsedAs(MI3272Constants.USED_AS_OPERATING);
                    if (MI3272Formulas.checkDeltaAsControl(delta)) {
                        mi3272FinalData.setUsedAs(MI3272Constants.USED_AS_CONTROL_AND_OPERATING);
                    }
                    mi3272FinalData.setConclusion(MI3272Constants.FIT);
                }
                else {
                    mi3272FinalData.setConclusion(MI3272Constants.UNFIT);
                }

            } else {
                double[] delta_k = mi3272FinalData.getDelta_k();
                if (MI3272Formulas.checkDelta_kAsOperating(delta_k)) {
                    mi3272FinalData.setUsedAs(MI3272Constants.USED_AS_OPERATING);
                    if (MI3272Formulas.checkDelta_kAsControl(delta_k)) {
                        mi3272FinalData.setUsedAs(MI3272Constants.USED_AS_CONTROL_AND_OPERATING);
                    }
                    mi3272FinalData.setConclusion(MI3272Constants.FIT);
                }
                else{
                    mi3272FinalData.setConclusion(MI3272Constants.UNFIT);
                }
            }
        }

        return mi3272FinalData;
    }

    private double[][] calculateV_KP_pr_ij(double alpha_cyl_t, double[][] t_KP_ij_avg, double[][] P_KP_ij_avg,
                                           double[][] t_TPR_ij_avg, double[][] P_TPR_ij_avg,
                                           double[][] t_st_ij, double[][] rho_TPR_ij) {
        double[][] V_KP_pr_ij;

        // если ТПР входит в состав компакт-прувера
        if (TPRInKP) {
            V_KP_pr_ij = MI3272Formulas.calculateV_KP_pr_ij_Formula4(V_KP_0, alpha_cyl_t, t_KP_ij_avg,
                    alpha_st_t, t_st_ij, D, E, s, P_KP_ij_avg);
        } else {
            // TODO: 27.07.2023 здесь rho_PP_ij и t_PP_ij кажется нужны другие, т.к. их измерения должны быть длиной
            //  seriesCount, а не measureCount
            double[][] rho_15 = Appendix.calculateRho_15(workingFluid, rho_TPR_ij, t_KP_ij_avg, P_KP_ij_avg);
            double[][] beta_fluid_ij = Appendix.calculateBeta_fluid(workingFluid, rho_15, t_KP_ij_avg);
            double[][] gamma_fluid_ij = Appendix.calculateGamma_fluid(rho_15, t_KP_ij_avg);
            log.info("---------- Вычисление V_KP_pr_ij ---------------");
            log.info("rho_15={}", Arrays.deepToString(rho_15));
            log.info("beta_ж={}", Arrays.deepToString(beta_fluid_ij));

            log.info("gamma_ж={}",Arrays.deepToString(gamma_fluid_ij));

            V_KP_pr_ij = MI3272Formulas.calculateV_KP_pr_ij_Formula7(V_KP_0, alpha_cyl_t, t_KP_ij_avg,
                    D, E, s, P_KP_ij_avg, beta_fluid_ij, t_TPR_ij_avg, P_TPR_ij_avg, gamma_fluid_ij);
        }
        return V_KP_pr_ij;
    }

}
