package com.nppgks.reportingsystem.controller;

import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.ManualReportGenerator;
import com.nppgks.reportingsystem.util.ArrayParser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/kmh")
public class KmhController {

    private final ManualReportGenerator reportGenerator;

    public KmhController(@Qualifier("kmhViscReportGenerator") ManualReportGenerator reportGenerator) {
        this.reportGenerator = reportGenerator;
    }

    @GetMapping("/viscometer")
    public String getKmhViscometerPage(ModelMap modelMap){

        List<ReportData> reportDataList = reportGenerator.generateReport();
        reportDataList.forEach(rd -> {
            Object value = ArrayParser.fromJsonToObject(rd.getData());
            modelMap.put(
                    rd.getTag().getPermanentName(), value);
        });
        modelMap.put("printSaveButtonsRequired", true);

        return "report_pages/kmh-viscometer-report-page";
    }

    @GetMapping("/moisturemeter")
    public String getKmhMoistureMeterPage(ModelMap modelMap){

        modelMap.put("protocolNumber", "020-19");
        modelMap.put("siknNumber", "1516");
        modelMap.put("place_name", "ПСП Марковское ООО «ИНК»");
        modelMap.put("reserve_mm_type", "УДВН-1пм");
        modelMap.put("working_mm_type", "УДВН-1пм");
        modelMap.put("reserve_mm_number", "2207");
        modelMap.put("working_mm_number", "2208");
        modelMap.put("reserve_mm_date", "02.07.2018");
        modelMap.put("working_mm_date", "25.04.2018");
        modelMap.put("reserve_mm_delta_W_kv", "0.06");
        modelMap.put("working_mm_delta_W_kv", "0.06");
        modelMap.put("reserve_mm_delta_W_0", "0.14");
        modelMap.put("working_mm_delta_W_0", "0.14");

        modelMap.put("measure_time", new String[]{"4:48", "4:58", "5:08"});
        modelMap.put("work_mm_phi_v", new double[]{0.02, 0.02, 0.01});
        modelMap.put("reserve_mm_phi_v", new double[]{0.0, 0.0, 0.0});

        modelMap.put("work_mm_W_kv", new double[]{0.02, 0.02, 0.01});
        modelMap.put("reserve_mm_W_kv", new double[]{0.0, 0.0, 0.0});
        modelMap.put("W_0", new double[]{0.03, 0.06, 0.03});
        modelMap.put("rho_v", new double[]{824.2, 824.2, 824.2});

        modelMap.put("work_mm_W_id", new double[]{0.01, 0.04, 0.02});
        modelMap.put("reserve_mm_W_id", new double[]{0.03, 0.06, 0.03});

        modelMap.put("working_mm_conclusion", "годен");
        modelMap.put("reserve_mm_conclusion", "годен");
        modelMap.put("kmh_date", "07.02.2019");
        modelMap.put("deliverOrg", "ООО \"ИНК\"");
        modelMap.put("metrologistName", "Титов А.В.");
        modelMap.put("chemistName", "Рабинович Ю.Д.");
        modelMap.put("receiverOrg", "ООО \"Транснефть-Восток\"");
        modelMap.put("receiverPosition", "Мастер ПСП НПС-7");
        modelMap.put("receiverName", "Ермолаев К.В.");
        modelMap.put("serviceOrg", "ЗАО НИЦ \"Инкомсистем\"");
        modelMap.put("servicePosition", "Инженер ТО и С");
        modelMap.put("serviceName", "Рахматуллин Р.Ф.");
        modelMap.put("printSaveButtonsRequired", true);
        return "report_pages/kmh-moisture-meter-report-page";
    }

    @ResponseBody
    @GetMapping("/viscometer/save")
    public String saveKmhViscometerReport() {
        return reportGenerator.saveReportInDb();
    }
}
