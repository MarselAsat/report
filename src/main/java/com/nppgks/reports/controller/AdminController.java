package com.nppgks.reports.controller;

import com.nppgks.reports.dto.poverka.PoverkaTagNameDto;
import com.nppgks.reports.dto.ReportTypeDto;
import com.nppgks.reports.dto.TagNameDto;
import com.nppgks.reports.service.db_services.*;
import com.nppgks.reports.service.db_services.poverka.PoverkaTagNameService;
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
    private final PoverkaTagNameService poverkaTagNameService;

    @Autowired
    public AdminController(ReportTypeService reportTypeService, TagNameService tagNameService, PoverkaTagNameService poverkaTagNameService) {
        this.reportTypeService = reportTypeService;
        this.poverkaTagNameService = poverkaTagNameService;
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
    @PostMapping("/poverkaTagName")
    @ResponseBody
    public Map<Integer, Boolean> updatePoverkaTagNames(@RequestBody List<PoverkaTagNameDto> tagNames){
        return poverkaTagNameService.updateTagNames(tagNames);
    }

    @DeleteMapping("/poverkaTagName/{id}")
    @ResponseBody
    public Map<Integer, Boolean> deletePoverkaTagName(@PathVariable Integer id){
        boolean isDeleted = poverkaTagNameService.deleteTagName(id);
        return Map.of(id, isDeleted);
    }

    @GetMapping("/poverkaTagName")
    public String getAllPoverkaTagNames(ModelMap modelMap){
        List<PoverkaTagNameDto> tagNames = poverkaTagNameService.getAllTagNames();
        modelMap.put("tagNames", tagNames);
        return "edit-poverka-tag-name-table";
    }
}
