package com.nppgks.reportingsystem.controller.poverki;

import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
import com.nppgks.reportingsystem.util.ArrayParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/mi3313")
public class MI3313Controller {

    @GetMapping
    public String getMI3313Page(ModelMap modelMap) {
        modelMap.put("protocolNumber", "6");
        modelMap.put("place_name", "Какое-то место");
        modelMap.put("srm_sensor_type", "Promass F");
        modelMap.put("srm_sensor_number", "MB04AC02000");
        modelMap.put("srm_sensor_line", "2");
        modelMap.put("srm_converter_type", "Кориолисовый");

        modelMap.put("srm_converter_number", "08798962");
        modelMap.put("esrm_sensor_type", "PROM");
        modelMap.put("esrm_sensor_number", "49862484");
        modelMap.put("esrm_converter_type", "Кориолисовый");
        modelMap.put("esrm_converter_number", "8970204");

        modelMap.put("ivk_type", "Октопус Л");
        modelMap.put("ivk_number", "96768723");
        modelMap.put("operating_fluid", "черная черная нефть");

        modelMap.put("delta_e", 0.1);
        modelMap.put("K_pme", 45000);
        modelMap.put("delta_ivk", 0.01);
        modelMap.put("K_PM", 45000);
        modelMap.put("MForK_set", 1);
        modelMap.put("Q_Mmax", 0);
        modelMap.put("ZS", 0.032);
        modelMap.put("Q_nom", 0);
        modelMap.put("delta_tdop", 0.008);
        modelMap.put("t_min", 0);
        modelMap.put("t_max", 0);
        modelMap.put("delta_Pdop", 0);
        modelMap.put("P_min", 0);
        modelMap.put("P_max", 0);

        double[][] Q_ji = new double[][]{{35.7, 35.7, 35.9, 36.3}, {36.9, 37, 37.5, 37.7}, {37.6, 37.4, 37.2, 37.1}};
        double[][] T_ji = new double[][]{{15, 15, 15, 15}, {15, 15, 15, 15}, {15, 15, 15, 15}};
        double[][] N_eji = new double[][]{{6697, 6696, 6727, 6813}, {6912, 6941, 7036, 7077}, {7041, 7014, 6974, 6947}};
        double[][] N_ji = new double[][]{{6677, 6694, 6717, 6773}, {6911, 6967, 7004, 7037}, {7057, 7040, 7017, 6991}};
        double[][] M_eji = new double[][]{{0.14882, 0.1488, 0.14949, 0.1514}, {0.1536, 0.15424, 0.15636, 0.15727}, {0.15647, 0.15587, 0.15498, 0.15438}};
        double[][] M_ji = new double[][]{{0.14838, 0.14876, 0.14927, 0.15051}, {0.15358, 0.15482, 0.15564, 0.15638}, {0.15682, 0.15644, 0.15593, 0.15536}};
        modelMap.put("Q_ji", Q_ji);
        modelMap.put("T_ji", T_ji);
        modelMap.put("N_eji", N_eji);
        modelMap.put("N_ji", N_ji);
        modelMap.put("M_eji", M_eji);
        modelMap.put("M_ji", M_ji);
        modelMap.put("MForKji", Q_ji);

        double[] Q_j = new double[]{37, 35, 36, 35.5};
        modelMap.put("Q_j", Q_j);
        modelMap.put("MForKj", Q_j);
        modelMap.put("n_j", Q_j);
        modelMap.put("S_j", Q_j);
        modelMap.put("S_0j", Q_j);
        modelMap.put("t_095", Q_j);
        modelMap.put("epsilon_j", Q_j);

        modelMap.put("Q_min", 0.89890);
        modelMap.put("Q_max", 0.89890);
        modelMap.put("MForK", 0.89890);
        modelMap.put("S_0", 0.89890);
        modelMap.put("epsilon", 0.89890);
        modelMap.put("theta_A", 0.89890);
        modelMap.put("theta_Z", 0.89890);
        modelMap.put("t_P", 0.89890);
        modelMap.put("theta_Mt", 0.89890);
        modelMap.put("P_P", 0.89890);
        modelMap.put("theta_MP", 0.89890);
        modelMap.put("theta_sigma", 0.89890);
        modelMap.put("delta", 0.89890);

        modelMap.put("conclusion", "годен");
        modelMap.put("dateDay", "10");
        modelMap.put("dateMonth", "июня");
        modelMap.put("dateYear", "2020");


        // Этот параметр нужен для сохранения отчета
        // Этот параметр используется в обработчике нажатия на кнопку "Сохранить в БД" в save-report-in-db.js
        modelMap.put("saveUrl", "/mi3313/save");

        // Этот параметр нужен для отображения кнопок "Сохранить в БД" и "Печать" после генерации отчета
        modelMap.put("printSaveButtonsRequired", true);

        return "report_pages/poverki/MI3313-one-esrm";
    }
}
