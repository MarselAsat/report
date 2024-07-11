package com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3272.calculations;

import com.nppgks.reportingsystem.reportgeneration.manual_reports.formulas.P50_2_076;
import com.nppgks.reportingsystem.util.TableDisplay;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.math3.distribution.TDistribution;

//@Component
@Slf4j
public class Appendix {

    private static final Map<Double, Double> tableG_2 = Map.of(0.5, 0.81,
            0.75, 0.77,
            1.0, 0.74,
            2.0, 0.71,
            3.0, 0.73,
            4.0, 0.76,
            5.0, 0.78,
            6.0, 0.79,
            7.0, 0.8,
            8.0, 0.81);

    // n - кол-во измерений
    public static double get_t_P_n(int n) {
        TDistribution t = new TDistribution(n-1);
        return t.inverseCumulativeProbability(0.975);
    }

    public static double calculateBeta_fluid_max(double[][] beta_fluid_ij) {
        double beta_fluid_max;
        double max = beta_fluid_ij[0][0];
        for (double[] beta_i : beta_fluid_ij) {
            for (double beta : beta_i) {
                max = Math.max(max, beta);
            }
        }
        beta_fluid_max = max;
        log.info("beta_ж_max={}", beta_fluid_max);
        return beta_fluid_max;
    }
    public static Map<String, double[][]> calculateBetaGamma (
            String workingFluid, double[][] t_TPRorPP,
            double[][] W_w_ij, double[][] rho_15, double[][] t_KP, double[][] W_xc_ij){

        int n = t_KP.length;
        int n1 = t_TPRorPP.length;
        int m = t_KP[0].length;
        int m2 = t_TPRorPP[0].length;
        double[][] beta_fluid_ij = new double[n][m];
        double[][] gamma_fluid_ij = new double[n][m];

        boolean oilIsCrude = W_w_ij != null && W_w_ij.length != 0 && W_xc_ij != null && W_xc_ij.length != 0;

        if(!oilIsCrude){
            if(t_TPRorPP != null && t_TPRorPP.length > 0 && t_TPRorPP[0].length > 0){
                n = t_TPRorPP.length;
                m = t_TPRorPP[0].length;
                beta_fluid_ij = Appendix.calculateBeta_fluid(workingFluid, rho_15, t_TPRorPP);
                gamma_fluid_ij = Appendix.calculateGamma_fluid(rho_15, t_TPRorPP);
            } else {
                beta_fluid_ij = Appendix.calculateBeta_fluid(workingFluid, rho_15, t_KP);
                gamma_fluid_ij = Appendix.calculateGamma_fluid(rho_15, t_KP);
            }
        } else {
            n = n1;
            m = m2;
            log.info("----- Вычисление Beta и Gamma по приложению B.3 -----");
            log.info("t_КП_ij = \n{}", TableDisplay.display2DimArray(t_KP));
            log.info("W_в = \n{}", TableDisplay.display2DimArray(W_w_ij));
            log.info("W_хс = \n{}", TableDisplay.display2DimArray(W_xc_ij));
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if(W_w_ij[i][j] <= 5){
                        beta_fluid_ij[i][j] = Appendix.calculateBeta_fluid_forCrudeOilLess5Perc(workingFluid, rho_15[i][j], t_TPRorPP[i][j], W_w_ij[i][j]);
                    }
                    else{
                        double beta_w = Appendix.calculateB_w(t_TPRorPP[i][j], t_KP[i][j], W_xc_ij[i][j]);
                        beta_fluid_ij[i][j] = Appendix.calculateBeta_fluid_forCrudeOilMore5Perc(workingFluid, rho_15[i][j], t_TPRorPP[i][j], W_w_ij[i][j], beta_w);
                    }

                    gamma_fluid_ij[i][j] = Appendix.calculateGamma_fluid_forCrudeOil(rho_15[i][j], t_TPRorPP[i][j], W_w_ij[i][j]);
                }
            }
        }
        return Map.of("beta", beta_fluid_ij, "gamma", gamma_fluid_ij);
    }


    public static double[][] calculateBeta_fluid(String fluidType, double[][] rho_15, double[][] t) {
        int m = rho_15[0].length;
        int n = rho_15.length;
        double[][] beta_fluid_ij = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                beta_fluid_ij[i][j] = P50_2_076.calculateBeta_t(fluidType, rho_15[i][j], t[i][j]);
            }
        }
        return beta_fluid_ij;
    }

    public static double[][] calculateGamma_fluid(double[][] rho_15, double[][] t) {
        int m = rho_15[0].length;
        int n = rho_15.length;
        double[][] gamma_fluid_ij = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                gamma_fluid_ij[i][j] = P50_2_076.calculateGamma_t(rho_15[i][j], t[i][j]);
            }
        }
        return gamma_fluid_ij;
    }

    // формула (B.1) при условии B.3.1
    public static double calculateBeta_fluid_forCrudeOilLess5Perc(
            String fluidType, double rho_15, double t, double W_w_ij) {
        double beta_n = P50_2_076.calculateBeta_t(fluidType, rho_15, t);
        return beta_n * (1 - W_w_ij / 100) + 0.00026 * W_w_ij / 100;
    }

    // формула (B.1) при условии B.3.2
    public static double calculateBeta_fluid_forCrudeOilMore5Perc(
            String fluidType, double rho_15, double t, double W_w_ij, double beta_w) {
        double beta_n = P50_2_076.calculateBeta_t(fluidType, rho_15, t);
        return beta_n * (1 - W_w_ij / 100) + beta_w * W_w_ij / 100;
    }

    // формулы (B.3a), (B.3б) и (B.3в)
    public static double calculateB_w(double t_TPRorPP, double t_KP, double W_xc_ij) {
        double CTL_TPRorPP = calculateCTL(t_TPRorPP, W_xc_ij);
        double CTL_KP = calculateCTL(t_KP, W_xc_ij);

        if (t_TPRorPP == t_KP || CTL_TPRorPP == CTL_KP) {
            return 1 / CTL_KP;
        } else {
            return (CTL_TPRorPP - CTL_KP) /
                    (CTL_KP * (t_KP - t_TPRorPP));
        }
    }

    // формула B.4
    public static double calculateCTL(double t, double W_xc) {
        double delta_t = t - 15;
        return 1 - (0.00018526 + 0.000012882 * W_xc) * delta_t
                - (0.0000041151 - 0.00000014464 * W_xc) * Math.pow(delta_t, 2)
                + (0.0000000071926 - 0.00000000013085 * W_xc) * Math.pow(delta_t, 3);
    }

    // формула (B.2) при условии B.3.1
    public static double calculateGamma_fluid_forCrudeOil(
            double rho_15, double t, double W_w_ij) {
        double gamma_n = P50_2_076.calculateGamma_t(rho_15, t);
        return gamma_n * (1 - W_w_ij / 100) + 0.000491 * W_w_ij / 100;
    }

    public static double[][] calculateRho_15(String fluidType, double[][] rho, double[][] t, double[][] P) {
        int m = rho[0].length;
        int n = rho.length;
        double[][] rho_15 = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                rho_15[i][j] = P50_2_076.calculateRho_15(fluidType, rho[i][j], t[i][j], P[i][j]);
            }
        }
        return rho_15;
    }

    public static double getZ_P(double ratio) {
        if (ratio <= 0.625) {
            return tableG_2.get(0.5);
        } else if (ratio <= 0.875) {
            return tableG_2.get(0.75);
        } else if (ratio > 0.875 && ratio <= 8) {
            return tableG_2.get((double) Math.round(ratio));
        } else {
            return -1;
        }
    }
}
