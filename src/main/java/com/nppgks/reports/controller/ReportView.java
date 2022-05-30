package com.nppgks.reports.controller;

import com.nppgks.reports.dto.TagDataDto;
import com.nppgks.reports.entity.ReportName;
import com.nppgks.reports.service.ReportNameService;
import com.nppgks.reports.service.ReportService;
import com.nppgks.reports.service.TagDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/startPage")
public class ReportView {

    private final ReportNameService reportNameService;
    private final TagDataService tagDataService;
    private final ReportService reportService;

    @Autowired
    public ReportView(ReportNameService reportNameService, TagDataService tagDataService, ReportService reportService) {
        this.reportNameService = reportNameService;
        this.reportService = reportService;
        this.tagDataService = tagDataService;
    }

    @GetMapping
    public String getStartPage(ModelMap model){
        setCommonParams(model, true);
        return "blog";
    }
    @GetMapping("/reportName/{reportTypeId}")
    public String getReportName(ModelMap modelMap,
                                @PathVariable(name = "reportTypeId") Long reportTypeId){
        List<ReportName> all = reportNameService.findByReportTypeId(reportTypeId);
        modelMap.put("reportNames", all);
        setCommonParams(modelMap, false);
        return "blog";
    }
    @GetMapping("/reportName/{date}/{id}")
    @ResponseBody
    public List<ReportName> getReport(ModelMap modelMap, @PathVariable String date, @PathVariable Long id){
        List<ReportName> reportNames = reportNameService.getReportNameByDateAndReportId(id,date);
        return reportNames;
    }

    @GetMapping("/tagData/{reportNameId}")
    @ResponseBody
    public List<TagDataDto> getTagData(@PathVariable Long reportNameId){
        List<TagDataDto> tagDataDto = tagDataService.getDataForReport(reportNameId);
        return tagDataDto;
    }
    void setCommonParams(ModelMap model, boolean defaultView){
        if(defaultView){
            model.put("reportTypes", reportService.getAllReportTypes());
            model.put("reportNames", reportNameService.findAll());
        }
        else{
            model.put("reportTypes", reportService.getAllReportTypes());
        }
    }
}
