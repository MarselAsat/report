package com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3272.calculations;

import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.InitialData;
import lombok.Data;

@Data
public class MI3272InitialData implements InitialData {
    // Используется ли ТПР во время поверки
    private boolean usedTPR;

    // Используется ТПР, входящий в состав компакт-прувера или не входящий
    private boolean TPRInKP;

    // Реализация градуировочных характеристик:
    // {ПЭП} - реализация в ПЭП,
    // {СОИ рабочий диапазон} - реализация в СОИ в виде постоянного значения К-фактора,
    // {СОИ поддиапазон} - реализация в СОИ в виде кусочно-линейной аппроксимации (по умолчанию)
    private String calibrCharImpl;

    // Параметры ТПР. Если ТПР не используется, то и эти параметры не используются
    private double[][] N_TPR_ij_avg, t_TPR_ij_avg, P_TPR_ij_avg;

    // параметры компакт-прувера
    private double[][] t_KP_ij_avg, P_KP_ij_avg;

    private double[][] t_st_ij;

    private double[][] T_ij_TPR;
    private double[][] T_ij;

    // Группа, к которой принадлежит рабочая жидкость.
    // Значения могут быть: "нефть", "нефтепродукт", "смазочное масло"
    // С помощью этого параметра вычисляются beta_fluid_ij и gamma_fluid_ij
    private String workingFluid;
    private Double alpha_cyl_t, alpha_st_t, alpha_cyl_t_sq;
    private double V_KP_0;
    private double[][] rho_TPR_ij; // используется для расчета beta и gamma в формуле (7)
    private double[][] rho_PP_ij, t_PP_ij, P_PP_ij;

    // параметры для повтороного подсчета коэффициента преобразования ТПР
    private double[][] rho2_TPR_ij, N2_TPR_ij_avg, t2_KP_ij_avg, P2_KP_ij_avg, t2_TPR_ij_avg, P2_TPR_ij_avg, t2_st_ij;

    // параметры для расчета МХ массомера
    private double[][] N_mas_ij;
    private double[][] N_TPR_ij_zad, t_TPR_ij, P_TPR_ij;
    private double[][] rho_BIK_ij;
    private double KF_conf;
    private double K_PEP_gr;
    private double MF_set_range;
    private double delta_KP, delta_PP;
    private double delta_UOI_K;
    private double delta_t_KP, delta_t_PP;
    private double ZS;
    private double D, E, s;

    // кол-во измерений для поверки массомера
    private int measureCount;

    // кол-во серий прохода поршня для определения коэффициентов преобразования ТПР
    private int seriesCount;
    private int pointsCount;
}
