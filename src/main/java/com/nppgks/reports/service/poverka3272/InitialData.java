package com.nppgks.reports.service.poverka3272;

import lombok.Data;

@Data
public class InitialData {
    private boolean usedTPR;
    private OperatingFluid opFluid;
    private double[][] T_ij;
    private double V_0_KP;
    private double[][] ro_PP_ij, ro_BIK_ij;
    private double[][] Q_ij;
    private double[][] N_TPR_ij, N_mas_ij;
    private double[][] t_KP_ij, t_PP_ij, t_st_ij;;
    private double[][] N2_TPR_ij;
    private double[][] t2_KP_ij;
    private double[][] P2_KP_ij;
    private double[][] P_KP_ij, P_PP_ij;
    private double alpha_CYL_t_sq;
    private double alpha_ST_t;
    private double KFconf;
    private double K_PEP_gr;
    private double MF_setRange;
    private double delta_KP, delta_PP;
    private double delta_UOI_K;
    private double delta_t_KP, delta_t_PP;
    private double ZS;
    private double D, E, s;
}
