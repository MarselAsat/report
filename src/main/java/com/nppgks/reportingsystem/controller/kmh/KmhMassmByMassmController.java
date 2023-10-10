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
public class KmhMassmByMassmController {

    private final ManualReportGenerator reportGenerator;

    public KmhMassmByMassmController(@Qualifier("kmhMassmByMassmReportGenerator") ManualReportGenerator reportGenerator) {
        this.reportGenerator = reportGenerator;
    }

    @GetMapping("/massmByMassm")
    public String getMassmByMassmReportPage(ModelMap modelMap){
        List<ReportData> reportDataList = reportGenerator.generateReport();
        reportDataList.forEach(rd -> {
            Object value = ArrayParser.fromJsonToObject(rd.getData());
            modelMap.put(
                    rd.getTag().getPermanentName(), value);
        });
        modelMap.put("printSaveButtonsRequired", true);
        return "report_pages/kmh-massm-by-massm-report-page";
    }

    @ResponseBody
    @GetMapping("/massmByMassm/save")
    public String saveMassmByMassmReport() {
        return reportGenerator.saveReportInDb();
    }
}