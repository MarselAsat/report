package com.nppgks.reportingsystem.controller;

import com.nppgks.reportingsystem.dto.ReportRowDto;
import com.nppgks.reportingsystem.dto.TagNameDto;
import com.nppgks.reportingsystem.dto.calc.CalcTagNameDto;
import com.nppgks.reportingsystem.dto.ReportTypeDto;
import com.nppgks.reportingsystem.service.dbservices.*;
import com.nppgks.reportingsystem.service.dbservices.calculation.CalcTagNameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ReportTypeService reportTypeService;

    private final ReportRowService reportRowService;
    private final TagNameService tagNameService;
    private final CalcTagNameService calcTagNameService;

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

    @GetMapping("/tag-name-editor")
    public String tagNameEditorView(ModelMap modelMap){
        modelMap.put("reportTypes",
                reportTypeService.getAllReportTypes().stream()
                .map(ReportTypeDto::getName)
                .toList());
        modelMap.put("reportRows",
                reportRowService.getAllReportRows().stream()
                .map(ReportRowDto::combineNameAndType)
                .toList());
        return "tag-names-editor";
    }
}
