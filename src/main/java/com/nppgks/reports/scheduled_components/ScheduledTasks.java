package com.nppgks.reports.scheduled_components;

import com.nppgks.reports.entity.ReportName;
import com.nppgks.reports.entity.ReportType;
import com.nppgks.reports.entity.TagData;
import com.nppgks.reports.entity.TagName;
import com.nppgks.reports.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ScheduledTasks {
    private static final Integer HOUR_REPORT_TYPE_ID = 1;

    private final TagNameServiceImpl tagNameService;

    private final ReportTypeService reportTypeService;

    private final ReportNameService reportNameService;

    private final TagDataService tagDataService;

    private final OpcRequests opcRequests;

//    @Scheduled(cron = "0 0 0/1 * * ?") // every hour
//    @Scheduled(cron = "0 0/1 * * * ?") // every minute
    public List<TagData> generateTagDataEveryHour() {
        ReportType hourReportType = reportTypeService.getReportTypeById(HOUR_REPORT_TYPE_ID).get();
        if(hourReportType.getActive()){
            List<TagName> tagNames = tagNameService.getAllTagNamesByReportType(hourReportType);
            String name = "Часовой отчет за "+ LocalTime.now().getHour()+" часов";
            ReportName reportName = new ReportName(null, name, LocalDateTime.now(), hourReportType);
            reportNameService.saveReportName(reportName);

            List<String> tagNamesStr = tagNames.stream()
                    .map(TagName::getName)
                    .toList();
            Map<String, String> tagDataFromOPC = opcRequests.getTagDataFromOpc(tagNamesStr);
            return tagDataService.saveTagDataMapByReportName(tagDataFromOPC, reportName, LocalDateTime.now());
        }
        return null;
    }
}
