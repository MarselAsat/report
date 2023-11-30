package com.nppgks.reportingsystem.reportgeneration.manual_reports.kmh.massmbypu;

import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.InitialData;
import lombok.Data;

@Data
public class KmhMassmByPuInitialData implements InitialData {
    private int pointsCount;
    private int measureCount;
    private double V_KP_0;
    private double delta_KP;
    private double D;
    private double E;
    private double s;
    private double delta_t_KP;
    private double delta_PP;
    private double delta_t_PP;
    private double delta_UOI_K;

    private double KF_conf;
    private double ZS;
    private Double alpha_cyl_t;
    private Double alpha_cyl_t_sq;
    private Double alpha_st_t;

    private double[][] Q_TPR_ij;
    private double[][] N_TPR_ij_avg;
    private double[][] t_TPR_ij_avg;
    private double[][] P_TPR_ij_avg;
    private double[][] t_KP_ij_avg;
    private double[][] P_KP_ij_avg;
    private double[][] t_st_ij;
    private double mass;
    private double[][] Q_ij;
    private double[][] t_il;
    private double[][] P_il;
    private double[][] t_TPR;
    private double[][] P_TPR;
    private double[][] rho_TPR;
    private double[][] M_il;
    private double[][] M_TPR;
    private double[][] delta_M;
}
