package com.nppgks.reports.scheduled_components;

import com.nppgks.reports.db.entity.ReportName;
import com.nppgks.reports.db.entity.ReportType;
import com.nppgks.reports.db.entity.TagData;
import com.nppgks.reports.db.entity.TagName;
import com.nppgks.reports.opc.OpcRequests;
import com.nppgks.reports.service.db_services.ReportNameService;
import com.nppgks.reports.service.db_services.ReportTypeService;
import com.nppgks.reports.service.db_services.TagDataService;
import com.nppgks.reports.service.db_services.TagNameServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ScheduledTasks {
    private static final String HOUR_REPORT_TYPE_ID = "hour";

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
            LocalDateTime startDt = LocalDateTime.now(); // заглушка, реализация потом будет
            LocalDateTime endDt = LocalDateTime.now(); // заглушка, реализация потом будет
            ReportName reportName = new ReportName(null, name, LocalDateTime.now(), startDt, endDt, hourReportType);
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
