package com.nppgks.reportingsystem.certification;

public class CertificationAlgorithms {

    public static double calculateDensity15(String product, double density, double temp, double overpressure) {
        double result = calculateRo(product, density, density, temp, overpressure);
        double prevResult = density;

        while (Math.abs(result - prevResult) > 0.01) {
            System.out.println("dif: "+Math.abs(result - prevResult));
            prevResult = result;
            result = calculateRo(product, result, density, temp, overpressure);
        }
        System.out.println("dif: "+Math.abs(result - prevResult));
        return result;
    }

    private static double calculateRo(String product, double density15, double densityMeas, double temp, double overpressure) {
        double CTL = calculateCTL(product, density15, temp, overpressure);
        System.out.println("CTL="+CTL);
        double CPL = calculateCPL(product, density15, temp, overpressure);
        System.out.println("CPL="+CPL);
        return densityMeas / (CTL * CPL);
    }

    public static double calculateCTL(String environment, double p15, double temp, double overpressure) {
        double beta = calculateBeta(environment, p15, temp, overpressure);
        return Math.exp(-beta * (temp - 15) * (1 + 0.8 * beta * (temp - 15)));
    }

    public static double calculateCPL(String environment, double p15, double temp, double overpressure) {

        double gamma_t = calculateGamma(environment, p15, temp, overpressure);
        return 1 / (1 - gamma_t * overpressure);
    }

    public static double calculateBeta(String environment, double p15, double temperature, double overpressure) {
        double K0 = 0;
        double K1 = 0;
        double K2 = 0;
        if (environment.equals("нефть")) {
            K0 = 613.9723;
            K1 = 0;
            K2 = 0;
        } else {
            // Бензины
            if (p15 >= 611.2 && p15 <= 770.9) {
                K0 = 346.4228;
                K1 = 0.43884;
                K2 = 0;
            }
            // Топлива, занимающие по плотности промежуточное место между бензинами и керосинами
            else if (p15 >= 770.9 && p15 <= 788) {
                K0 = 2690.744;
                K1 = 0;
                K2 = -0.0033762;
            }
            // Топлива и керосины для реактивных двигателей. авиационное реактивное топливо ДЖ ЕТ А-1 по ГОСТ 32595
            else if (p15 >= 788 && p15 <= 838.7) {
                K0 = 594.5418;
                K1 = 0;
                K2 = 0;
            }
            // Дизельные топлива, мазуты, печные топлива
            else if (p15 >= 838.7 && p15 <= 1163.9) {
                K0 = 186.9696;
                K1 = 0.4862;
                K2 = 0;
            }
        }
        return (K0 + K1 * p15) / (p15 * p15) + K2;
    }

    public static double calculateGamma(String environment, double p15, double temp, double overpressure) {
        return 0.001 * Math.exp(-1.62080 + 0.00021592 * temp + 870960 / (p15 * p15) + 4209.2 * temp / (p15 * p15));
    }

    public static double calculateWaterFraction(double fi_w, double ro_w, double ro_bik) {
        return fi_w * ro_w / (((1 - fi_w / 100) * ro_bik) + fi_w / 100 * ro_w);
    }

    public static double calculateChlorideSaltsFraction(double fi_cs, double ro_cs) {
        return 0.1 * fi_cs / ro_cs;
    }

    public static double calculateQ_vol(double f, double K) {
        return f * 3600 / K;
    }

    public static double calculateQ_mass(double Q_il, double ro_il) {
        return Q_il * ro_il / 1000;
    }

    public static double calculateKinematicViscosity(double dynVisc, double density) {
        return dynVisc/(density*0.001);
    }
}
