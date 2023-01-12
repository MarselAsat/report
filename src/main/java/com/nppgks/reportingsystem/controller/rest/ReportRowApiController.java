package com.nppgks.reportingsystem.controller.rest;

import com.nppgks.reportingsystem.dto.ReportRowDto;
import com.nppgks.reportingsystem.service.dbservices.ReportRowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reportRows")
@RequiredArgsConstructor
public class ReportRowApiController {

    private final ReportRowService reportRowService;

    @GetMapping
    public List<ReportRowDto> getReportRows() {
        return reportRowService.getAllReportRows();
    }
}
