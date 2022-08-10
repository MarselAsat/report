package com.nppgks.reports.service.poverka3622;

import lombok.Data;

@Data
public class InitialData {

    private double[][] Q;
    private double[][] N_e;
    private double[][] N_p;
    private double[][] T;
    private double[][] M;
    private double f_p_max;
    private double Q_p_max;
    private double MF_prev;
    double[][] K_e;
}
