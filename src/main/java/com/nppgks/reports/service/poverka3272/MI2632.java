package com.nppgks.reports.service.poverka3272;

import java.util.Map;

public class MI2632 {

    public static double calculateGamma_fluid(double ro_PP, double t_PP) {
        return 0.001*Math.exp(-1.6208+0.000216*t_PP+870960/Math.pow(ro_PP, 2)+4209.2*t_PP/Math.pow(ro_PP, 2));
    }

    public static double calculateBetta_fluid(OperatingFluid opFluid, double ro_PP, double t_PP){
        double alpha = calculateAlpha(opFluid, ro_PP);

        // МИ2632 формула (4)
        double betta = alpha+1.6*Math.pow(alpha, 2)*(t_PP-15);
        return betta;
    }

    private static double calculateAlpha(OperatingFluid opFluid, double ro_PP){
        double[] K = calculateCoeffK0K1(opFluid, ro_PP);
        double alpha = (K[0] +K[1]*ro_PP)/(Math.pow(ro_PP, 2));
        return alpha;
    }

    private static double[] calculateCoeffK0K1(OperatingFluid opFluid, double ro_pp) {
        Map<OperatingFluid, double[]> K0K1Table = Map.of(OperatingFluid.OIL, new double[]{613.9722, 0},
                OperatingFluid.PETROL, new double[]{346.4228, 0.4389},
                OperatingFluid.JET_FUEL, new double[]{594.5418, 0},
                OperatingFluid.OIL_FUEL, new double[]{186.9697, 0.4862});
        return K0K1Table.get(opFluid);
    }
}
