package com.nppgks.reports.controller;

import com.nppgks.reports.dto.ManualTagNameDto;
import com.nppgks.reports.dto.ReportTypeDto;
import com.nppgks.reports.dto.TagNameDto;
import com.nppgks.reports.service.*;
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

    private final TagNameService<TagNameDto, Long> tagNameService;
    private final TagNameService<ManualTagNameDto, String> manualTagNameService;

    @Autowired
    public AdminController(ReportNameService reportNameService,
                           TagDataService tagDataService,
                           ReportTypeService reportTypeService,
                           TagNameService<TagNameDto, Long> tagNameService, TagNameService<ManualTagNameDto, String> manualTagNameService) {
        this.reportNameService = reportNameService;
        this.reportTypeService = reportTypeService;
        this.tagDataService = tagDataService;
        this.tagNameService = tagNameService;
        this.manualTagNameService = manualTagNameService;
    }

    @GetMapping("/tagName/new")
    public String createNewTagName(ModelMap modelMap){
        List<ReportTypeDto> reportTypes = reportTypeService.getAllReportTypes();
        modelMap.put("reportTypes", reportTypes);
        return "newTagName";
    }

    @PostMapping("/tagName/new")
    @ResponseBody
    public Long newTagName(@RequestBody TagNameDto tagName){
        return tagNameService.saveTagName(tagName);
    }

    @GetMapping("/tagName")
    public String getAllTagNames(ModelMap modelMap){
        List<TagNameDto> tagNames = tagNameService.getAllTagNamesDto();
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

    @DeleteMapping("/tagName/{id}")
    @ResponseBody
    public Map<Long, Boolean> deleteTagName(@PathVariable Long id){
        boolean isDeleted = tagNameService.deleteTagName(id);
        return Map.of(id, isDeleted);
    }
    @PostMapping("/manualTagName")
    @ResponseBody
    public Map<String, Boolean> updateManualTagNames(@RequestBody List<ManualTagNameDto> tagNames){
        return manualTagNameService.saveTagNames(tagNames);
    }

    @DeleteMapping("/manualTagName/{permanentName}")
    @ResponseBody
    public Map<String, Boolean> deleteManualTagName(@PathVariable String permanentName){
        boolean isDeleted = manualTagNameService.deleteTagName(permanentName);
        return Map.of(permanentName, isDeleted);
    }
    @GetMapping("/manualTagName")
    public String getAllManualTagNames(ModelMap modelMap){
        List<ManualTagNameDto> tagNames = manualTagNameService.getAllTagNamesDto();
        modelMap.put("tagNames", tagNames);
        return "edit-manual-tag-name-table";
    }
}
