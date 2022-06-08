package com.nppgks.reports.controller;

import com.nppgks.reports.dto.TagDataDto;
import com.nppgks.reports.service.TagDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final TagDataService tagDataService;

    @Autowired
    public ApiController(TagDataService tagDataService) {
        this.tagDataService = tagDataService;
    }

    @GetMapping("/tagData/{reportNameId}")
    public List<TagDataDto> getTagData(@PathVariable Long reportNameId){
        List<TagDataDto> tagDataDto = tagDataService.getDataForReport(reportNameId);
        return tagDataDto;
    }
}
