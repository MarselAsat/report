package com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3272.calculations;

import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.InitialData;
import lombok.Data;

@Data
public class MI3272InitData implements InitialData {
    // Таблица 1
    private double V_KP_0, delta_KP, D, s, E, delta_t_KP;
    private double delta_PP, delta_t_PP;
    private double delta_UOI_K, KF_conf;
    private double ZS;

    // Таблица 2
    private double[][] N_mas_ij;
    private double[][] t_KP_ij_avg, P_KP_ij_avg;
    private double[][] rho_PP_ij_avg, t_PP_ij_avg, P_PP_ij_avg,Rho_PP_ij;
    double[][] W_xc_ij;
    double[][] W_w_ij;

    // Таблица 3
    private Double alpha_cyl_t;
    private Double alpha_cyl_t_sq;
    private Double alpha_st_t;

    // Группа, к которой принадлежит рабочая жидкость.
    // Значения могут быть: "нефть", "нефтепродукт", "смазочное масло"
    // С помощью этого параметра вычисляются beta_fluid_ij и gamma_fluid_ij
    private String workingFluid;

    // Реализация градуировочных характеристик:
    // {ПЭП} - реализация в ПЭП,
    // {СОИ рабочий диапазон} - реализация в СОИ в виде постоянного значения К-фактора,
    // {СОИ поддиапазон} - реализация в СОИ в виде кусочно-линейной аппроксимации (по умолчанию)
    private String calibrCharImpl;

    // применяют поточный ПП, установленный в БИК или на компакт-прувере
    private boolean PPInKP;

    private double[][] T_ij;
    private double[][] t_st_ij;

    private double K_PEP_gr;
    private double MF_set_range;

    // Эти параметры не используются в вычислениях. Только отображаются на html странице протокола поверки
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
