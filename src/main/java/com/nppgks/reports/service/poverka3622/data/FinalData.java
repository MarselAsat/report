package com.nppgks.reports.service.poverka3622.data;

import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.jdbc.support.incrementer.HsqlMaxValueIncrementer;

import java.util.HashMap;
import java.util.Map;

import static com.nppgks.reports.service.poverka3622.data.FinalData.Fields.*;

@FieldNameConstants
@Data
public class FinalData {

    private double[][] f_ij;
    private double[][] M_e_ij;
    private double[][] K_ij;
    private double[][] MF_ij;
    private double[] Q_j;
    private double[] f_j;
    private double[] K_j;
    private double[] MF_j;
    private double[] S_j;
    private double[] S_0j;
    private double[] t_095;
    private double[] eps_j;
    private double[] theta_zj;
    private double[] theta_sigma_j;
    private double[] S_theta_j;
    private double[] S_sigma_j;
    private double[] t_sigma_j;
    private double[] delta_j;
    private double[] Q_min;
    private double[] Q_max;
    private double[] eps_PDk;
    private double[] S_PDk;
    private double[] theta_PDk;
    private double[] theta_PDz;
    private double[] theta_sigma_PDk;
    private double[] S_theta_PDk;
    private double[] S_sigma_PDk;
    private double[] t_sigma_PDk;
    private double[] delta_PDk;

    public Map<String, Object> convertToMap(Map<String, String> tagNamesMap){
        Map<String, Object> map = new HashMap<>();
        map.put(tagNamesMap.get(Fields.f_ij), f_ij);
        map.put(tagNamesMap.get(Fields.M_e_ij), M_e_ij);
        map.put(tagNamesMap.get(Fields.K_ij), K_ij);
        map.put(tagNamesMap.get(Fields.MF_ij), MF_ij);
        map.put(tagNamesMap.get(Fields.Q_j), Q_j);
        map.put(tagNamesMap.get(Fields.f_j), f_j);
        map.put(tagNamesMap.get(Fields.K_j), K_j);
        map.put(tagNamesMap.get(Fields.MF_j), MF_j);
        map.put(tagNamesMap.get(Fields.S_j), S_j);
        map.put(tagNamesMap.get(Fields.S_0j), S_0j);
        map.put(tagNamesMap.get(Fields.t_095), t_095);
        map.put(tagNamesMap.get(Fields.eps_j), eps_j);
        map.put(tagNamesMap.get(Fields.theta_zj), theta_zj);
        map.put(tagNamesMap.get(Fields.theta_sigma_j), theta_sigma_j);
        map.put(tagNamesMap.get(Fields.S_theta_j), S_theta_j);
        map.put(tagNamesMap.get(Fields.S_sigma_j), S_sigma_j);
        map.put(tagNamesMap.get(Fields.t_sigma_j), t_sigma_j);
        map.put(tagNamesMap.get(Fields.delta_j), delta_j);
        map.put(tagNamesMap.get(Fields.Q_min), Q_min);
        map.put(tagNamesMap.get(Fields.Q_max), Q_max);
        map.put(tagNamesMap.get(Fields.eps_PDk), eps_PDk);
        map.put(tagNamesMap.get(Fields.S_PDk), S_PDk);
        map.put(tagNamesMap.get(Fields.theta_PDk), theta_PDk);
        map.put(tagNamesMap.get(Fields.theta_PDz), theta_PDz);
        map.put(tagNamesMap.get(Fields.theta_sigma_PDk), theta_sigma_PDk);
        map.put(tagNamesMap.get(Fields.S_theta_PDk), S_theta_PDk);
        map.put(tagNamesMap.get(Fields.S_sigma_PDk), S_sigma_PDk);
        map.put(tagNamesMap.get(Fields.t_sigma_PDk), t_sigma_PDk);
        map.put(tagNamesMap.get(Fields.delta_PDk), delta_PDk);

        return map;
    }
}
