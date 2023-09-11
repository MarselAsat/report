package com.nppgks.reportingsystem.reportgeneration.manual_reports.kmh.massmbypu;

import com.nppgks.reportingsystem.reportgeneration.manual_reports.formulas.MI3272Formulas;

import java.util.Arrays;

public class KmhMassmByPuCalculator {

    public static KmhMassmByPuFinalData calculate(KmhMassmByPuInitialData initialData){
        var finalData = new KmhMassmByPuFinalData();

        double alpha_cyl_t = MI3272Formulas.calculateAlpha_cyl_t(initialData.getAlpha_cyl_t(), initialData.getAlpha_cyl_t_sq());
        double[][] V_KP_pr_ij = MI3272Formulas.calculateV_KP_pr_ij_Formula4(initialData.getV_KP_0(),
                alpha_cyl_t,
                initialData.getT_KP_ij_avg(),
                initialData.getAlpha_st_t(),
                initialData.getT_st_ij(),
                initialData.getD(),
                initialData.getE(),
                initialData.getS(),
                initialData.getP_KP_ij_avg());
        double[][] K_TPR_ij = MI3272Formulas.calculateK_TPR_ij(initialData.getN_TPR_ij_avg(), V_KP_pr_ij);
        double[] Pi_j = MI3272Formulas.calculatePi_j(K_TPR_ij);
        double[] K_TPR_j = MI3272Formulas.calculateK_TPR_j(K_TPR_ij);
        double delta_max = Arrays.stream(initialData.getDelta_M())
                .flatMapToDouble(Arrays::stream)
                .map(Math::abs)
                .max().orElseThrow();

        String conclusion = "годен";

        if(delta_max >= 0.2) conclusion = "не годен";

        finalData.setV_KP_pr_ij(V_KP_pr_ij);
        finalData.setK_TPR_ij(K_TPR_ij);
        finalData.setPi_j(Pi_j);
        finalData.setK_TPR_j(K_TPR_j);
        finalData.setDelta_max(delta_max);
        finalData.setConclusion(conclusion);

        return finalData;
    }
}
