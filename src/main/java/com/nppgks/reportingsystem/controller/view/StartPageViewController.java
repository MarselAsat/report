package com.nppgks.reportingsystem.controller.view;

import com.nppgks.reportingsystem.db.operative_reports.entity.ReportName;
import com.nppgks.reportingsystem.service.dbservices.ReportNameService;
import com.nppgks.reportingsystem.service.dbservices.ReportTypeService;
import com.nppgks.reportingsystem.service.dbservices.calculation.CalcReportNameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class StartPageViewController {

    private final ReportTypeService reportTypeService;
    private final ReportNameService reportNameService;

    private final CalcReportNameService calcReportNameService;


    @GetMapping
    public String getStartPage(ModelMap model){
        setCommonParams(model);
        return "start-page";
    }

    @GetMapping(value = "/filter")
    public String getReportNameByDateAndReportType(ModelMap modelMap,
                                                   @RequestParam(required = false) LocalDate date,
                                                   @RequestParam(required = false) String reportTypeId){
        if(date == null && reportTypeId == null) {
            return "redirect:/";
        }
        else if(reportTypeId != null && reportTypeId.equals("poverki")) {
            var calcReportNames = calcReportNameService.findReportNameByDate(date);
            modelMap.put("reportNames", calcReportNames);
        }
        else if(reportTypeId != null){
            List<ReportName> reportNames = reportNameService.getReportNameByDateAndReportId(reportTypeId, date);
            modelMap.put("reportNames", reportNames);
        }

        setCommonParams(modelMap);
        return "start-page";
    }

    void setCommonParams(ModelMap model){
        model.put("reportTypes", reportTypeService.getAllActiveReportTypes());
    }
}
