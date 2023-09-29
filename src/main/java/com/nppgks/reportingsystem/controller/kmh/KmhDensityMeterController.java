package com.nppgks.reportingsystem.controller.kmh;

import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
import com.nppgks.reportingsystem.util.ArrayParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/kmh")
public class KmhDensityMeterController {

    @GetMapping("/densityMeter")
    public String getDensityMeterReportPage(ModelMap modelMap){
        modelMap.put("protocolNumber", "45-16");
        modelMap.put("siknNumber", "1516");
        modelMap.put("place_name", "ПСП Марковское ООО «ИНК»");
        modelMap.put("pp_type", "Solartron 7835B");
        modelMap.put("areometer_type", "АНТ-1");
        modelMap.put("pp_number", "8000596");
        modelMap.put("areometer_number", "45309");
        modelMap.put("pp_date", "09.10.2015");
        modelMap.put("areometer_date", "21.06.2012");
        modelMap.put("delta_pp", "0,3");
        modelMap.put("delta_areometer", "0,5");
        modelMap.put("delta_syst", "0,62");
        modelMap.put("delta_met", "0,6");
        modelMap.put("Q_i", new double[]{4.95, 5.0, 4.88});
        modelMap.put("t_pl_i", new double[]{25, 25, 25});
        modelMap.put("P_pl_i", new double[]{2.92, 2.92, 2.93});
        modelMap.put("rho_pl_i", new double[]{822.9, 822.9, 822.9});
        modelMap.put("rho_meas_i", new double[]{821.1, 821.1, 820.9, 820.8, 821.1, 820.9});
        modelMap.put("t_ar_i", new double[]{25.5, 25.3, 25.6, 25.6, 25.7, 25.7});
        modelMap.put("beta15", new double[]{0.000895, 0.000896, 0.000895});
        modelMap.put("gamma_pl_i", new double[]{0.000825, 0.000826, 0.000825});
        modelMap.put("rho_lpr_i", new double[]{822.6, 822.5, 822.8});
        modelMap.put("delta_pk_i", new double[]{1, 0.9, 1.1});
        modelMap.put("conclusion", "не годен");
        modelMap.put("kmh_date", "03.03.2016");
        modelMap.put("deliverOrg", "ООО «ИНК»");
        modelMap.put("metrologistName", "Н.В. Тростин");
        modelMap.put("chemistName", "Ю.Д. Рабинович");
        modelMap.put("receiverOrg", "ООО «Транснефть-Восток»");
        modelMap.put("receiverPosition", "Инженер по метрологии ПСП НПС-8");
        modelMap.put("receiverName", "А.Ю. Вялов");
        modelMap.put("serviceOrg", "ЗАО НИЦ «Инкомсистем»");
        modelMap.put("servicePosition", "Ст. инженер МС");
        modelMap.put("serviceName", "Д.Р. Габдулхаков");
        modelMap.put("workingOrReserve", "рабочий");

        return "report_pages/kmh-density-meter-report-page";
    }
}
