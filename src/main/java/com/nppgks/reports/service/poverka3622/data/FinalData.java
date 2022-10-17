package com.nppgks.reports.service.poverka3622.data;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

@FieldNameConstants
@Data
public class FinalData {

    private Double K_pm;
    private double[][] M_e_ij;
    private double[][] MF_ij;
    private Double MF;
    private double[] MF_j;
    private Double K;
    private Double[] K_j;
    private double[][] K_ij;
    private Double MF_p;
    private double[][] f_ij;
    private double[] f_j;
    private double[] S_j;
    private double[] S_0j;
    private double[] eps_j;
    private double[] t_095;
    private Double eps_D;
    private double[] Q_j;
    private double[] theta_sigma_j;
    private double[] eps_PDk;
    private Double theta_sigma_D;
    private double[] theta_sigma_PDk;
    private double[] delta_j;
    private double[] t_sigma_j;
    private double[] S_sigma_j;
    private double[] S_theta_j;
    private Double delta_D;
    private double[] delta_PDk;
    private double[] t_sigma_PDk;
    private double[] S_sigma_PDk;
    private double[] S_theta_PDk;
    private double[] S_PDk;
    private double[] theta_zj;
    private Double theta_Dz;
    private Double Q_min;
    private Double Q_max;
    private Double[] Q_min_k;
    private double[] Q_max_k;
    private Double theta_D;
    private double[] theta_PDz;
    private double[] theta_PDk;
}
