package com.nppgks.reportingsystem.reportgeneration.operative;

import com.nppgks.reportingsystem.constants.ReportTypesEnum;
import com.nppgks.reportingsystem.constants.SettingsConstants;
import com.nppgks.reportingsystem.db.operative_reports.entity.ReportName;
import com.nppgks.reportingsystem.db.operative_reports.entity.ReportType;
import com.nppgks.reportingsystem.db.operative_reports.entity.TagData;
import com.nppgks.reportingsystem.db.operative_reports.entity.TagName;
import com.nppgks.reportingsystem.exception.MissingDbDataException;
import com.nppgks.reportingsystem.opc.OpcRequests;
import com.nppgks.reportingsystem.service.dbservices.*;
import com.nppgks.reportingsystem.util.time.DateTimeRange;
import com.nppgks.reportingsystem.util.time.DateTimeRangeBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import static com.nppgks.reportingsystem.util.time.SingleDateTimeFormatter.formatToSinglePattern;

@Service
@RequiredArgsConstructor
@Slf4j
@Profile("scheduling")
//@DependsOn("liquibase") //let this class run @PostConstruct after liquibase
public class ReportsScheduler {

    private ReportsScheduler reportsScheduler;

    private RescheduleService rescheduleService;

    private final TaskScheduler taskScheduler;

    private final TagNameService tagNameService;

    private final ReportTypeService reportTypeService;

    private final ReportNameService reportNameService;

    private final TagDataService tagDataService;

    private final SettingsService settingsService;

    private final OpcRequests opcRequests;

    @Autowired
    public void setRescheduleService(RescheduleService rescheduleService){
        this.rescheduleService = rescheduleService;
    }

    // self-injection to make @Transactional work
    @Autowired
    public void setReportsScheduler(@Lazy ReportsScheduler reportsScheduler) {
        this.reportsScheduler = reportsScheduler;
    }


    @PostConstruct
    public void initSchedule() {
        rescheduleService.scheduledHourReport = scheduleHourReport(reportsScheduler::generateTagDataForHourReport);
        schedule2HourReport(reportsScheduler::generateTagDataFor2HourReport);
        rescheduleService.scheduledDailyReport = scheduleDailyReport(reportsScheduler::generateTagDataForDailyReport);
        rescheduleService.scheduledMonthReport = scheduleMonthReport(reportsScheduler::generateTagDataForMonthReport);
        rescheduleService.scheduledYearReport = scheduleYearReport(reportsScheduler::generateTagDataForYearReport);
        scheduleAllShiftReports();
    }

    // every hour at 00 minutes
    @Transactional
    public List<TagData> generateTagDataForHourReport() {
        log.info("Создание часового отчета...");
        ReportType hourReportType = reportTypeService.getReportTypeById(ReportTypesEnum.hour.name());
        if (hourReportType.getActive()) {
            LocalDateTime currentDt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
            DateTimeRange startEndDt = DateTimeRangeBuilder.buildStartEndDateForHourReport(currentDt);
            LocalDateTime startDt = startEndDt.getStartDateTime();
            String name = String.format("Часовой отчет за %s %s",
                    formatToSinglePattern(startDt.toLocalTime()),
                    formatToSinglePattern(startDt.toLocalDate())
            );
            return createAndSaveTagData(hourReportType, currentDt, startEndDt, name);
        }
        return List.of();
    }

    // every 2 hour at 00 minutes
    @Transactional
    public List<TagData> generateTagDataFor2HourReport() {
        log.info("Создание двухчасового отчета...");
        ReportType twoHourReportType = reportTypeService.getReportTypeById(ReportTypesEnum.twohour.name());
        if (twoHourReportType.getActive()) {
            LocalDateTime currentDt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
            DateTimeRange startEndDt = DateTimeRangeBuilder.buildStartEndDateFor2HourReport(currentDt);
            LocalDateTime startDt = startEndDt.getStartDateTime();
            LocalDateTime endDt = startEndDt.getEndDateTime();
            String name = String.format("Двухчасовой отчет за период с %s по %s %s",
                    formatToSinglePattern(startDt.toLocalTime()),
                    formatToSinglePattern(endDt.toLocalTime()),
                    formatToSinglePattern(startDt.toLocalDate())
            );
            return createAndSaveTagData(twoHourReportType, currentDt, startEndDt, name);
        }
        return List.of();
    }

    @Transactional
    public List<TagData> generateTagDataForDailyReport() {
        log.info("Создание суточного отчета...");
        ReportType dailyReportType = reportTypeService.getReportTypeById(ReportTypesEnum.daily.name());
        if (dailyReportType.getActive()) {
            LocalDateTime currentDt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
            DateTimeRange startEndDt = DateTimeRangeBuilder.buildStartEndDateForDailyReport(currentDt);
            LocalDateTime startDt = startEndDt.getStartDateTime();
            String reportName = String.format("Суточный отчет за %s", formatToSinglePattern(startDt.toLocalDate()));
            return createAndSaveTagData(dailyReportType, currentDt, startEndDt, reportName);
        }
        return List.of();
    }

    @Transactional
    public List<TagData> generateTagDataForMonthReport() {
        log.info("Создание месячного отчета...");
        ReportType monthReportType = reportTypeService.getReportTypeById(ReportTypesEnum.month.name());
        if (monthReportType.getActive()) {
            LocalDateTime currentDt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
            DateTimeRange startEndDt = DateTimeRangeBuilder.buildStartEndDateForMonthReport(currentDt);
            LocalDateTime startDt = startEndDt.getStartDateTime();
            String reportName = String.format("Месячный отчет за %s",
                    formatToSinglePattern(
                            YearMonth.of(
                                    startDt.getYear(),
                                    startDt.getMonthValue())));
            return createAndSaveTagData(monthReportType, currentDt, startEndDt, reportName);
        }
        return List.of();
    }

    @Transactional
    public List<TagData> generateTagDataForYearReport() {
        log.info("Создание годового отчета...");
        ReportType yearReportType = reportTypeService.getReportTypeById(ReportTypesEnum.year.name());
        if (yearReportType.getActive()) {
            LocalDateTime currentDt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
            DateTimeRange startEndDt = DateTimeRangeBuilder.buildStartEndDateForYearReport(currentDt);
            LocalDateTime startDt = startEndDt.getStartDateTime();
            String reportName = String.format("Годовой отчет за %s год", startDt.getYear());
            return createAndSaveTagData(yearReportType, currentDt, startEndDt, reportName);
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
        String reportNameStr = String.format("Сменный отчет за %s смену %s", shiftNum, formatToSinglePattern(startDt.toLocalDate()));

        return createAndSaveTagData(reportType, currentDt, startEndDt, reportNameStr);
    }

    private List<TagData> createAndSaveTagData(ReportType reportType, LocalDateTime currentDt, DateTimeRange startEndDt, String reportNameStr) {
        List<TagName> tagNames = tagNameService.getAllTagNamesByReportType(reportType);
        if(tagNames.isEmpty()){
            throw new MissingDbDataException("There is not a single "+reportType.getId()+" tag name in DB");
        }
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
        String cron = String.format("30 %s %s * * *", minute, hour);
        return taskScheduler.schedule(task, new CronTrigger(cron));
    }

    public ScheduledFuture<?> scheduleHourReport(final Runnable task) {
        return taskScheduler.schedule(task, new CronTrigger("30 0 * * * ?"));
    }

    public ScheduledFuture<?> schedule2HourReport(final Runnable task) {
        return taskScheduler.schedule(task, new CronTrigger("30 0 */2 * * ?"));
    }

    // every first day of month at hour:minute
    public ScheduledFuture<?> scheduleMonthReport(final Runnable task) {
        String startMonthReportStr = settingsService.getStringValueBySettingName(SettingsConstants.START_MONTH_REPORT);
        LocalTime startMonthReport = LocalTime.parse(startMonthReportStr);
        int hour = startMonthReport.getHour();
        int minute = startMonthReport.getMinute();
        String cron = String.format("30 %s %s 1 * *", minute, hour);
        return taskScheduler.schedule(task, new CronTrigger(cron));
    }

    // every first January at hour:minute
    public ScheduledFuture<?> scheduleYearReport(final Runnable task) {
        String startYearReportStr = settingsService.getStringValueBySettingName(SettingsConstants.START_YEAR_REPORT);
        LocalTime startYearReport = LocalTime.parse(startYearReportStr);
        int hour = startYearReport.getHour();
        int minute = startYearReport.getMinute();
        String cron = String.format("30 %s %s 1 JAN *", minute, hour);
        return taskScheduler.schedule(task, new CronTrigger(cron));
    }

    public void scheduleAllShiftReports() {
        ReportType shiftReportType = reportTypeService.getReportTypeById(ReportTypesEnum.shift.name());
        if (shiftReportType.getActive()) {
            LinkedHashMap<String, String> shiftNumToStartTime = moveShiftTime(settingsService
                    .getMapValuesBySettingName(SettingsConstants.START_SHIFT_REPORT));
            for (Map.Entry<String, String> entry : shiftNumToStartTime.entrySet()) {
                ScheduledFuture<?> scheduledShiftReport = scheduleShiftReport(
                        () -> reportsScheduler.generateTagDataForShiftReport(shiftReportType, entry.getKey()),
                        entry.getValue());
                rescheduleService.scheduledShiftReportList.add(scheduledShiftReport);
            }
        }
    }


    public LinkedHashMap<String, String> moveShiftTime(LinkedHashMap<String, String> shiftNumToStartTime){
        LinkedHashMap<String, String> shiftNumToStartTimeCopy = (LinkedHashMap<String, String>) shiftNumToStartTime.clone();
        int size = shiftNumToStartTimeCopy.size();
        if(size > 1){
            String startTimeFirst = shiftNumToStartTimeCopy.replace(1+"", shiftNumToStartTimeCopy.get(2+""));
            for(int i = 2; i < size; i++){
                shiftNumToStartTimeCopy.replace(i+"", shiftNumToStartTimeCopy.get(i+1+""));
            }
            shiftNumToStartTimeCopy.replace(size +"", startTimeFirst);
        }
        return shiftNumToStartTimeCopy;
    }

    // every day at hour:minute (hour and minute change depending on shift)
    private ScheduledFuture<?> scheduleShiftReport(final Runnable task, String startTimeStr) {
        LocalTime startTime = LocalTime.parse(startTimeStr);
        int hour = startTime.getHour();
        int minute = startTime.getMinute();
        String cron = String.format("30 %s %s * * *", minute, hour);
        return taskScheduler.schedule(task, new CronTrigger(cron));
    }
}
