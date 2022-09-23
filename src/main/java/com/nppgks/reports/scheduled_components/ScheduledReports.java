package com.nppgks.reports.scheduled_components;

import com.nppgks.reports.constants.ReportTypesEnum;
import com.nppgks.reports.constants.SettingsConstants;
import com.nppgks.reports.db.entity.ReportName;
import com.nppgks.reports.db.entity.ReportType;
import com.nppgks.reports.db.entity.TagData;
import com.nppgks.reports.db.entity.TagName;
import com.nppgks.reports.opc.OpcRequests;
import com.nppgks.reports.service.db_services.*;
import com.nppgks.reports.service.time_services.DateTimeRange;
import com.nppgks.reports.service.time_services.DateTimeRangeBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduledReports {

    ScheduledFuture<?> scheduledDailyReport;
    List<ScheduledFuture<?>> scheduledShiftReportList;
    ScheduledFuture<?> scheduledMonthReport;
    ScheduledFuture<?> scheduledYearReport;
    private final TaskScheduler taskScheduler;

    private final TagNameServiceImpl tagNameService;

    private final ReportTypeService reportTypeService;

    private final ReportNameService reportNameService;

    private final TagDataService tagDataService;

    private final SettingsService settingsService;

    private final OpcRequests opcRequests;

    @PostConstruct
    public void initSchedule() {
        scheduledDailyReport = scheduleDailyReport(this::generateTagDataForDailyReport);

        //scheduleAllShiftReports();
    }

    private void scheduleAllShiftReports() {
        ReportType shiftReportType = reportTypeService.getReportTypeById(ReportTypesEnum.shift.name());
        if(shiftReportType.getActive()){
            LinkedHashMap<String, String> startShiftReportMap = settingsService.getMapValuesBySettingName(SettingsConstants.START_SHIFT_REPORT);
            for(Map.Entry<String, String> entry: startShiftReportMap.entrySet()){
                ScheduledFuture<?> scheduledShiftReport = scheduleShiftReport(
                        () -> generateTagDataForShiftReport(shiftReportType, entry.getKey()),
                        entry.getValue());
                scheduledShiftReportList.add(scheduledShiftReport);
            }
        }
    }

    @Scheduled(cron = "0 0 * * * ?") // every hour at 00 minutes
    @Transactional
    public List<TagData> generateTagDataEveryHour() {
        ReportType hourReportType = reportTypeService.getReportTypeById(ReportTypesEnum.hour.name());
        if(hourReportType.getActive()){
            LocalDateTime currentDt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
            DateTimeRange startEndDt = DateTimeRangeBuilder.buildStartEndDateForHourReport(currentDt);
            LocalDateTime startDt = startEndDt.getStartDateTime();
            String name = String.format("Часовой отчет за %s %s", startDt.toLocalTime().truncatedTo(ChronoUnit.MINUTES), startDt.toLocalDate());
            return createAndSaveTagData(hourReportType, currentDt, startEndDt, name);
        }
        return List.of();
    }

    @Transactional
    public List<TagData> generateTagDataForDailyReport() {
        log.info("Создание суточного отчета...");
        ReportType dailyReportType = reportTypeService.getReportTypeById(ReportTypesEnum.daily.name());
        if(dailyReportType.getActive()){
            LocalDateTime currentDt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
            DateTimeRange startEndDt = DateTimeRangeBuilder.buildStartEndDateForDailyReport(currentDt);
            LocalDateTime startDt = startEndDt.getStartDateTime();
            String reportName = String.format("Суточный отчет за %s", startDt.toLocalDate());
            return createAndSaveTagData(dailyReportType, currentDt, startEndDt, reportName);
        }
        return List.of();
    }

    @Transactional
    public List<TagData> generateTagDataForShiftReport(ReportType reportType, String shiftNum) {
        log.info("Создание сменного отчета...");
        LocalDateTime currentDt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        LinkedHashMap<String, String> startShiftReportMap = settingsService.getMapValuesBySettingName(SettingsConstants.START_SHIFT_REPORT);

        DateTimeRange startEndDt = DateTimeRangeBuilder.buildStartEndDateForShiftReport(startShiftReportMap, shiftNum, currentDt);
        LocalDateTime startDt = startEndDt.getStartDateTime();
        String reportNameStr = String.format("Сменный отчет за %s смену %s", shiftNum, startDt.toLocalDate());

        return createAndSaveTagData(reportType, currentDt, startEndDt, reportNameStr);
    }

    private List<TagData> createAndSaveTagData(ReportType reportType, LocalDateTime currentDt, DateTimeRange startEndDt, String reportNameStr) {
        List<TagName> tagNames = tagNameService.getAllTagNamesByReportType(reportType);
        LocalDateTime startDt = startEndDt.getStartDateTime();
        LocalDateTime endDt = startEndDt.getEndDateTime();
        ReportName reportName = new ReportName(null,
                reportNameStr,
                currentDt,
                startDt,
                endDt,
                reportType);
        reportNameService.saveReportName(reportName);

        List<String> tagNamesStr = tagNames.stream()
                .map(TagName::getName)
                .toList();
        Map<String, String> tagDataFromOPC = opcRequests.getTagDataFromOpc(tagNamesStr);
        return tagDataService.saveTagDataMapByReportName(tagDataFromOPC, reportName, currentDt);
    }

    // every day at hour:minute
    public ScheduledFuture<?> scheduleDailyReport(final Runnable task) {
        String startDailyReportStr = settingsService.getStringValueBySettingName(SettingsConstants.START_DAILY_REPORT);
        LocalTime startDailyReport = LocalTime.parse(startDailyReportStr);
        int hour = startDailyReport.getHour();
        int minute = startDailyReport.getMinute();
        String cron = String.format("0 %s %s * * *", minute, hour);
        return taskScheduler.schedule(task, new CronTrigger(cron));
    }

    public ScheduledFuture<?> scheduleShiftReport(final Runnable task, String startTimeStr) {
            LocalTime startTime = LocalTime.parse(startTimeStr);
            int hour = startTime.getHour();
            int minute = startTime.getMinute();
            String cron = String.format("0 %s %s * * *", minute, hour);
            return taskScheduler.schedule(task, new CronTrigger(cron));
    }

    public void rescheduleReports(List<String> reportTypes){
        for(String reportType:reportTypes){
            switch(reportType){
                case "daily" -> rescheduleDailyReport();
                case "shift" -> rescheduleShiftReport();
            }
        }
    }

    public void rescheduleDailyReport(){
        log.info("Изменение расписания создания суточных отчетов...");
        scheduledDailyReport.cancel(false);
        scheduledDailyReport = scheduleDailyReport(this::generateTagDataForDailyReport);
    }
    public void rescheduleShiftReport(){
        log.info("Изменение расписания создания сменных отчетов...");
        for(ScheduledFuture<?> scheduledShiftReport:scheduledShiftReportList){
            scheduledShiftReport.cancel(false);
        }
        scheduleAllShiftReports();
    }
}
