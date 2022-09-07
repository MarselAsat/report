package com.nppgks.reports.controller;

import com.nppgks.reports.dto.TagDataDto;
import com.nppgks.reports.entity.ReportName;
import com.nppgks.reports.entity.ReportViewTagData;
import com.nppgks.reports.service.ReportNameService;
import com.nppgks.reports.service.ReportTypeService;
import com.nppgks.reports.service.TagDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class ReportViewController {

    private final ReportNameService reportNameService;
    private final TagDataService tagDataService;
    private final ReportTypeService reportTypeService;

    @Autowired
    public ReportViewController(ReportNameService reportNameService,
                                TagDataService tagDataService,
                                ReportTypeService reportTypeService) {
        this.reportNameService = reportNameService;
        this.reportTypeService = reportTypeService;
        this.tagDataService = tagDataService;
    }

    @GetMapping("/startPage")
    public String getStartPage(ModelMap model){
        setCommonParams(model, true);
        return "blog";
    }

    @GetMapping(value = "/startPage/filter")
    public String getReportNameByDateAndReportType(ModelMap modelMap,
                                      @RequestParam(required = false) String date,
                                      @RequestParam(required = false) Integer reportTypeId){
        if(Objects.equals(date, "")&&reportTypeId==null){
            return "redirect:/startPage";
        }
        List<ReportName> reportNames = reportNameService.getReportNameByDateAndReportId(reportTypeId, date);
        modelMap.put("reportNames", reportNames);
        setCommonParams(modelMap, false);
        return "blog";
    }

    @GetMapping("/tagData/{reportNameId}")
    @ResponseBody
    public List<TagDataDto> getTagData(@PathVariable Long reportNameId){
        List<TagDataDto> tagDataDto = tagDataService.getDataForReport(reportNameId);
        return tagDataDto;
    }

    @GetMapping(value = "/report/{reportNameId}")
    public String getReport(ModelMap modelMap,
                            @PathVariable Long reportNameId){
        Optional<ReportName> reportName = reportNameService.getById(reportNameId);
        List<ReportViewTagData> reportViewTagData = tagDataService.getReportViewTagData(reportNameId);
        modelMap.put("reportViewTagData", reportViewTagData);
        modelMap.put("reportName", reportName);
        return "report-page";
    }

    void setCommonParams(ModelMap model, boolean defaultView){
        if(defaultView){
            model.put("reportTypes", reportTypeService.getAllReportTypes());
            model.put("reportNames", reportNameService.findAll());
        }
        else{
            model.put("reportTypes", reportTypeService.getAllReportTypes());
        }
    }
}
