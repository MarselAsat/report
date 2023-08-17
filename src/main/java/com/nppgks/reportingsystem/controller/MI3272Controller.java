package com.nppgks.reportingsystem.controller;

import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
import com.nppgks.reportingsystem.reportgeneration.poverki.ManualReportGenerator;
import com.nppgks.reportingsystem.util.ArrayParser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/mi3272")
public class MI3272Controller {

    private final ManualReportGenerator reportGenerator;

    public MI3272Controller(@Qualifier("MI3272ReportGenerator") ManualReportGenerator reportGenerator) {
        this.reportGenerator = reportGenerator;
    }

    @GetMapping
    public String getMI3272Page(ModelMap modelMap){
        List<ReportData> reportDataList = reportGenerator.generateReport();
        reportDataList.forEach(rd -> {
            Object value = ArrayParser.fromJsonToObject(rd.getData());
            modelMap.put(
                    rd.getTag().getPermanentName(), value);
        });
        modelMap.put("printSaveButtonsRequired", true);
        return "report_pages/MI3272-report-page";
    }

    @ResponseBody
    @GetMapping("/save")
    public String saveMI3272Data() {
        return reportGenerator.saveReportInDb();
    }
}
