package com.nppgks.reports.controller;

import com.nppgks.reports.dto.TagDataDto;
import com.nppgks.reports.entity.ReportName;
import com.nppgks.reports.service.ReportNameService;
import com.nppgks.reports.service.TagDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ReportView {

    private final ReportNameService reportService;
    private final TagDataService tagDataService;

    @Autowired
    public ReportView(ReportNameService reportService, TagDataService tagDataService) {
        this.reportService = reportService;
        this.tagDataService = tagDataService;
    }


    @GetMapping("/reportname/{reporttypeid}")
    public String getReportName(ModelMap modelMap,
                                @PathVariable(name = "reporttypeid") Long reportTypeId){
        List<ReportName> all = reportService.findByReportTypeId(reportTypeId);
        modelMap.put("report", all);
        return "reports";
    }

    @GetMapping("/tagdata/{reportnameid}")
    public String getTegData(ModelMap modelMap,
                             @PathVariable(name = "reportnameid") Long reportNameId){
        List<TagDataDto> tagDataDto = tagDataService.getDataForReport(reportNameId);
        modelMap.put("tagdata", tagDataDto);
        return "reports2";
    }


}
