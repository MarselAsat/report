package com.nppgks.reports.controller;

import com.nppgks.reports.dto.TagDataDto;
import com.nppgks.reports.entity.ReportName;
import com.nppgks.reports.entity.TagData;
import com.nppgks.reports.service.ReportNameService;
import com.nppgks.reports.service.TagDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    private final ReportNameService reportService;
    private final TagDataService tagDataService;

    @Autowired
    public TestController(ReportNameService reportService, TagDataService tagDataService) {
        this.reportService = reportService;
        this.tagDataService = tagDataService;
    }

    @GetMapping("reportName/{reportTypeId}")
    public List<ReportName> getReportNameByReportTypeId(@PathVariable(name = "reportTypeId") Integer id){
        List<ReportName> reportNames = reportService.findByReportTypeId(id);
        reportNames.get(0).getReportType();
        return reportNames;
    }

    @GetMapping("/tagData/{reportNameId}")
    public List<TagDataDto> getTegData(@PathVariable(name = "reportNameId") Long id){
        List<TagDataDto> tagDataDto = tagDataService.getDataForReport(id);
        return tagDataDto;
    }

    @GetMapping("/reportName/{date}/{reportTypeId}")
    public List<ReportName> getReportNameByDateAndReportId(@PathVariable Integer reportTypeId,
                                                           @PathVariable String date){
        return reportService.getReportNameByDateAndReportId(reportTypeId, date);
    }
}
