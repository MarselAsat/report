package com.nppgks.reportingsystem.reportgeneration.manual_reports.formulas;

import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3272.calculations.WorkingFluid;

public class P50_2_076 {

    // формула (3)
    public static double calculateGamma_t(double rho_15, double t) {
        return 0.001 * Math.exp(-1.6208 + 0.00021592 * t + 870960 / Math.pow(rho_15, 2) + 4209.2 * t / Math.pow(rho_15, 2));
    }

    // формула (4)
    public static double calculateBeta_t(String opFluid, double rho_15, double t) {
        double beta_15 = calculateBeta_15(opFluid, rho_15);
        return beta_15 + 1.6 * Math.pow(beta_15, 2) * (t - 15);
    }

    public static double calculateRho_15(String fluidType, double rho_tP, double t, double P) {
        double beta_15 = calculateBeta_15(fluidType, rho_tP);
        double gamma_t = calculateGamma_t(rho_tP, t);
        double rho_15_prev = calculateRho15(beta_15, t, gamma_t, P, rho_tP);
        beta_15 = calculateBeta_15(fluidType, rho_15_prev);
        gamma_t = calculateGamma_t(rho_15_prev, t);
        double rho_15 = calculateRho15(beta_15, t, gamma_t, P, rho_tP);
        while(Math.abs(rho_15-rho_15_prev) > 0.01){
            beta_15 = calculateBeta_15(fluidType, rho_15_prev);
            gamma_t = calculateGamma_t(rho_15_prev, t);
            rho_15_prev = rho_15;
            rho_15 = calculateRho15(beta_15, t, gamma_t, P, rho_tP);
        }
        return rho_15;
    }

    private static double calculateRho15(double beta_15, double t, double gamma_t, double P, double rho_tP) {
        return (rho_tP * (1 - gamma_t * P)) /
                (Math.exp(-beta_15 * (t - 15) * (1 + 0.8 * beta_15 * (t - 15))));
    }

    // формула (2)
    private static double calculateBeta_15(String fluidType, double rho_15) {
        double[] K = calculateCoeffK0K1K2(fluidType, rho_15);
        return (K[0] + K[1] * rho_15) / (Math.pow(rho_15, 2)) + K[2];
    }

    // Таблица 1
    private static double[] calculateCoeffK0K1K2(String fluidType, double rho_15) {
        if (fluidType.equalsIgnoreCase(WorkingFluid.OIL) && rho_15 >= 611.2 && rho_15 < 1163.9) {
            return new double[]{613.9723, 0, 0};
        } else if (fluidType.equalsIgnoreCase(WorkingFluid.OIL_PRODUCT)) {
            if (rho_15 >= 611.2 && rho_15 < 770.9) {
                return new double[]{346.4228, 0.43884, 0};
            } else if (rho_15 >= 770.9 && rho_15 < 788) {
                return new double[]{2690.744, 0, -0.0033762};
            } else if (rho_15 >= 788 && rho_15 < 838.7) {
                return new double[]{594.5418, 0, 0};
            } else if (rho_15 >= 838.7 && rho_15 < 1163.9) {
                return new double[]{186.9696, 0.4862, 0};
            }
        } else if (fluidType.equalsIgnoreCase(WorkingFluid.LUBE_OIL) && rho_15 >= 801.3 && rho_15 < 1163.9) {
            return new double[]{0, 0.6278, 0};
        }
        throw new RuntimeException("Неверная группа нефтепродуктов (%s) или плотность (%s)".formatted(fluidType, rho_15));
    }
}
