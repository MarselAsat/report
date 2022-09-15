package com.nppgks.reports.controller;

import com.nppgks.reports.dto.TagDataDto;
import com.nppgks.reports.service.db_services.TagDataService;
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
        return tagDataService.getDataForReport(reportNameId);
    }
}
