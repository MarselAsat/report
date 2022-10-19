package com.nppgks.reports.service.poverka3622.data;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

@Data
@FieldNameConstants
public class InitialData {

    private double[][] Q_ij;
    private double[][] N_e_ij;
    private double[][] N_p_ij;
    private double[][] T_ij;
    private double[][] M_ij;
    private double f_p_max;
    private double Q_p_max;
    private double MF_p;
    private double[][] K_e_ij;
    private double ZS;
    private double theta_e;
    private double theta_t;
    private double theta_p;
    private double theta_N;
    private double theta_PDt;
    private double theta_PDp;
    private double theta_Dt;
    private double theta_Dp;
    private int measureCount;
    private int pointsCount;
}
