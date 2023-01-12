package com.nppgks.reportingsystem.controller.rest;

import com.nppgks.reportingsystem.dto.ReportTypeDto;
import com.nppgks.reportingsystem.service.dbservices.ReportTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reportTypes")
@RequiredArgsConstructor
public class ReportTypeApiController {

    private final ReportTypeService reportTypeService;

    @GetMapping
    public List<ReportTypeDto> getAllReportTypes() {
        return reportTypeService.getAllReportTypes();
    }
}
