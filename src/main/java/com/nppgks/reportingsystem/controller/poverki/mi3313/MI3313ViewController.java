package com.nppgks.reportingsystem.controller.poverki.mi3313;

import com.nppgks.reportingsystem.service.dbservices.manual_reports.ManualReportDataService;
import com.nppgks.reportingsystem.service.dbservices.manual_reports.ManualReportService;
import com.nppgks.reportingsystem.util.time.SingleDateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class MI3313ViewController {

    private final ManualReportService manualReportService;
    private final ManualReportDataService manualReportDataService;
    @GetMapping(value = "/mi3313ManyEsrmReport/{reportId}")
    public String getMi3313Report(ModelMap modelMap,
                                  @PathVariable Long reportId) {
        var report = manualReportService.findReportById(reportId);

        LocalDate creationDate = report.getCreationDt().toLocalDate();

        modelMap.put("date", SingleDateTimeFormatter.formatMonthToRussian(creationDate));

        var reportDataList = manualReportDataService.getReportDataList(reportId);

        ModelMapFillerMi3313.fillForMI3313ManyEsrm(modelMap, reportDataList);

        return "report_pages/poverki/MI3313-many-esrm";
    }

    @GetMapping(value = "/mi3313OneEsrmReport/{reportId}")
    public String getMi3313ReportOneEsrm(ModelMap modelMap,
                                  @PathVariable Long reportId) {
        var report = manualReportService.findReportById(reportId);

        LocalDate creationDate = report.getCreationDt().toLocalDate();

        modelMap.put("date", SingleDateTimeFormatter.formatMonthToRussian(creationDate));

        var reportDataList = manualReportDataService.getReportDataList(reportId);

        ModelMapFillerMi3313.fillForMI3313OneEsrm(modelMap, reportDataList);

        return "report_pages/poverki/MI3313-one-esrm";
    }
}
