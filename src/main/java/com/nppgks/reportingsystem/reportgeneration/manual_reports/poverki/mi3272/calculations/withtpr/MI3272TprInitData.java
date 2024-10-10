package com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3272.calculations.withtpr;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MI3272TprInitData extends TprCoeffInitData {
    // Реализация градуировочных характеристик:
    // {ПЭП} - реализация в ПЭП,
    // {СОИ рабочий диапазон} - реализация в СОИ в виде постоянного значения К-фактора,
    // {СОИ поддиапазон} - реализация в СОИ в виде кусочно-линейной аппроксимации (по умолчанию)
    private String calibrCharImpl,WorkingFluid;

    // доп параметры для вычисления Q_ij
    private double[][] T_ij_avg, rho_BIK_ij_avg, t_PP_ij_avg, P_PP_ij_avg;

    // Повторные измерения для расчета коэффициента ТПР
    private double[][] N_TPR_ij_avg, t_TPR_ij_avg, P_TPR_ij_avg;
    private double[][] t_KP_ij_avg, P_KP_ij_avg;
    private double[][] t_st_ij;
    private double[][] rho_TPR_ij; // используется для расчета beta и gamma в формуле (7)
    private double[][] W_w_TPR_ij;
    private double[][] W_xc_TPR_ij;


    // предыдущее расчитаное значение коэффициента
    private double[] K_TPR_j;

    // параметры для расчета МХ массомера
    private double[][] T_ij;
    private double[][] N_mas_ij;
    private double[][] rho_PP_ij, t_PP_ij, P_PP_ij;
    private double[][] N_TPR_ij_zad, t_TPR_ij, P_TPR_ij;
    private double[][] t_KP_ij, P_KP_ij;
    private double[][] W_w_ij;
    private double[][] W_xc_ij;
    private double KF_conf;
    private double K_PEP_gr;
    private double MF_set_range;
    private double delta_KP, delta_PP;
    private double delta_UOI_K;
    private double delta_t_KP, delta_t_PP;
    private double ZS;
//    private double Alpha_cyl_t;
//    private double Alpha_cyl_t_sq;
//    private double Alpha_st_t;


    // Эти параметры не используются в вычислениях. Только отображаются на html странице протокола поверки
    private double[] N_TPR_j_zad;
    private String protocolNumber;
    private String massmeterModel;
    private String place_name;
    private String place_owner;
    private String massmeterSensor;
    private String massmeterDu;
    private String massmeterNumber;
    private String pepModel;
    private String pepNumber;
    private String installedOn;
    private String ilNumber;
    private String cpType;
    private String cpGrade;
    private String cpNumber;
    private String cpDate;
    private String tprType;
    private String tprRange;
    private String tprNumber;
    private String ppType;
    private String ppNumber;
    private String ppDate;
    private String companyName;
    private String verifierName;

}
