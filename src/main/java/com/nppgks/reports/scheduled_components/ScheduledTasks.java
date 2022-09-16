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
import com.nppgks.reports.service.time_services.DateTimeRange;
import com.nppgks.reports.service.time_services.DateTimeRangeBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
            LocalDateTime currentDt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
            List<TagName> tagNames = tagNameService.getAllTagNamesByReportType(hourReportType);
            String name = "Часовой отчет за "+ currentDt.getHour()+" часов " + currentDt.toLocalDate();
            DateTimeRange startEndDt = DateTimeRangeBuilder.buildStartEndDateForHourReport(currentDt);
            ReportName reportName = new ReportName(null,
                    name,
                    currentDt,
                    startEndDt.getStartDateTime(),
                    startEndDt.getEndDateTime(),
                    hourReportType);
            reportNameService.saveReportName(reportName);

            List<String> tagNamesStr = tagNames.stream()
                    .map(TagName::getName)
                    .toList();
            Map<String, String> tagDataFromOPC = opcRequests.getTagDataFromOpc(tagNamesStr);
            return tagDataService.saveTagDataMapByReportName(tagDataFromOPC, reportName, currentDt);
        }
        return null;
    }
}
