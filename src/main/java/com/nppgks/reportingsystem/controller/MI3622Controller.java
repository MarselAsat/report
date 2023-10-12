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
@RequestMapping("/mi3622")
public class MI3622Controller {
    private final ManualReportGenerator MI3622Generator;

    public MI3622Controller(@Qualifier("MI3622ReportGenerator") ManualReportGenerator reportGenerator) {
        this.MI3622Generator = reportGenerator;
    }

    @GetMapping
    public String calculateMI3622(ModelMap modelMap) {
        List<ReportData> reportDataList = MI3622Generator.generateReport();
        reportDataList.forEach(td -> {
            Object value = ArrayParser.fromJsonToObject(td.getData());
            modelMap.put(
                    td.getTag().getPermanentName(), value);
        });

        // Этот параметр нужен для сохранения отчета
        // (используется в обработчике нажатия на кнопку "Сохранить в БД" в save-report-in-db.js)
        modelMap.put("saveUrl", "/mi3622/save");

        // Этот параметр нужен для отображения кнопок "Сохранить в БД" и "Печать" после генерации отчета
        modelMap.put("printSaveButtonsRequired", true);

        return "report_pages/MI3622-report-page";
    }

    @ResponseBody
    @GetMapping("/save")
    public String saveMI3622Data() {
        return MI3622Generator.saveReportInDb();
    }
}
