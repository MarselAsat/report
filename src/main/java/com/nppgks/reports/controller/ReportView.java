package com.nppgks.reports.controller;

import com.nppgks.reports.dto.ReportTypeDto;
import com.nppgks.reports.dto.TagDataDto;
import com.nppgks.reports.dto.TagNameDto;
import com.nppgks.reports.entity.ReportName;
import com.nppgks.reports.service.ReportNameService;
import com.nppgks.reports.service.ReportTypeService;
import com.nppgks.reports.service.TagDataService;
import com.nppgks.reports.service.TagNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/startPage")
public class ReportView {

    private final ReportNameService reportNameService;
    private final TagDataService tagDataService;
    private final ReportTypeService reportTypeService;

    private final TagNameService tagNameService;

    @Autowired
    public ReportView(ReportNameService reportNameService,
                      TagDataService tagDataService,
                      ReportTypeService reportTypeService,
                      TagNameService tagNameService) {
        this.reportNameService = reportNameService;
        this.reportTypeService = reportTypeService;
        this.tagDataService = tagDataService;
        this.tagNameService = tagNameService;
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
            model.put("reportTypes", reportTypeService.getAllReportTypes());
            model.put("reportNames", reportNameService.findAll());
        }
        else{
            model.put("reportTypes", reportTypeService.getAllReportTypes());
        }
    }

    @GetMapping("/tagName/new")
    public String createNewTagName(ModelMap modelMap){
        List<ReportTypeDto> reporTypes = reportTypeService.getAllReportTypes();
        modelMap.put("reportTypes", reporTypes);
        return "newTagName";
    }

    @PostMapping("/tagName/new")
    @ResponseBody
    public String newTagName(@RequestBody TagNameDto tagName, ModelMap model){
        System.out.println(tagName.toString());
        boolean isSaved = tagNameService.saveTagName(tagName);
        String isSavedStr = isSaved ? "is saved":"isn't saved";
        String response = "Tag "+tagName.getName()+" "+isSavedStr;
        model.put("response", response);
        return response;
    }
}
