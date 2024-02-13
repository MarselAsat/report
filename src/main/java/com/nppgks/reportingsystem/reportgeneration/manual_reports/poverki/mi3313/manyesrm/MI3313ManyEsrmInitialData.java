package com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3313.manyesrm;

import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.InitialData;
import lombok.Data;

import java.util.List;

@Data
public class MI3313ManyEsrmInitialData implements InitialData {
    private double[] K_PMEk;
    private List<double[][]> N_ejik;
    private double[][] T_ji;
    private double[][] N_ji;
    private double K_PM;
    private double MForK_set;
    private double delta_e;
    private double delta_ivk;
    private Double ZS;
    private Double delta_tdop;
    private Double delta_Pdop;
    private Double Q_nom;
    private double Q_Mmax;
    private Double t_max;
    private double t_P;
    private Double t_min;
    private Double P_max;
    private double P_P;
    private Double P_min;
    private String workingOrControl;
}
