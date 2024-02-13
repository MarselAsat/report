package com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3272.calculations.withtpr;

import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.InitialData;
import lombok.Data;

/**
 * Этот класс хранит данные, необходимые для расчета коэффициента преобразования ТПР
 */
@Data
public class TprCoeffInitData implements InitialData {
    // Группа, к которой принадлежит рабочая жидкость.
    // Значения могут быть: "нефть", "нефтепродукт", "смазочное масло"
    // С помощью этого параметра вычисляются beta_fluid_ij и gamma_fluid_ij
    private String workingFluid;

    // Используется ТПР, входящий в состав компакт-прувера или не входящий
    private boolean TPRInKP;

    // Используется ПП, входящий в состав компакт-прувера
    private boolean PPInKP;

    private Double alpha_cyl_t;
    private Double alpha_cyl_t_sq;
    private Double alpha_st_t;
    private double[][] t_KP_ij_avg;
    private double[][] P_KP_ij_avg;
    private double[][] t_TPR_ij_avg;
    private double[][] P_TPR_ij_avg;
    private double[][] t_st_ij;
    private double[][] rho_TPR_ij; // используется для расчета beta и gamma в формуле (7)
    private double[][] N_TPR_ij_avg;
    private double V_KP_0;
    private double D, E, s;
}
