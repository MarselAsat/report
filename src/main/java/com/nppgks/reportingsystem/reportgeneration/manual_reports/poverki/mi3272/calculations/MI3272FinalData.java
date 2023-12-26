package com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3272.calculations;

import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.FinalData;
import lombok.Data;

@Data
public class MI3272FinalData implements FinalData {
    private double[][] beta_fluid_ij;
    private double[][] gamma_fluid_ij;
    private double[][] V_KP_pr_ij;
    private double[][] K_TPR_ij;
    private double[] Pi_j;
    private double[] K_TPR_j;
    private double[] K2_TPR_j;
    private double[] delta_K_j;
    private double[][] V_TPR_ij;
    private double[][] rho_PP_pr_ij;
    private double[][] M_re_ij;
    private double[][] M_mas_ij;
    private double[][] MF_ij;
    private double[][] KF_ij;
    private double alpha_cyl_t;
    private double t_P_n;
    private Double Z_P;
    private double[] Z_P_k;
    private double[] Q_j_avg;
    private double[] MForKF_j_avg;
    private double S_MForKF_range;
    private double delta_mas_0;
    private double MForKF_range;
    private double K_gr;
    private double epsilon;
    private double theta_sigma;
    private double theta_KF_range;
    private double delta;
    private double[] Q_kmin;
    private double[] Q_kmax;
    private double[] S_KF_k;
    private double[] delta_mas_0k;
    private double[] theta_KF_k;
    private double[] epsilon_k;
    private double[] theta_sigma_k;
    private double[] delta_k;
    private String usedAs;
    private String conclusion;
}
