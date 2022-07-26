package com.nppgks.reports.scheduled_components;

import com.nppgks.reports.entity.ReportName;
import com.nppgks.reports.entity.ReportType;
import com.nppgks.reports.entity.TagData;
import com.nppgks.reports.entity.TagName;
import com.nppgks.reports.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Component
public class ScheduledTasks {

    private TagNameServiceImpl tagNameService;

    private ReportNameService reportNameService;

    private ReportTypeService reportTypeService;

    private TagDataService tagDataService;

    private OpcRequests opcRequests;

    @Autowired
    public ScheduledTasks(TagNameServiceImpl tagNameService, ReportNameService reportNameService, ReportTypeService reportTypeService, TagDataService tagDataService, OpcRequests opcRequests) {
        this.tagNameService = tagNameService;
        this.reportNameService = reportNameService;
        this.reportTypeService = reportTypeService;
        this.tagDataService = tagDataService;
        this.opcRequests = opcRequests;
    }

//    @Scheduled(cron = "0 0 0/1 * * ?") // every hour
//    @Scheduled(cron = "0 0/1 * * * ?") // every minute
    public List<TagData> generateTagDataEveryHour() {
        ReportType hourReportType = reportTypeService.getReportTypeById(1).get();
        List<TagName> tagNames = tagNameService.getAllTagNames();
        String name = "Часовой отчет за "+ LocalTime.now().getHour()+" часов";
        ReportName reportName = new ReportName(null, name, LocalDateTime.now(), hourReportType);
        reportNameService.saveReportName(reportName);

        List<String> tagNamesStr = tagNames.stream()
                .map(tagName ->
                    tagName.getName())
                .toList();
        Map<String, String> tagDataFromOPC = opcRequests.getTagDataFromOpc(tagNamesStr);
        return tagDataService.saveTagDataMapByReportName(tagDataFromOPC, reportName, LocalDateTime.now());
    }
}
