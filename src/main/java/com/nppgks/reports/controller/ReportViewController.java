package com.nppgks.reports.controller;

import com.nppgks.reports.dto.TagDataDto;
import com.nppgks.reports.entity.ReportName;
import com.nppgks.reports.entity.ReportViewTagData;
import com.nppgks.reports.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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
        return tagDataService.getDataForReport(reportNameId);
    }

    @GetMapping(value = "/report/{reportNameId}")
    public String getReport(ModelMap modelMap,
                            @PathVariable Long reportNameId){
        ReportName reportName = reportNameService.getById(reportNameId);
        List<ReportViewTagData> reportViewTagData = tagDataService.getReportViewTagData(reportNameId);
        DateTimeRange dateTimeRange = DateTimeRangeBuilder
                .buildStartEndDateForDailyReport(reportName.getDtCreation());
        modelMap.put("reportViewTagData", reportViewTagData);
        modelMap.put("reportNameDtCreation", SingleDateTimeFormatter
                .formatToSinglePattern(reportName.getDtCreation()));
        modelMap.put("startReportDate", SingleDateTimeFormatter
                .formatToSinglePattern(dateTimeRange.getStartDateTime()));
        modelMap.put("endReportDate", SingleDateTimeFormatter
                .formatToSinglePattern(dateTimeRange.getEndDateTime()));
        return "daily-report-page";
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
