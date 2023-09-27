package com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3272.calculations;

import com.nppgks.reportingsystem.reportgeneration.manual_reports.formulas.MI2632;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

//@Component
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

    private static double[][] beta_fluid_ij;
    private static double[][] gamma_fluid_ij;

    static {
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

    public static double calculateBeta_fluid_max(String opFluid, double[][] ro_PP, double[][] t_PP) {
        int m = ro_PP[0].length;
        int n = ro_PP.length;
        double betta_fluid_max;
        double max = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                max = Math.max(max, MI2632.calculateBetta_fluid(opFluid, ro_PP[i][j], t_PP[i][j]));
            }
        }
        betta_fluid_max = max;
        return betta_fluid_max;
    }

    public static double[][] calculateBeta_fluid(String opFluid, double[][] ro_PP, double[][] t_PP) {
        int m = ro_PP[0].length;
        int n = ro_PP.length;
        beta_fluid_ij = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                beta_fluid_ij[i][j] = MI2632.calculateBetta_fluid(opFluid, ro_PP[i][j], t_PP[i][j]);
            }
        }
        return beta_fluid_ij;
    }

    public static double[][] calculateGamma_fluid(double[][] ro_PP, double[][] t_PP) {
        int m = ro_PP[0].length;
        int n = ro_PP.length;
        gamma_fluid_ij = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                gamma_fluid_ij[i][j] = MI2632.calculateGamma_fluid(ro_PP[i][j], t_PP[i][j]);
            }
        }
        return gamma_fluid_ij;
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
