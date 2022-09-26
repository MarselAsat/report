package com.nppgks.reports.controller;

import com.nppgks.reports.dto.TagDataDto;
import com.nppgks.reports.db.entity.ReportName;
import com.nppgks.reports.service.db_services.ReportNameService;
import com.nppgks.reports.service.db_services.TagDataService;
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
        return tagDataService.getDataForReport(id);
    }

    @GetMapping("/reportName/{date}/{reportTypeId}")
    public List<ReportName> getReportNameByDateAndReportId(@PathVariable String reportTypeId,
                                                           @PathVariable String date){
        return reportService.getReportNameByDateAndReportId(reportTypeId, date);
    }
}
