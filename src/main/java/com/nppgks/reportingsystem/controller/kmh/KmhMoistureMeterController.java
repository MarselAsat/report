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
public class KmhMoistureMeterController {

    private final ManualReportGenerator reportGenerator;

    public KmhMoistureMeterController(@Qualifier("kmhMoistMetReportGenerator") ManualReportGenerator reportGenerator) {
        this.reportGenerator = reportGenerator;
    }

    @GetMapping("/moisturemeter")
    public String getKmhMoistureMeterPage(ModelMap modelMap){

        List<ReportData> reportDataList = reportGenerator.generateReport();
        reportDataList.forEach(rd -> {
            Object value = ArrayParser.fromJsonToObject(rd.getData());
            modelMap.put(
                    rd.getTag().getPermanentName(), value);
        });

        // Этот параметр нужен для сохранения отчета
        // Этот параметр используется в обработчике нажатия на кнопку "Сохранить в БД" в save-report-in-db.js
        modelMap.put("saveUrl", "/kmh/moisturemeter/save");

        // Этот параметр нужен для отображения кнопок "Сохранить в БД" и "Печать" после генерации отчета
        modelMap.put("printSaveButtonsRequired", true);

        return "report_pages/kmh-moisturemeter-report-page";
    }

    @ResponseBody
    @GetMapping("/moisturemeter/save")
    public String saveKmhMoistureMeterReport() {
        return reportGenerator.saveReportInDb();
    }
}
