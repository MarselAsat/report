package com.nppgks.reports.controller;

import com.nppgks.reports.dto.calc.CalcTagNameDto;
import com.nppgks.reports.dto.ReportTypeDto;
import com.nppgks.reports.dto.TagNameDto;
import com.nppgks.reports.service.db_services.*;
import com.nppgks.reports.service.db_services.calculation.CalcTagNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ReportTypeService reportTypeService;
    private final TagNameService tagNameService;
    private final CalcTagNameService calcTagNameService;

    @Autowired
    public AdminController(ReportTypeService reportTypeService, TagNameService tagNameService, CalcTagNameService calcTagNameService) {
        this.reportTypeService = reportTypeService;
        this.calcTagNameService = calcTagNameService;
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
    @PostMapping("/calcTagName")
    @ResponseBody
    public Map<Integer, Boolean> updatecalcTagNames(@RequestBody List<CalcTagNameDto> tagNames){
        return calcTagNameService.updateTagNames(tagNames);
    }

    @DeleteMapping("/calcTagName/{id}")
    @ResponseBody
    public Map<Integer, Boolean> deletecalcTagName(@PathVariable Integer id){
        boolean isDeleted = calcTagNameService.deleteTagName(id);
        return Map.of(id, isDeleted);
    }

    @GetMapping("/calcTagName")
    public String getAllcalcTagNames(ModelMap modelMap){
        List<CalcTagNameDto> tagNames = calcTagNameService.getAllTagNames();
        modelMap.put("tagNames", tagNames);
        return "edit-calc-tag-name-table";
    }
}
