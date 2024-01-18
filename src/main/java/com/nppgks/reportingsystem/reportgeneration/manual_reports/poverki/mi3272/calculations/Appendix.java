package com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3272.calculations;

import com.nppgks.reportingsystem.reportgeneration.manual_reports.formulas.P50_2_076;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

//@Component
@Slf4j
public class Appendix {
    private static final Map<Integer, Double> tableG_1 = new HashMap<>();
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

    static {
        tableG_1.put(1, 12.706);
        tableG_1.put(2, 4.303);
        tableG_1.put(3, 3.182);
        tableG_1.put(4, 2.776);
        tableG_1.put(5, 2.571);
        tableG_1.put(6, 2.447);
        tableG_1.put(7, 2.365);
        tableG_1.put(8, 2.306);
        tableG_1.put(9, 2.262);
        tableG_1.put(10, 2.228);
        tableG_1.put(11, 2.203);
        tableG_1.put(12, 2.179);
        tableG_1.put(13, 2.162);
        tableG_1.put(14, 2.145);
        tableG_1.put(15, 2.132);
        tableG_1.put(16, 2.120);
        tableG_1.put(17, 2.110);
        tableG_1.put(18, 2.101);
        tableG_1.put(19, 2.093);
        tableG_1.put(20, 2.086);
    }

    // n - кол-во измерений
    public static double get_t_P_n(int n) {
        return tableG_1.get(n - 1);
    }

    public static double calculateBeta_fluid_max(double[][] beta_fluid_ij) {
        double betta_fluid_max;
        double max = beta_fluid_ij[0][0];
        for (double[] beta_i: beta_fluid_ij) {
            for (double beta: beta_i) {
                max = Math.max(max, beta);
            }
        }
        betta_fluid_max = max;
        log.info("beta_ж_max={}", betta_fluid_max);
        return betta_fluid_max;
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
