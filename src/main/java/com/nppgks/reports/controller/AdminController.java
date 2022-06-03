package com.nppgks.reports.controller;

import com.nppgks.reports.dto.ReportTypeDto;
import com.nppgks.reports.dto.TagNameDto;
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
@RequestMapping("/admin")
public class AdminController {

    private final ReportNameService reportNameService;
    private final TagDataService tagDataService;
    private final ReportTypeService reportTypeService;

    private final TagNameService tagNameService;

    @Autowired
    public AdminController(ReportNameService reportNameService,
                      TagDataService tagDataService,
                      ReportTypeService reportTypeService,
                      TagNameService tagNameService) {
        this.reportNameService = reportNameService;
        this.reportTypeService = reportTypeService;
        this.tagDataService = tagDataService;
        this.tagNameService = tagNameService;
    }

    @GetMapping("/tagName/new")
    public String createNewTagName(ModelMap modelMap){
        List<ReportTypeDto> reportTypes = reportTypeService.getAllReportTypes();
        modelMap.put("reportTypes", reportTypes);
        return "newTagName";
    }

    @PostMapping("/tagName/new")
    @ResponseBody
    public String newTagName(@RequestBody TagNameDto tagName){
        System.out.println(tagName.toString());
        boolean isSaved = tagNameService.saveTagName(tagName);
        String isSavedStr = isSaved ? "is saved":"isn't saved";
        String response = "Tag "+tagName.getName()+" "+isSavedStr;
        return isSaved+"";
    }

    @GetMapping("/tagName")
    public String getAllTagNames(ModelMap modelMap){
        List<TagNameDto> tagNames = tagNameService.getAllTagNames();
        List<ReportTypeDto> reportTypes = reportTypeService.getAllReportTypes();
        modelMap.put("tagNames", tagNames);
        modelMap.put("reportTypes", reportTypes);
        return "edit-tag-name-table";
    }
    @PostMapping("/tagName")
    @ResponseBody
    public Map<Long, Boolean> updateTagNames(@RequestBody List<TagNameDto> tagNames){
        return tagNameService.saveTagNames(tagNames);
    }
}
