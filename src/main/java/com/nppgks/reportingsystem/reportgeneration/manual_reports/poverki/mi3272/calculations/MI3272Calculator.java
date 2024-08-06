package com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3272.calculations;

import com.nppgks.reportingsystem.constants.MI3272Constants;
import com.nppgks.reportingsystem.exception.NotValidTagValueException;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.formulas.MI3272Formulas;
import com.nppgks.reportingsystem.util.TableDisplay;
import com.nppgks.reportingsystem.util.TagValueValidator;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
public class MI3272Calculator {

    // Таблица 1
    private double V_KP_0, delta_KP, D, s, E, delta_t_KP;
    private double delta_PP, delta_t_PP;
    private double delta_UOI_K, KF_conf;
    private double ZS;

    // Таблица 2
    private double[][] N_mas_ij;
    private double[][] t_KP_ij_avg, P_KP_ij_avg;
    private double[][] rho_PP_ij_avg, t_PP_ij_avg, P_PP_ij_avg;
    double[][] W_w_ij;
    double[][] W_xc_ij;

    // Таблица 3
    private Double alpha_cyl_t;
    private Double alpha_cyl_t_sq;
    private Double alpha_st_t;

    // Группа, к которой принадлежит рабочая жидкость.
    // Значения могут быть: "нефть", "нефтепродукт", "смазочное масло"
    // С помощью этого параметра вычисляются beta_fluid_ij и gamma_fluid_ij
    private String workingFluid;

    // Реализация градуировочных характеристик:
    // {ПЭП} - реализация в ПЭП,
    // {СОИ рабочий диапазон} - реализация в СОИ в виде постоянного значения К-фактора,
    // {СОИ поддиапазон} - реализация в СОИ в виде кусочно-линейной аппроксимации (по умолчанию)
    private String calibrCharImpl;

    private boolean PPInKP;

    private double[][] T_ij;

    private double[][] rho_BIK_ij_avg;
    private double[][] t_st_ij;
    private double K_PEP_gr;
    private double MF_set_range;

    public void initData(MI3272InitData MI3272InitData) {
        this.workingFluid = MI3272InitData.getWorkingFluid();
        this.PPInKP = MI3272InitData.isPPInKP();
        this.alpha_st_t = MI3272InitData.getAlpha_st_t();
        this.alpha_cyl_t = MI3272InitData.getAlpha_cyl_t();
        this.alpha_cyl_t_sq = MI3272InitData.getAlpha_cyl_t_sq();
        this.t_KP_ij_avg = MI3272InitData.getT_KP_ij_avg();
        this.P_KP_ij_avg = MI3272InitData.getP_KP_ij_avg();
        this.V_KP_0 = MI3272InitData.getV_KP_0();
        this.D = MI3272InitData.getD();
        this.E = MI3272InitData.getE();
        this.s = MI3272InitData.getS();
        this.calibrCharImpl = MI3272InitData.getCalibrCharImpl();
        this.T_ij = MI3272InitData.getT_ij();
        this.t_st_ij = MI3272InitData.getT_st_ij();
        this.N_mas_ij = MI3272InitData.getN_mas_ij();
        //this.rho_BIK_ij_avg = MI3272InitData.getRho_BIK_ij_avg();
        this.rho_PP_ij_avg = MI3272InitData.getRho_PP_ij_avg();
        this.t_PP_ij_avg = MI3272InitData.getT_PP_ij_avg();
        this.P_PP_ij_avg = MI3272InitData.getP_PP_ij_avg();
        this.W_w_ij = MI3272InitData.getW_w_ij();
        this.W_xc_ij = MI3272InitData.getW_xc_ij();
        this.KF_conf = MI3272InitData.getKF_conf();
        this.K_PEP_gr = MI3272InitData.getK_PEP_gr();
        this.MF_set_range = MI3272InitData.getMF_set_range();
        this.delta_KP = MI3272InitData.getDelta_KP();
        this.delta_PP = MI3272InitData.getDelta_PP();
        this.delta_UOI_K = MI3272InitData.getDelta_UOI_K();
        this.delta_t_KP = MI3272InitData.getDelta_t_KP();
        this.delta_t_PP = MI3272InitData.getDelta_t_PP();
        this.ZS = MI3272InitData.getZS();
    }

    private void validate() {
        TagValueValidator.notNull(N_mas_ij, "N_mass_ij");
        TagValueValidator.notNull(t_KP_ij_avg, "t_KP_ij_avg");
        TagValueValidator.notNull(P_KP_ij_avg, "P_KP_ij_avg");
        TagValueValidator.notNull(rho_PP_ij_avg, "rho_PP_ij_avg");
        TagValueValidator.notNull(t_PP_ij_avg, "t_PP_ij_avg");
        TagValueValidator.notNull(P_PP_ij_avg, "P_PP_ij_avg");
        TagValueValidator.notNull(T_ij, "T_ij");
        TagValueValidator.notNull(t_st_ij, "t_st_ij");
        TagValueValidator.notNull(alpha_st_t, "alpha_st_t");

        TagValueValidator.haveSameLen2DimArr(
                List.of(N_mas_ij, t_KP_ij_avg, P_KP_ij_avg, rho_PP_ij_avg, t_PP_ij_avg, T_ij, t_st_ij),
                List.of("N_mas_ij", "t_KP_ij_avg", "P_KP_ij_avg", "rho_PP_ij_avg", "t_PP_ij_avg", "T_ij", "t_st_ij"));

        TagValueValidator.hasOneOfValues(workingFluid, List.of("нефть", "нефтепродукт", "смазочное масло"), "workingFluid");
        TagValueValidator.hasOneOfValues(calibrCharImpl, List.of("ПЭП", "СОИ рабочий диапазон", "СОИ поддиапазон"), "calibrCharImpl");

        TagValueValidator.notZero(KF_conf, "KF_conf");
        TagValueValidator.notZero(T_ij, "T_ij");
        if(PPInKP){
            boolean tEquals = Arrays.deepEquals(t_KP_ij_avg, t_PP_ij_avg);
            boolean PEquals = Arrays.deepEquals(P_PP_ij_avg, P_KP_ij_avg);
            if(!tEquals || !PEquals){
                throw new NotValidTagValueException("Если ПП установлен на КП, то t_ПП должно быть равно t_КП, а P_ПП должно быть равно P_KP");
            }
        }

        if(alpha_cyl_t == null && alpha_cyl_t_sq == null){
            throw new NotValidTagValueException(
                    "Оба коэффициента alpha_cyl_t и alpha_cyl_t_sq равны null. " +
                            "Хотя бы одно из них должно иметь значение");
        }

    }

    public MI3272FinalData calculate() {
        log.info("----- МИ3272 без ТПР -----");
        validate();
        MI3272FinalData mi3272FinalData = new MI3272FinalData();
        double alpha_cyl_t = MI3272Formulas.calculateAlpha_cyl_t(this.alpha_cyl_t, alpha_cyl_t_sq);

        double[][] V_KP_pr_ij = MI3272Formulas.calculateV_KP_pr_ij_Formula4(V_KP_0, alpha_cyl_t, t_KP_ij_avg,
                alpha_st_t, t_st_ij, D, E, s, P_KP_ij_avg);
        log.info("t_ст_ij = \n{}", TableDisplay.display2DimArray(t_st_ij));

        log.info("----- Вычисление rho_ПП_пр_ij -----");
        double[][] rho_15 = Appendix.calculateRho_15(workingFluid, rho_PP_ij_avg, t_PP_ij_avg, P_PP_ij_avg);
        BetaGamma betaGamma = Appendix.calculateBetaGamma(workingFluid, t_KP_ij_avg, W_w_ij, rho_15, t_KP_ij_avg, W_xc_ij);
        double[][] beta_fluid_ij = betaGamma.getBeta();
        double[][] gamma_fluid_ij = betaGamma.getGamma();

        log.info("rho_15 = \n{}", TableDisplay.display2DimArray(rho_15));
        log.info("beta_ж = \n{}", TableDisplay.display2DimArray(beta_fluid_ij));
        log.info("gamma_ж = \n{}", TableDisplay.display2DimArray(gamma_fluid_ij));

        double[][] rho_PP_pr_ij = MI3272Formulas.calculateRho_PP_pr_ij(rho_PP_ij_avg, t_PP_ij_avg, t_KP_ij_avg,
                    beta_fluid_ij, gamma_fluid_ij, P_KP_ij_avg, P_PP_ij_avg);

        double[][] M_re_ij;
        if (!PPInKP) {
            M_re_ij = MI3272Formulas.calculateM_re_ij(V_KP_pr_ij, rho_PP_pr_ij);
        } else {
            M_re_ij = MI3272Formulas.calculateM_re_ij(V_KP_pr_ij, rho_PP_ij_avg);
        }

        double[][] M_mas_ij = MI3272Formulas.calculateM_mas_ij(N_mas_ij, KF_conf);
        double[][] MF_ij = MI3272Formulas.calculateMF_ij(M_re_ij, M_mas_ij, MF_set_range);
        double[][] Q_ij = MI3272Formulas.calculateQ_ij(V_KP_pr_ij, T_ij, rho_PP_pr_ij);

        log.info("MF_уст_диап = \n{}", MF_set_range);
        log.info("T_ij = \n{}", TableDisplay.display2DimArray(T_ij));

        mi3272FinalData.setQ_ij(Q_ij);
        mi3272FinalData.setBeta_fluid_ij(beta_fluid_ij);
        mi3272FinalData.setGamma_fluid_ij(gamma_fluid_ij);
        mi3272FinalData.setV_KP_pr_ij(V_KP_pr_ij);
        mi3272FinalData.setRho_PP_pr_ij(rho_PP_pr_ij);
        mi3272FinalData.setM_re_ij(M_re_ij);
        mi3272FinalData.setM_mas_ij(M_mas_ij);
        mi3272FinalData.setMF_ij(MF_ij);
        mi3272FinalData.setAlpha_cyl_t_out(alpha_cyl_t);

        double[] Q_j_avg = MI3272Formulas.calculateQ_j_avg(Q_ij);
        mi3272FinalData.setQ_j_avg(Q_j_avg);
        if (calibrCharImpl.equals(MI3272Constants.PEP)) {
            int measureCount = mi3272FinalData.getMF_ij().length;
            int pointsCount = mi3272FinalData.getMF_ij()[0].length;
            // записываются в таблицу 4 - (при реализации ГХ в ПЭП)
            double[] MF_j_avg = MI3272Formulas.calculateMF_j_avg(mi3272FinalData.getMF_ij());
            double S_MF_range = MI3272Formulas.calculateS_MF_range(mi3272FinalData.getMF_ij(), MF_j_avg);
            double delta_mas_0 = MI3272Formulas.calculateDelta_mas_0(ZS, Q_j_avg);
            double MF_range = MI3272Formulas.calculateMF_range(MF_j_avg);
            double K_gr = MI3272Formulas.calculateK_gr(K_PEP_gr, MF_range);
            double t_P_n = Appendix.get_t_P_n(measureCount * pointsCount);
            double epsilon = MI3272Formulas.calculateEpsilon_PEP(t_P_n, S_MF_range);

            // вспомогательные
            double theta_t = MI3272Formulas.calculateTheta_t(delta_t_KP, delta_t_PP, mi3272FinalData.getBeta_fluid_ij());
            double theta_MF_range = MI3272Formulas.calculateTheta_MForKF_range(MF_j_avg, MF_range);
            log.info("K_ПЭП_гр = \n{}", K_PEP_gr);
            log.info("theta_t = \n{}", theta_t);
            log.info("theta_MF_диап = \n{}", theta_MF_range);

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
            int pointsCount = KF_ij[0].length;

            // записываются в таблицу 4 - (при реализации ГХ в СОИ в виде постоянного значения К-фактора)
            double[] KF_j_avg = MI3272Formulas.calculateKF_j_avg(KF_ij);
            double S_KF_range = MI3272Formulas.calculateS_KF_range(KF_ij, KF_j_avg);
            double delta_mas_0 = MI3272Formulas.calculateDelta_mas_0(ZS, Q_j_avg);
            double KF_range = MI3272Formulas.calculateKF_range(KF_j_avg);
            double theta_KF_range = MI3272Formulas.calculateTheta_MForKF_range(KF_j_avg, KF_range);

            // записываются в таблицу 3
            double t_P_n = Appendix.get_t_P_n(measureCount*pointsCount);

            // записываются в таблицу 4 - (при реализации ГХ в СОИ в виде постоянного значения К-фактора)
            double epsilon = MI3272Formulas.calculateEpsilon_SOI_const(t_P_n, S_KF_range);

            // вспомогательные
            double theta_t = MI3272Formulas.calculateTheta_t(delta_t_KP, delta_t_PP, mi3272FinalData.getBeta_fluid_ij());
            log.info("theta_t = \n{}", theta_t);

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
            log.info("theta_t = \n{}", theta_t);

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
        if (calibrCharImpl.equals(MI3272Constants.PEP) || calibrCharImpl.equals(MI3272Constants.SOI_RANGE)) {
            double delta = mi3272FinalData.getDelta();
            if (MI3272Formulas.checkDeltaAsOperating(delta)) {
                mi3272FinalData.setUsedAs(MI3272Constants.USED_AS_OPERATING);
                if (MI3272Formulas.checkDeltaAsControl(delta)) {
                    mi3272FinalData.setUsedAs(MI3272Constants.USED_AS_CONTROL_AND_OPERATING);
                }
                mi3272FinalData.setConclusion(MI3272Constants.FIT);
            } else {
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
            } else {
                mi3272FinalData.setConclusion(MI3272Constants.UNFIT);
            }
        }

        return mi3272FinalData;
    }

}
