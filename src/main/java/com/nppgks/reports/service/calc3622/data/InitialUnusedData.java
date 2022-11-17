package com.nppgks.reports.service.calc3622.data;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

@FieldNameConstants
@Data
public class InitialUnusedData {
    private double delta_t_dop;
    private double delta_P_dop;
    private double t_min;
    private double t_max;
    private double P_min;
    private double P_max;
    private double[][] t_ij;
    private double[][] P_ij;
    private double[] Q_p;
    private double[] f_p;
    private double[] K_y;
    private double[] f_e_arr;
}
