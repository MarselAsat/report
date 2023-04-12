package com.nppgks.reportingsystem.reportgeneration.operative;

import com.nppgks.reportingsystem.constants.ReportTypesEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

@Service
@Slf4j
public class RescheduleService {

    ScheduledFuture<?> scheduledHourReport;
    ScheduledFuture<?> scheduledDailyReport;
    List<ScheduledFuture<?>> scheduledShiftReportList = new ArrayList<>();
    ScheduledFuture<?> scheduledMonthReport;
    ScheduledFuture<?> scheduledYearReport;

    private ReportsScheduler reportsScheduler;

    @Autowired
    public void setScheduledReports(@Lazy ReportsScheduler reportsScheduler){
        this.reportsScheduler = reportsScheduler;
    }

    public void rescheduleReports(List<String> reportTypes) {
        for (String reportTypeId : reportTypes) {
            switch (ReportTypesEnum.valueOf(reportTypeId)) {
                case daily -> rescheduleDailyReport();
                case shift -> rescheduleShiftReport();
                case month -> rescheduleMonthReport();
                case year -> rescheduleYearReport();
            }
        }
    }

    private void rescheduleDailyReport() {
        log.info("Изменение расписания создания суточных отчетов...");
        scheduledDailyReport.cancel(false);
        scheduledDailyReport = reportsScheduler.scheduleDailyReport(
                reportsScheduler::generateTagDataForDailyReport);
    }

    private void rescheduleMonthReport() {
        log.info("Изменение расписания создания месячных отчетов...");
        scheduledMonthReport.cancel(false);
        scheduledMonthReport = reportsScheduler.scheduleMonthReport(
                reportsScheduler::generateTagDataForMonthReport);
    }

    private void rescheduleYearReport() {
        log.info("Изменение расписания создания годовых отчетов...");
        scheduledYearReport.cancel(false);
        scheduledYearReport = reportsScheduler.scheduleYearReport(
                reportsScheduler::generateTagDataForYearReport);
    }

    private void rescheduleShiftReport() {
        log.info("Изменение расписания создания сменных отчетов...");
        for (ScheduledFuture<?> scheduledShiftReport : scheduledShiftReportList) {
            scheduledShiftReport.cancel(false);
        }
        reportsScheduler.scheduleAllShiftReports();
    }
}
