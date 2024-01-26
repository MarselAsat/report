package com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3272.calculations.withtpr;

import com.nppgks.reportingsystem.constants.MI3272Constants;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.formulas.MI3272Formulas;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3272.calculations.Appendix;
import com.nppgks.reportingsystem.util.TableDisplay;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class MI3272WithTprCalculator {

    private Double alpha_cyl_t;
    private Double alpha_cyl_t_sq;

    // Параметры ТПР. Если ТПР не используется, то и эти параметры не используются
    private double[][] N_TPR_ij_avg, t_TPR_ij_avg, P_TPR_ij_avg;

    // параметры компакт-прувера
    private double[][] t_KP_ij_avg, P_KP_ij_avg;
    private double[][] rho_TPR_ij; // используется для расчета beta и gamma в формуле (7)
    private double[][] t_st_ij;
    private double[][] t2_KP_ij_avg;
    private double[][] P2_KP_ij_avg;
    private double[][] t2_TPR_ij_avg;
    private double[][] P2_TPR_ij_avg;
    private double[][] t2_st_ij;
    private double[][] rho2_TPR_ij;
    private double[][] N2_TPR_ij_avg;

    // Используется ТПР, входящий в состав компакт-прувера или не входящий
    private boolean TPRInKP;

    // Используется ПП, входящий в состав компакт-прувера
    private boolean PPInKP;

    // Группа, к которой принадлежит рабочая жидкость.
    // Значения могут быть: "нефть", "нефтепродукт", "смазочное масло"
    // С помощью этого параметра вычисляются beta_fluid_ij и gamma_fluid_ij
    private String workingFluid;

    // Реализация градуировочных характеристик:
    // {ПЭП} - реализация в ПЭП,
    // {СОИ рабочий диапазон} - реализация в СОИ в виде постоянного значения К-фактора,
    // {СОИ поддиапазон} - реализация в СОИ в виде кусочно-линейной аппроксимации (по умолчанию)
    private String calibrCharImpl;

    // доп параметры для вычисления Q_ij
    private double[][] T_ij_avg, rho_BIK_ij_avg, t_PP_ij_avg, P_PP_ij_avg, rho_PP_ij_avg;

    private double[][] T_ij;
    private double[] K_TPR_j;
    private Double alpha_st_t;
    private double V_KP_0;

    // параметры для расчета МХ массомера
    private double[][] N_mas_ij;
    private double[][] rho_PP_ij, t_PP_ij, P_PP_ij;
    private double[][] N_TPR_ij_zad, t_TPR_ij, P_TPR_ij;
    private double[][] rho_BIK_ij;
    private double[][] t_KP_ij, P_KP_ij;
    double[][] W_w_TPR_ij;
    double[][] W_xc_TPR_ij;
    double[][] W_w_ij;
    double[][] W_xc_ij;
    private double KF_conf;
    private double K_PEP_gr;
    private double MF_set_range;
    private double delta_KP, delta_PP;
    private double delta_UOI_K;
    private double delta_t_KP, delta_t_PP;
    private double ZS;
    private double D, E, s;

    public void initTprCoeffData(TprCoeffInitData tprCoeffInitData) {
        this.workingFluid = tprCoeffInitData.getWorkingFluid();
        this.TPRInKP = tprCoeffInitData.isTPRInKP();
        this.PPInKP = tprCoeffInitData.isPPInKP();
        this.alpha_st_t = tprCoeffInitData.getAlpha_st_t();
        this.alpha_cyl_t = tprCoeffInitData.getAlpha_cyl_t();
        this.alpha_cyl_t_sq = tprCoeffInitData.getAlpha_cyl_t_sq();
        this.t_KP_ij_avg = tprCoeffInitData.getT_KP_ij_avg();
        this.P_KP_ij_avg = tprCoeffInitData.getP_KP_ij_avg();
        this.t_TPR_ij_avg = tprCoeffInitData.getT_TPR_ij_avg();
        this.P_TPR_ij_avg = tprCoeffInitData.getP_TPR_ij_avg();
        this.t_st_ij = tprCoeffInitData.getT_st_ij();
        this.rho_TPR_ij = tprCoeffInitData.getRho_TPR_ij();
        this.N_TPR_ij_avg = tprCoeffInitData.getN_TPR_ij_avg();
        this.V_KP_0 = tprCoeffInitData.getV_KP_0();
        this.D = tprCoeffInitData.getD();
        this.E = tprCoeffInitData.getE();
        this.s = tprCoeffInitData.getS();
    }

    public void initRestData(MI3272TprInitData MI3272TprInitData) {
        this.calibrCharImpl = MI3272TprInitData.getCalibrCharImpl();
        this.T_ij = MI3272TprInitData.getT_ij();
        this.N2_TPR_ij_avg = MI3272TprInitData.getN_TPR_ij_avg();
        this.t2_TPR_ij_avg = MI3272TprInitData.getT_TPR_ij_avg();
        this.P2_TPR_ij_avg = MI3272TprInitData.getP_TPR_ij_avg();
        this.t2_KP_ij_avg = MI3272TprInitData.getT_KP_ij_avg();
        this.P2_KP_ij_avg = MI3272TprInitData.getP_KP_ij_avg();
        this.t2_st_ij = MI3272TprInitData.getT_st_ij();
        this.rho2_TPR_ij = MI3272TprInitData.getRho_TPR_ij();
        this.T_ij_avg = MI3272TprInitData.getT_ij_avg();
        this.rho_BIK_ij_avg = MI3272TprInitData.getRho_BIK_ij_avg();
        this.t_PP_ij_avg = MI3272TprInitData.getT_PP_ij_avg();
        this.P_PP_ij_avg = MI3272TprInitData.getP_PP_ij_avg();
        this.rho_PP_ij_avg = MI3272TprInitData.getRho_PP_ij_avg();
        this.rho_PP_ij = MI3272TprInitData.getRho_PP_ij();
        this.P_PP_ij = MI3272TprInitData.getP_PP_ij();
        this.N_mas_ij = MI3272TprInitData.getN_mas_ij();
        this.t_PP_ij = MI3272TprInitData.getT_PP_ij();
        this.N_TPR_ij_zad = MI3272TprInitData.getN_TPR_ij_zad();
        this.t_TPR_ij = MI3272TprInitData.getT_TPR_ij();
        this.P_TPR_ij = MI3272TprInitData.getP_TPR_ij();
        this.rho_BIK_ij = MI3272TprInitData.getRho_BIK_ij();
        this.t_KP_ij = MI3272TprInitData.getT_KP_ij();
        this.P_KP_ij = MI3272TprInitData.getP_KP_ij();
        this.W_w_TPR_ij = MI3272TprInitData.getW_w_TPR_ij();
        this.W_xc_TPR_ij = MI3272TprInitData.getW_xc_TPR_ij();
        this.W_w_ij = MI3272TprInitData.getW_w_ij();
        this.W_xc_ij = MI3272TprInitData.getW_xc_ij();
        this.KF_conf = MI3272TprInitData.getKF_conf();
        this.K_PEP_gr = MI3272TprInitData.getK_PEP_gr();
        this.MF_set_range = MI3272TprInitData.getMF_set_range();
        this.delta_KP = MI3272TprInitData.getDelta_KP();
        this.delta_PP = MI3272TprInitData.getDelta_PP();
        this.delta_UOI_K = MI3272TprInitData.getDelta_UOI_K();
        this.delta_t_KP = MI3272TprInitData.getDelta_t_KP();
        this.delta_t_PP = MI3272TprInitData.getDelta_t_PP();
        this.ZS = MI3272TprInitData.getZS();
    }

    double[][] V_KP_pr_ij;
    double[][] K_TPR_ij;
    public double[] calculateK_j() {
        alpha_cyl_t = MI3272Formulas.calculateAlpha_cyl_t(alpha_cyl_t, alpha_cyl_t_sq);
        V_KP_pr_ij = calculateV_KP_pr_ij(alpha_cyl_t, t_KP_ij_avg, P_KP_ij_avg, t_TPR_ij_avg,
                P_TPR_ij_avg, t_st_ij, rho_TPR_ij, W_w_TPR_ij, W_xc_TPR_ij);
        K_TPR_ij = MI3272Formulas.calculateK_TPR_ij(N_TPR_ij_avg, V_KP_pr_ij);
        K_TPR_j = MI3272Formulas.calculateK_TPR_j(K_TPR_ij);
        return K_TPR_j;
    }

    public MI3272TprFinalData calculateWithTpr() {
        log.info("----- МИ3272 -----");
        MI3272TprFinalData mi3272TprFinalData = new MI3272TprFinalData();
        double alpha_cyl_t = MI3272Formulas.calculateAlpha_cyl_t(this.alpha_cyl_t, alpha_cyl_t_sq);
        double[][] Q_ij_TPR = calculateQ_ij(alpha_cyl_t);

//        double[][] V_KP_pr_ij = calculateV_KP_pr_ij(alpha_cyl_t, t_KP_ij_avg, P_KP_ij_avg, t_TPR_ij_avg,
//                P_TPR_ij_avg, t_st_ij, rho_TPR_ij);
//        double[][] K_TPR_ij = MI3272Formulas.calculateK_TPR_ij(N_TPR_ij_avg, V_KP_pr_ij);
        
        double[] Pi_j = MI3272Formulas.calculatePi_j(K_TPR_ij);
        double[][] V2_KP_pr_ij = calculateV_KP_pr_ij(alpha_cyl_t, t2_KP_ij_avg, P2_KP_ij_avg, t2_TPR_ij_avg,
                P2_TPR_ij_avg, t2_st_ij, rho2_TPR_ij, W_w_TPR_ij, W_xc_TPR_ij);
        double[][] K2_TPR_ij = MI3272Formulas.calculateK_TPR_ij(N2_TPR_ij_avg, V2_KP_pr_ij);
        double[] K2_TPR_j = MI3272Formulas.calculateK_TPR_j(K2_TPR_ij);

        log.info("t_КП_ij (повторное измерение) = \n{}", TableDisplay.display2DimArray(t2_KP_ij_avg));
        log.info("P_КП_ij (повторное измерение) = \n{}", TableDisplay.display2DimArray(P2_KP_ij_avg));
        log.info("t_ТПР_ij (повторное измерение) = \n{}", TableDisplay.display2DimArray(t2_TPR_ij_avg));
        log.info("P_ТПР_ij (повторное измерение) = \n{}", TableDisplay.display2DimArray(P2_TPR_ij_avg));
        log.info("t_ст_ij (повторное измерение) = \n{}", TableDisplay.display2DimArray(t2_st_ij));

        log.info("V_КП_пр_ij (повторное измерение) = \n{}", TableDisplay.display2DimArray(V2_KP_pr_ij));
        log.info("K_ТПР_ij (повторное измерение) = \n{}", TableDisplay.display2DimArray(K2_TPR_ij));
        log.info("K_ТПР_j (повторное измерение) = \n{}", K2_TPR_j);

        double[] delta_k_j = MI3272Formulas.calculateDelta_K_j(K_TPR_j, K2_TPR_j);
        log.info("N_ТПР_задij = \n{}", TableDisplay.display2DimArray(N_TPR_ij_zad));
        double[][] V_TPR_ij = MI3272Formulas.calculateV_TPR_ij(N_TPR_ij_zad, K_TPR_j);

        log.info("----- Вычисление rho_ПП_пр_ij -----");
        double[][] rho_15 = Appendix.calculateRho_15(workingFluid, rho_PP_ij, t_PP_ij, P_PP_ij);
        Map<String, double[][]> betaGamma = Appendix.calculateBetaGamma(workingFluid, t_PP_ij, W_w_ij, rho_15, t_KP_ij, W_xc_ij);
        double[][] beta_fluid_ij = betaGamma.get("beta");
        double[][] gamma_fluid_ij = betaGamma.get("gamma");

        log.info("rho_15 = \n{}", TableDisplay.display2DimArray(rho_15));
        log.info("beta_ж = \n{}", TableDisplay.display2DimArray(beta_fluid_ij));
        log.info("gamma_ж = \n{}", TableDisplay.display2DimArray(gamma_fluid_ij));

        double[][] rho_PP_pr_ij;
        double[][] M_re_ij;
        if(PPInKP && TPRInKP){
            rho_PP_pr_ij = MI3272Formulas.calculateRho_PP_pr_ij(rho_BIK_ij, t_PP_ij, t_KP_ij,
                beta_fluid_ij, gamma_fluid_ij, P_KP_ij, P_PP_ij);
            M_re_ij = MI3272Formulas.calculateM_re_ij(V_TPR_ij, rho_PP_pr_ij);
        }
        else{
            rho_PP_pr_ij = MI3272Formulas.calculateRho_PP_pr_ij(rho_BIK_ij, t_PP_ij, t_TPR_ij,
                    beta_fluid_ij, gamma_fluid_ij, P_TPR_ij, P_PP_ij);
            M_re_ij = MI3272Formulas.calculateM_re_ij(V_TPR_ij, rho_PP_ij);
        }

        double[][] M_mas_ij = MI3272Formulas.calculateM_mas_ij(N_mas_ij, KF_conf);
        double[][] MF_ij = MI3272Formulas.calculateMF_ij(M_re_ij, M_mas_ij, MF_set_range);
        double[][] Q_ij = MI3272Formulas.calculateQ_ij(V_TPR_ij, T_ij, rho_PP_pr_ij);

        mi3272TprFinalData.setQ_TPR_ij(Q_ij_TPR);
        mi3272TprFinalData.setQ_ij(Q_ij);
        mi3272TprFinalData.setBeta_fluid_ij(beta_fluid_ij);
        mi3272TprFinalData.setGamma_fluid_ij(gamma_fluid_ij);
        mi3272TprFinalData.setV_KP_pr_ij(V_KP_pr_ij);
        mi3272TprFinalData.setK_TPR_ij(K_TPR_ij);
        mi3272TprFinalData.setPi_j(Pi_j);
        mi3272TprFinalData.setK2_TPR_j(K2_TPR_j);
        mi3272TprFinalData.setDelta_K_j(delta_k_j);
        mi3272TprFinalData.setV_TPR_ij(V_TPR_ij);
        mi3272TprFinalData.setRho_PP_pr_ij(rho_PP_pr_ij);
        mi3272TprFinalData.setM_re_ij(M_re_ij);
        mi3272TprFinalData.setM_mas_ij(M_mas_ij);
        mi3272TprFinalData.setMF_ij(MF_ij);
        mi3272TprFinalData.setAlpha_cyl_t_out(alpha_cyl_t);

        double[] Q_j_avg = MI3272Formulas.calculateQ_j_avg(Q_ij);
        mi3272TprFinalData.setQ_j_avg(Q_j_avg);
        if (calibrCharImpl.equals(MI3272Constants.PEP)) {
            int measureCount = mi3272TprFinalData.getMF_ij().length;
            // записываются в таблицу 4 - (при реализации ГХ в ПЭП)
            double[] MF_j_avg = MI3272Formulas.calculateMF_j_avg(mi3272TprFinalData.getMF_ij());
            double S_MF_range = MI3272Formulas.calculateS_MF_range(mi3272TprFinalData.getMF_ij(), MF_j_avg);
            double delta_mas_0 = MI3272Formulas.calculateDelta_mas_0(ZS, Q_j_avg);
            double MF_range = MI3272Formulas.calculateMF_range(MF_j_avg);
            double K_gr = MI3272Formulas.calculateK_gr(K_PEP_gr, MF_range);
            double t_P_n = Appendix.get_t_P_n(measureCount);
            double epsilon = MI3272Formulas.calculateEpsilon_PEP(t_P_n, S_MF_range);

            // вспомогательные
            double theta_t = MI3272Formulas.calculateTheta_t(delta_t_KP, delta_t_PP, mi3272TprFinalData.getBeta_fluid_ij());
            double theta_MF_range = MI3272Formulas.calculateTheta_MForKF_range(MF_j_avg, MF_range);
            log.info("theta_t = \n{}", theta_t);
            log.info("theta_MF_диап = \n{}", theta_MF_range);

            // записываются в таблицу 4 - (при реализации ГХ в ПЭП)
            double theta_sigma = MI3272Formulas.calculateTheta_sigma_PEP(delta_KP, delta_PP, theta_t, delta_UOI_K, theta_MF_range, delta_mas_0);

            // записываются в таблицу 3
            double Z_P = Appendix.getZ_P(theta_sigma / S_MF_range);

            // записываются в таблицу 4 - (при реализации ГХ в ПЭП)
            double delta = MI3272Formulas.calculateDelta_PEP(Z_P, theta_sigma, epsilon, S_MF_range);

            mi3272TprFinalData.setMForKF_j_avg(MF_j_avg);
            mi3272TprFinalData.setS_MForKF_range(S_MF_range);
            mi3272TprFinalData.setDelta_mas_0(delta_mas_0);
            mi3272TprFinalData.setMForKF_range(MF_range);
            mi3272TprFinalData.setK_gr(K_gr);
            mi3272TprFinalData.setT_P_n(t_P_n);
            mi3272TprFinalData.setEpsilon(epsilon);
            mi3272TprFinalData.setTheta_sigma(theta_sigma);
            mi3272TprFinalData.setZ_P(Z_P);
            mi3272TprFinalData.setDelta(delta);
        } else if (calibrCharImpl.equals(MI3272Constants.SOI_RANGE)) {

            // записывается в таблицу 2
            double[][] KF_ij = MI3272Formulas.calculateKF_ij(N_mas_ij, mi3272TprFinalData.getM_re_ij());

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
            double theta_t = MI3272Formulas.calculateTheta_t(delta_t_KP, delta_t_PP, mi3272TprFinalData.getBeta_fluid_ij());
            log.info("theta_t = \n{}", theta_t);

            // записываются в таблицу 4 - (при реализации ГХ в СОИ в виде постоянного значения К-фактора)
            double theta_sigma = MI3272Formulas.calculateTheta_sigma_SOI_const(delta_KP, delta_PP, theta_t, delta_UOI_K, theta_KF_range, delta_mas_0);

            // записываются в таблицу 3
            double Z_P = Appendix.getZ_P(theta_sigma / S_KF_range);

            // записываются в таблицу 4 - (при реализации ГХ в СОИ в виде постоянного значения К-фактора)
            double delta = MI3272Formulas.calculateDelta_SOI_const(Z_P, theta_sigma, epsilon, S_KF_range);

            mi3272TprFinalData.setKF_ij(KF_ij);
            mi3272TprFinalData.setMForKF_j_avg(KF_j_avg);
            mi3272TprFinalData.setS_MForKF_range(S_KF_range);
            mi3272TprFinalData.setDelta_mas_0(delta_mas_0);
            mi3272TprFinalData.setMForKF_range(KF_range);
            mi3272TprFinalData.setTheta_KF_range(theta_KF_range);
            mi3272TprFinalData.setT_P_n(t_P_n);
            mi3272TprFinalData.setEpsilon(epsilon);
            mi3272TprFinalData.setTheta_sigma(theta_sigma);
            mi3272TprFinalData.setZ_P(Z_P);
            mi3272TprFinalData.setDelta(delta);
        } else {
            // записывается в таблицу 2
            double[][] KF_ij = MI3272Formulas.calculateKF_ij(N_mas_ij, mi3272TprFinalData.getM_re_ij());

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
            double theta_t = MI3272Formulas.calculateTheta_t(delta_t_KP, delta_t_PP, mi3272TprFinalData.getBeta_fluid_ij());
            log.info("theta_t = \n{}", theta_t);

            // записываются в таблицу 4 - (при реализации ГХ в СОИ в виде кусочно-линейной аппроксимации)
            double[] theta_sigma_k = MI3272Formulas.calculateTheta_sigma_k(delta_KP, delta_PP, theta_t, delta_UOI_K, theta_KF_k, delta_mas_0k);

            double[] Z_P_k = MI3272Formulas.calculateZ_P_k(theta_sigma_k, S_KF_k);
            double[] delta_k = MI3272Formulas.calculateDelta_k(Z_P_k, theta_sigma_k, epsilon_k, S_KF_k);

            mi3272TprFinalData.setKF_ij(KF_ij);
            mi3272TprFinalData.setZ_P_k(Z_P_k);
            mi3272TprFinalData.setMForKF_j_avg(KF_j_avg);
            mi3272TprFinalData.setQ_kmin(Q_kmin);
            mi3272TprFinalData.setQ_kmax(Q_kmax);
            mi3272TprFinalData.setS_KF_k(S_KF_k);
            mi3272TprFinalData.setDelta_mas_0k(delta_mas_0k);
            mi3272TprFinalData.setTheta_KF_k(theta_KF_k);
            mi3272TprFinalData.setEpsilon_k(epsilon_k);
            mi3272TprFinalData.setTheta_sigma_k(theta_sigma_k);
            mi3272TprFinalData.setT_P_n(t_P_n);
            mi3272TprFinalData.setDelta_k(delta_k);
        }

        // заключение: {годен}/{не годен}
        // в качестве {рабочего и контрольно-резервного (контрольного)} / {рабочего}
        if (/*usedTPR &&*/ !MI3272Formulas.checkDelta_k_j(mi3272TprFinalData.getDelta_K_j())) {
            mi3272TprFinalData.setConclusion(MI3272Constants.UNFIT);
        } else {
            if (calibrCharImpl.equals(MI3272Constants.PEP) || calibrCharImpl.equals(MI3272Constants.SOI_RANGE)) {
                double delta = mi3272TprFinalData.getDelta();
                if (MI3272Formulas.checkDeltaAsOperating(delta)) {
                    mi3272TprFinalData.setUsedAs(MI3272Constants.USED_AS_OPERATING);
                    if (MI3272Formulas.checkDeltaAsControl(delta)) {
                        mi3272TprFinalData.setUsedAs(MI3272Constants.USED_AS_CONTROL_AND_OPERATING);
                    }
                    mi3272TprFinalData.setConclusion(MI3272Constants.FIT);
                } else {
                    mi3272TprFinalData.setConclusion(MI3272Constants.UNFIT);
                }

            } else {
                double[] delta_k = mi3272TprFinalData.getDelta_k();
                if (MI3272Formulas.checkDelta_kAsOperating(delta_k)) {
                    mi3272TprFinalData.setUsedAs(MI3272Constants.USED_AS_OPERATING);
                    if (MI3272Formulas.checkDelta_kAsControl(delta_k)) {
                        mi3272TprFinalData.setUsedAs(MI3272Constants.USED_AS_CONTROL_AND_OPERATING);
                    }
                    mi3272TprFinalData.setConclusion(MI3272Constants.FIT);
                } else {
                    mi3272TprFinalData.setConclusion(MI3272Constants.UNFIT);
                }
            }
        }

        return mi3272TprFinalData;
    }

    private double[][] calculateQ_ij(double alpha_cyl_t){
        log.info("----- Вычисление Q_ij (таблица 2, часть I) -----");
        double[][] V_KP_pr_ij = MI3272Formulas.calculateV_KP_pr_ij_Formula4(V_KP_0, alpha_cyl_t, t_KP_ij_avg, alpha_st_t, t_st_ij, D, E, s, P_KP_ij_avg);
        double[][] rho15_ij = Appendix.calculateRho_15(workingFluid, rho_PP_ij_avg, P_PP_ij_avg, t_PP_ij_avg);
        Map<String, double[][]> betaGamma = Appendix.calculateBetaGamma(workingFluid, t_PP_ij_avg, W_w_TPR_ij, rho15_ij, t_KP_ij_avg, W_xc_TPR_ij);
        double[][] gamma_fluid_ij = betaGamma.get("gamma");
        double[][] beta_fluid_ij = betaGamma.get("beta");
        double[][] rho_PP_pr_ij = MI3272Formulas.calculateRho_PP_pr_ij(rho_BIK_ij_avg, t_PP_ij_avg, t_KP_ij_avg, beta_fluid_ij, gamma_fluid_ij, P_KP_ij_avg, P_PP_ij_avg);

        log.info("V_КП_пр_ij = \n{}", TableDisplay.display2DimArray(V_KP_pr_ij));
        log.info("rho_ПП_ij = \n{}", TableDisplay.display2DimArray(rho_PP_ij_avg));
        log.info("P_ПП_ij = \n{}", TableDisplay.display2DimArray(P_PP_ij_avg));
        log.info("t_ПП_ij = \n{}", TableDisplay.display2DimArray(t_PP_ij_avg));

        log.info("rho15_ij = \n{}", TableDisplay.display2DimArray(rho15_ij));
        log.info("gamma_ж_ij = \n{}", TableDisplay.display2DimArray(gamma_fluid_ij));
        log.info("beta_ж_ij = \n{}", TableDisplay.display2DimArray(beta_fluid_ij));
        log.info("rho_БИК_ij = \n{}", TableDisplay.display2DimArray(rho_BIK_ij_avg));
        log.info("rho_ПП_пр_ij = \n{}", TableDisplay.display2DimArray(rho_PP_pr_ij));
        return MI3272Formulas.calculateQ_ij(V_KP_pr_ij, T_ij_avg, rho_PP_pr_ij);
    }

    private double[][] calculateV_KP_pr_ij(double alpha_cyl_t, double[][] t_KP_ij_avg, double[][] P_KP_ij_avg,
                                           double[][] t_TPR_ij_avg, double[][] P_TPR_ij_avg,
                                           double[][] t_st_ij, double[][] rho_TPR_ij, double[][] W_w_ij, double[][] W_xc_ij) {
        double[][] V_KP_pr_ij;

        // если ТПР входит в состав компакт-прувера
        if (TPRInKP) {
            V_KP_pr_ij = MI3272Formulas.calculateV_KP_pr_ij_Formula4(V_KP_0, alpha_cyl_t, t_KP_ij_avg,
                    alpha_st_t, t_st_ij, D, E, s, P_KP_ij_avg);
        } else {
            log.info("----- Вычисление V_КП_пр_ij по формуле (7) (таблица 1, часть I) -----");
            double[][] rho_15 = Appendix.calculateRho_15(workingFluid, rho_TPR_ij, t_KP_ij_avg, P_KP_ij_avg);
            Map<String, double[][]> betaGamma = Appendix.calculateBetaGamma(workingFluid, t_TPR_ij_avg, W_w_ij, rho_15, t_KP_ij_avg, W_xc_ij);
            double[][] beta_fluid_ij = betaGamma.get("beta");
            double[][] gamma_fluid_ij = betaGamma.get("gamma");
            log.info("rho_15 = \n{}", TableDisplay.display2DimArray(rho_15));
            log.info("beta_ж = \n{}", TableDisplay.display2DimArray(beta_fluid_ij));
            log.info("gamma_ж = \n{}", TableDisplay.display2DimArray(gamma_fluid_ij));

            V_KP_pr_ij = MI3272Formulas.calculateV_KP_pr_ij_Formula7(V_KP_0, alpha_cyl_t, t_KP_ij_avg,
                    D, E, s, P_KP_ij_avg, beta_fluid_ij, t_TPR_ij_avg, P_TPR_ij_avg, gamma_fluid_ij);
        }
        return V_KP_pr_ij;
    }

}
