package com.nppgks.reportingsystem.controller;

import com.nppgks.reportingsystem.dto.TagDataDto;
import com.nppgks.reportingsystem.db.recurring_reports.entity.ReportName;
import com.nppgks.reportingsystem.service.dbservices.ReportNameService;
import com.nppgks.reportingsystem.service.dbservices.TagDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final ReportNameService reportService;
    private final TagDataService tagDataService;

    @GetMapping("reportName/{reportTypeId}")
    public List<ReportName> getReportNameByReportTypeId(@PathVariable(name = "reportTypeId") String reportTypeId){
        return reportService.findByReportTypeId(reportTypeId);
    }

    @GetMapping("/tagData/{reportNameId}")
    public List<TagDataDto> getTegData(@PathVariable(name = "reportNameId") Long id){
        return tagDataService.getDataByReport(id);
    }
}
