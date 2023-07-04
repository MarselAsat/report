package com.nppgks.reportingsystem.controller.view;

import com.nppgks.reportingsystem.certification.CRCGenerator;
import com.nppgks.reportingsystem.constants.CalcMethod;
import com.nppgks.reportingsystem.db.operative_reports.entity.Report;
import com.nppgks.reportingsystem.service.dbservices.ReportService;
import com.nppgks.reportingsystem.service.dbservices.ReportTypeService;
import com.nppgks.reportingsystem.service.dbservices.calculation.CalcReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class StartPageViewController {

    private final BuildProperties buildProperties;
    private final ReportTypeService reportTypeService;
    private final ReportService reportService;

    private final CalcReportService calcReportService;


    @GetMapping
    public String getStartPage(ModelMap model){
        setCommonParams(model);
        return "start-page";
    }

    @GetMapping(value = "/filter")
    public String getReportByDateAndReportType(ModelMap modelMap,
                                               @RequestParam(required = false) LocalDate date,
                                               @RequestParam(required = false) String reportTypeId){
        if(date == null && reportTypeId == null) {
            return "redirect:/";
        }
        else if(reportTypeId != null && reportTypeId.equals("mi3622")) {
            var calcReports = calcReportService.findReportByDateAndType(date, CalcMethod.MI_3622.name());
            modelMap.put("reports", calcReports);
        }
        else if(reportTypeId != null && reportTypeId.equals("acceptanceAct")) {
            var calcReports = calcReportService.findReportByDateAndType(date, CalcMethod.ACCEPTANCE_ACT.name());
            modelMap.put("reports", calcReports);
        }
        else if(reportTypeId != null){
            List<Report> reports = reportService.getReportsByDateAndReportId(reportTypeId, date);
            modelMap.put("reports", reports);
        }

        setCommonParams(modelMap);
        return "start-page";
    }

    void setCommonParams(ModelMap model){
        model.put("version", buildProperties.get("version"));
        model.put("crc", CRCGenerator.getCertificationAlgorithmsCrc());
        model.put("reportTypes", reportTypeService.getAllActiveReportTypes());
    }
}
