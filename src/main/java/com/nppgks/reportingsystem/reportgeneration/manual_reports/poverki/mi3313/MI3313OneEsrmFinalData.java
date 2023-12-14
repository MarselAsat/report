package com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3313;

import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.FinalData;
import lombok.Data;

@Data
public class MI3313OneEsrmFinalData implements FinalData {
    private double[][] M_eji;
    private double[][] Q_ji;
    private double[] Q_j;
    private double Q_min;
    private double Q_max;
    private double[][] M_ji;
    private double[][] MForK_ji;
    private double[] MForK_j;
    private double MForK;
    private double[] S_j;
    private double theta_sigma;
    private double theta_A;
    private double theta_Z;
    private double theta_Mt;
    private double theta_MP;
    private double[] S_0j;
    private double[] epsilon_j;
    private double epsilon;
    private double[] t_095j;
    private double delta;
    private double S_0;
    private String conclusion;

}
