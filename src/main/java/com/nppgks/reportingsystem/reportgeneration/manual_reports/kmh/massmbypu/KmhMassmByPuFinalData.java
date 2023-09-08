package com.nppgks.reportingsystem.reportgeneration.manual_reports.kmh.massmbypu;

import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.FinalData;
import lombok.Data;

@Data
public class KmhMassmByPuFinalData implements FinalData {
    private double[][] V_KP_pr_ij;
    private double[][] K_TPR_ij;
    private double[] Pi_j;
    private double[] K_TPR_j;
    private double delta_max;
    private String conclusion;
}
