package com.nppgks.reportingsystem.controller.kmh;

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
public class KmhDensityMeterController {

    private final ManualReportGenerator reportGenerator;

    public KmhDensityMeterController(@Qualifier("kmhDensityMeterReportGenerator") ManualReportGenerator reportGenerator) {
        this.reportGenerator = reportGenerator;
    }

    @GetMapping("/densityMeter")
    public String getDensityMeterReportPage(ModelMap modelMap){
        List<ReportData> reportDataList = reportGenerator.generateReport();
        reportDataList.forEach(rd -> {
            Object value = ArrayParser.fromJsonToObject(rd.getData());
            modelMap.put(
                    rd.getTag().getPermanentName(), value);
        });

        // Этот параметр нужен для отображения кнопок "Сохранить в БД" и "Печать" после генерации отчета
        modelMap.put("printSaveButtonsRequired", true);

        // Этот параметр нужен для сохранения отчета
        // Этот параметр используется в обработчике нажатия на кнопку "Сохранить в БД" в save-report-in-db.js
        modelMap.put("saveUrl", "/kmh/densityMeter/save");

        return "report_pages/kmh-density-meter-report-page";
    }

    @ResponseBody
    @GetMapping("/densityMeter/save")
    public String saveDensityMeterReport() {
        return reportGenerator.saveReportInDb();
    }
}
