package com.nppgks.reportingsystem.controller;

import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.ManualReportGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/passport")
public class PassportController {

    private final ManualReportGenerator reportGenerator;

    public PassportController(@Qualifier("oilQualityPassportReportGenerator") ManualReportGenerator reportGenerator) {
        this.reportGenerator = reportGenerator;
    }

    @GetMapping("/oilQuality")
    public String getOilQualityPassportPage(ModelMap modelMap){
        List<ReportData> reportDataList = reportGenerator.generateReport();
        ModelMapFiller.fillForAcceptanceAct(modelMap, reportDataList);

        // Этот параметр нужен для сохранения отчета
        // Этот параметр используется в обработчике нажатия на кнопку "Сохранить в БД" в save-report-in-db.js
        modelMap.put("saveUrl", "/passport/oilQuality/save");

        // Этот параметр нужен для отображения кнопок "Сохранить в БД" и "Печать" после генерации отчета
        modelMap.put("printSaveButtonsRequired", true);
        return "report_pages/acts/oil-quality-passport";
    }

    @ResponseBody
    @GetMapping("/oilQuality/save")
    public String saveOilQualityPassportReport() {
        return reportGenerator.saveReportInDb();
    }
}
