package com.nppgks.reports.service.poverka3622.data;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

@FieldNameConstants
@Data
public class FinalData {

    private double[][] f_ij;
    private double[][] M_e_ij;
    private double[][] K_ij;
    private double[][] MF_ij;
    private double[] Q_j;
    private double[] f_j;
    private double[] K_j;
    private double[] MF_j;
    private double[] S_j;
    private double[] S_0j;
    private double[] t_095;
    private double[] eps_j;
    private double[] theta_zj;
    private double[] theta_sigma_j;
    private double[] S_theta_j;
    private double[] S_sigma_j;
    private double[] t_sigma_j;
    private double[] delta_j;
    private double[] Q_min;
    private double[] Q_max;
    private double[] eps_PDk;
    private double[] S_PDk;
    private double[] theta_PDk;
    private double[] theta_PDz;
    private double[] theta_sigma_PDk;
    private double[] S_theta_PDk;
    private double[] S_sigma_PDk;
    private double[] t_sigma_PDk;
    private double[] delta_PDk;
}
