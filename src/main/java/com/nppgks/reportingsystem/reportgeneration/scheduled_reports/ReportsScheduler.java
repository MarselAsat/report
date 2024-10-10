package com.nppgks.reportingsystem.reportgeneration.scheduled_reports;

import com.nppgks.reportingsystem.constants.ReportTypesEnum;
import com.nppgks.reportingsystem.constants.SettingsConstants;
import com.nppgks.reportingsystem.db.common.entity.Settings;
import com.nppgks.reportingsystem.db.common.repository.SettingsRepository;
import com.nppgks.reportingsystem.db.scheduled_reports.entity.Report;
import com.nppgks.reportingsystem.db.scheduled_reports.entity.ReportType;
import com.nppgks.reportingsystem.db.scheduled_reports.entity.ReportData;
import com.nppgks.reportingsystem.db.scheduled_reports.entity.Tag;
import com.nppgks.reportingsystem.exception.MissingDbDataException;
import com.nppgks.reportingsystem.exception.MissingOpcTagException;
import com.nppgks.reportingsystem.exception.ReportTypeIsNotActiveException;
import com.nppgks.reportingsystem.opcservice.OpcServiceRequests;
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
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Repository;
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
import java.util.Optional;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static com.nppgks.reportingsystem.util.time.SingleDateTimeFormatter.formatToSinglePattern;

@Service
@RequiredArgsConstructor
@Slf4j
@Profile("scheduling")
public class ReportsScheduler {

    private ReportsScheduler reportsScheduler;

    private RescheduleService rescheduleService;

    private final TaskScheduler taskScheduler;

    private final TagService tagService;

    private final ReportTypeService reportTypeService;

    private final ReportService reportService;

    private final ReportDataService reportDataService;

    private final SettingsService settingsService;

    private final OpcServiceRequests opcServiceRequests;





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
        rescheduleService.scheduledHourReport = scheduleHourReport(reportsScheduler::generateReportDataForHourReport);
        schedule2HourReport(reportsScheduler::generateReportDataFor2HourReport);
        rescheduleService.scheduledDailyReport = scheduleDailyReport(reportsScheduler::generateReportDataForDailyReport);
        rescheduleService.scheduledMonthReport = scheduleMonthReport(reportsScheduler::generateReportDataForMonthReport);
        rescheduleService.scheduledYearReport = scheduleYearReport(reportsScheduler::generateReportDataForYearReport);
        scheduleAllShiftReports();
//        int minutes = 5; // Replace with the desired number of minutes
//        rescheduleService.scheduledMinuteReport = rescheduleMinuteReport(minutes);
        rescheduleService.scheduledMinuteReport = rescheduleMinuteReport(reportsScheduler::generateReportDataForMinuteReport);
    }
    // каждую минуту в 00 секунд
//    @Transactional
//    public List<ReportData> generateReportDataForMinuteReport(int minutes) {
//        log.info("Создание минутного отчета...");
//        ReportType minuteReportType = reportTypeService.getReportTypeById(ReportTypesEnum.minute.name());
//        if (minuteReportType.getActive()) {
//            LocalDateTime currentDt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
//            LocalDateTime startDt = currentDt.minusMinutes(minutes); // Используйте minutes здесь
//            String name = String.format("Минутный отчет за %s",
//                    formatToSinglePattern(startDt.toLocalTime()),
//                    formatToSinglePattern(currentDt.toLocalTime()),
//                    formatToSinglePattern(startDt.toLocalDate())
//            );
//            return createAndSaveReportData(minuteReportType, currentDt, new DateTimeRange(startDt, currentDt), name);
//        }
//        return List.of();
//    }

// РАБОЧИЙ КОД
        public void resetMinuteReport() {
        // Отмена задачи
        if (rescheduleService.scheduledMinuteReport != null && !rescheduleService.scheduledMinuteReport.isCancelled()) {
            rescheduleService.scheduledMinuteReport.cancel(false);
        }

        // Получение нового интервала из настроек
        Proverka minuteReportInterval = settingsService.getValuesBySettingName("minute report columns");
        Integer interval = minuteReportInterval.getTime();

        // Создание нового cron-выражения на основе нового интервала
        String cron = String.format("0 */%d * * * *", interval); // Запускает задачу каждые interval минут

        // Планирование новой задачи с новым cron-выражением
        rescheduleService.scheduledMinuteReport = taskScheduler.schedule(reportsScheduler::generateReportDataForMinuteReport, new CronTrigger(cron));
    }

//РАБОЧИЙ КОД
  public List<ReportData> generateReportDataForMinuteReport() {
        log.info("Создание минутного отчета...");
        ReportType minuteReportType = reportTypeService.getReportTypeById(ReportTypesEnum.minute.name());
        if (minuteReportType.getActive()) {
            try {
                // Получение настройки "time" непосредственно из базы данных
                Proverka minuteReportInterval = settingsService.getValuesBySettingName("minute report columns");
                Integer interval = minuteReportInterval.getTime();

                // Check if interval is null before proceeding
                if (interval == null) {
                    log.error("Значение интервала равно null");
                    return List.of();
                }
                LocalDateTime currentDt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
                LocalDateTime startDt = currentDt.minusMinutes(interval); // Используйте interval здесь
                String name = getMinuteReportName(interval, currentDt, startDt);
                return createAndSaveReportData(minuteReportType, currentDt, new DateTimeRange(startDt, currentDt), name);
            } catch (NumberFormatException e) {
                log.error("Не удалось преобразовать значение интервала в число", e);
                return List.of();
            } catch (RuntimeException e) {
                log.error("Ошибка при получении интервала отчета", e);
                return List.of();
            }
        }
        return List.of();
    }



    @Transactional
    public List<ReportData> generateReportDataForMinuteReport(int minutes) {
        log.info("Создание минутного отчета...");
        ReportType minuteReportType = reportTypeService.getReportTypeById(ReportTypesEnum.minute.name());
        if (minuteReportType.getActive()) {
            LocalDateTime currentDt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
            LocalDateTime startDt = currentDt.minusMinutes(minutes); // Используйте minutes здесь
            String name = getMinuteReportName(minutes, currentDt, startDt);
            return createAndSaveReportData(minuteReportType, currentDt, new DateTimeRange(startDt, currentDt), name);
        }
        return List.of();
    }

    // every hour at 00 minutes
    @Transactional
    public List<ReportData> generateReportDataForHourReport() {
        log.info("Создание часового отчета...");
        ReportType hourReportType = reportTypeService.getReportTypeById(ReportTypesEnum.hour.name());
        if (hourReportType.getActive()) {
            LocalDateTime currentDt = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
            DateTimeRange startEndDt = DateTimeRangeBuilder.buildStartEndDateForHourReport(currentDt);
            LocalDateTime startDt = startEndDt.getStartDateTime();
            String name = String.format("Часовой отчет за %s",
                    formatToSinglePattern(startDt.toLocalTime())
            );
            return createAndSaveReportData(hourReportType, currentDt, startEndDt, name);
        }
        throw new ReportTypeIsNotActiveException(hourReportType.getName());
    }

    // every 2 hour at 00 minutes
    @Transactional
    public List<ReportData> generateReportDataFor2HourReport() {
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
            return createAndSaveReportData(twoHourReportType, currentDt, startEndDt, name);
        }
        throw new ReportTypeIsNotActiveException(twoHourReportType.getName());
    }

    @Transactional
    public List<ReportData> generateReportDataForDailyReport() {
        log.info("Создание суточного отчета...");
        ReportType dailyReportType = reportTypeService.getReportTypeById(ReportTypesEnum.daily.name());
        if (dailyReportType.getActive()) {
            LocalDateTime currentDt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
            DateTimeRange startEndDt = DateTimeRangeBuilder.buildStartEndDateForDailyReport(currentDt);
            LocalDateTime startDt = startEndDt.getStartDateTime();
            String reportName = String.format("Суточный отчет за %s", formatToSinglePattern(startDt.toLocalDate()));
            return createAndSaveReportData(dailyReportType, currentDt, startEndDt, reportName);
        }
        throw new ReportTypeIsNotActiveException(dailyReportType.getName());
    }

    @Transactional
    public List<ReportData> generateReportDataForMonthReport() {
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
            return createAndSaveReportData(monthReportType, currentDt, startEndDt, reportName);
        }
        throw new ReportTypeIsNotActiveException(monthReportType.getName());
    }

    @Transactional
    public List<ReportData> generateReportDataForYearReport() {
        log.info("Создание годового отчета...");
        ReportType yearReportType = reportTypeService.getReportTypeById(ReportTypesEnum.year.name());
        if (yearReportType.getActive()) {
            LocalDateTime currentDt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
            DateTimeRange startEndDt = DateTimeRangeBuilder.buildStartEndDateForYearReport(currentDt);
            LocalDateTime startDt = startEndDt.getStartDateTime();
            String reportName = String.format("Годовой отчет за %s год", startDt.getYear());
            return createAndSaveReportData(yearReportType, currentDt, startEndDt, reportName);
        }
        throw new ReportTypeIsNotActiveException(yearReportType.getName());
    }

    @Transactional
    public List<ReportData> generateReportDataForShiftReport(ReportType reportType, String shiftNum) {
        log.info("Создание сменного отчета...");
        LocalDateTime currentDt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        LinkedHashMap<String, String> startShiftReportMap = settingsService.getMapValuesBySettingName(SettingsConstants.START_SHIFT_REPORT);

        DateTimeRange startEndDt = DateTimeRangeBuilder.buildStartEndDateForShiftReport(startShiftReportMap, shiftNum, currentDt);
        LocalDateTime startDt = startEndDt.getStartDateTime();
        String reportNameStr = String.format("Сменный отчет за %s смену %s", shiftNum, formatToSinglePattern(startDt.toLocalDate()));

        return createAndSaveReportData(reportType, currentDt, startEndDt, reportNameStr);
    }

    private List<ReportData> createAndSaveReportData(ReportType reportType, LocalDateTime currentDt, DateTimeRange startEndDt, String reportName) {
        List<Tag> tags = tagService.getAllTagsByReportType(reportType);
        if(tags.isEmpty()){
            throw new MissingDbDataException("Не получилось сгенерировать %s отчет. Нет ни одного тега в БД с таким типом отчета.".formatted(reportType.getName()));
        }
        LocalDateTime startDt = startEndDt.getStartDateTime();
        LocalDateTime endDt = startEndDt.getEndDateTime();
        Report report = new Report(null,
                reportName,
                currentDt,
                startDt,
                endDt,
                reportType);
        reportService.saveReport(report);

        List<String> tagAddresses = tags.stream()
                .map(Tag::getAddress)
                .toList();

        log.info("\nВ OPC сервис были отправлены следующие теги: \n"+tagAddresses);
        Map<String, String> tagValuesFromOPC = opcServiceRequests.getTagValuesFromOpc(tagAddresses);
        log.info("\nИз OPC сервиса были получены следующие значения тегов: \n"+tagValuesFromOPC);

        if(tagValuesFromOPC.isEmpty()) {
            throw new MissingOpcTagException("Не получилось сгенерировать %s отчет. Из OPC сервера вернулись 0 значений тегов по следующим адресам: %s".formatted(reportType.getName(), tagAddresses.toString()));
        }
        return reportDataService.saveReportValuesForReport(tagValuesFromOPC, report, currentDt);
    }

    // every day at hour:minute
    public ScheduledFuture<?> scheduleDailyReport(final Runnable task) {
        Proverka startDailyReportStr = settingsService.getValuesBySettingName(SettingsConstants.DAILY_REPORT_COLUMNS);
        LocalTime startDailyReport = LocalTime.parse(startDailyReportStr.getStartTime());
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

//    public ScheduledFuture<?> rescheduleMinuteReport(int minutes) {
//        MinuteReportTask task = new MinuteReportTask(this, minutes);
//        return taskScheduler.schedule(task, new CronTrigger("0 * * * * *"));
//    }

//    public ScheduledFuture<?> rescheduleMinuteReport(final Runnable task) {
//        Proverka startMinuteReportStr = settingsService.getValuesBySettingName(SettingsConstants.MINUTE_REPORT_COLUMNS);
//        Integer delayMinuteTask = startMinuteReportStr.getTime();
//        StringBuffer stringBuffer = new StringBuffer();
//        for (int i = 0; i < 60; i++) {
//            if (i % delayMinuteTask == 0){
//                stringBuffer.append(i + ",");
//            }
//        }
//        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
//        String cron = String.format("0 %s * * * *", stringBuffer);
//        return taskScheduler.schedule(task, new CronTrigger(cron));
//    }
//РАБОЧИЙ КОД
public ScheduledFuture<?> rescheduleMinuteReport(final Runnable task) {
    Proverka minuteReportInterval = settingsService.getValuesBySettingName("minute report columns");
    Integer interval = minuteReportInterval.getTime();

    // Проверка на null и использование значения по умолчанию, если interval равно null
    if (interval == null) {
        interval = 60; // Значение по умолчанию, если interval равно null
    }

    String cron = String.format("0 */%d * * * *", interval);
    return taskScheduler.schedule(task, new CronTrigger(cron));
}


    // every first day of month at hour:minute
    public ScheduledFuture<?> scheduleMonthReport(final Runnable task) {
        Proverka startMonthReportStr = settingsService.getValuesBySettingName(SettingsConstants.MONTH_REPORT_COLUMNS);
        LocalTime startMonthReport = LocalTime.parse(startMonthReportStr.getStartTime());
        int hour = startMonthReport.getHour();
        int minute = startMonthReport.getMinute();
        String cron = String.format("30 %s %s 1 * *", minute, hour);
        return taskScheduler.schedule(task, new CronTrigger(cron));
    }

    // every first January at hour:minute
    public ScheduledFuture<?> scheduleYearReport(final Runnable task) {
        Proverka startYearReportStr = settingsService.getValuesBySettingName(SettingsConstants.YEAR_REPORT_COLUMNS);
        LocalTime startYearReport = LocalTime.parse(startYearReportStr.getStartTime());
        int hour = startYearReport.getHour();
        int minute = startYearReport.getMinute();
        String cron = String.format("30 %s %s 1 JAN *", minute, hour);
        return taskScheduler.schedule(task, new CronTrigger(cron));
    }

    public void scheduleAllShiftReports() {
        ReportType shiftReportType = reportTypeService.getReportTypeById(ReportTypesEnum.shift.name());
        if (shiftReportType.getActive()) {
            LinkedHashMap<String, String> shiftNumToStartTime = getShiftNumToEndTime(settingsService
                    .getMapValuesBySettingName(SettingsConstants.SHIFT_REPORT_COLUMNS));
            for (Map.Entry<String, String> entry : shiftNumToStartTime.entrySet()) {
                ScheduledFuture<?> scheduledShiftReport = scheduleShiftReport(
                        () -> reportsScheduler.generateReportDataForShiftReport(shiftReportType, entry.getKey()),
                        entry.getValue());
                rescheduleService.scheduledShiftReportList.add(scheduledShiftReport);
            }
        }
    }


    // В настройках сменного отчета указывается номер смены и время ее начала.
    // Отчет формируется в конце смены.
    // Поэтому, этот метод сдвигает номера смен относительно времени
    // и получается map, где key - это номер смены и value - время ее окончания
    // Например, ("1", "10:00),("2", "22:00") превращается в ("2", "10:00"), ("1", "22:00")
    public LinkedHashMap<String, String> getShiftNumToEndTime(LinkedHashMap<String, String> shiftNumToStartTime){
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

    private String getMinuteReportName(Integer interval, LocalDateTime currentDt,LocalDateTime startDt){
        String reportType = interval == 1 ? "минутный" : "минутных";
        String name = String.format("%d %s отчет за период с %s по %s",
                interval,
                reportType,
                formatToSinglePattern(startDt.toLocalTime()),
                formatToSinglePattern(currentDt.toLocalTime())
        );
        return name;
    }

}


