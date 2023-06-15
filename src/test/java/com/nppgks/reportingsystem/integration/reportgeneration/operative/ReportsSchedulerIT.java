package com.nppgks.reportingsystem.integration.reportgeneration.operative;

import com.nppgks.reportingsystem.db.operative_reports.entity.Report;
import com.nppgks.reportingsystem.db.operative_reports.entity.ReportType;
import com.nppgks.reportingsystem.db.operative_reports.entity.ReportData;
import com.nppgks.reportingsystem.integration.IntegrationBaseTest;
import com.nppgks.reportingsystem.integration.annotation.ScheduledIT;
import com.nppgks.reportingsystem.opcservice.OpcServiceRequests;
import com.nppgks.reportingsystem.reportgeneration.operative.ReportsScheduler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.jdbc.Sql;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;


@ScheduledIT
@Sql({"classpath:sql/operative_reports/various_reports.sql"})
public class ReportsSchedulerIT extends IntegrationBaseTest {

    private final ReportsScheduler reportsScheduler;
    @SpyBean
    private OpcServiceRequests opcServiceRequests;

    @Autowired
    public ReportsSchedulerIT(ReportsScheduler reportsScheduler) {
        this.reportsScheduler = reportsScheduler;
    }

    @BeforeEach
    void init(){
        doReturn(Map.of("WinCC_OA.report_redu.save", "234", "WinCC_OA.report_redu.main", "890", "WinCC_OA.CRC.Calc_crc", "237"))
                .when(opcServiceRequests).getTagValuesFromOpc(Mockito.anyList());
    }
    @Test
    void generateReportDataForHourReport(){
        LocalDateTime currentDt = LocalDateTime.parse("2022-09-20T10:10:50");

        try (MockedStatic<LocalDateTime> mockedLocalDateTime = Mockito.mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS)) {
            mockedLocalDateTime.when(LocalDateTime::now).thenReturn(currentDt);

            List<ReportData> reportDataList = reportsScheduler.generateReportDataForHourReport();
            String reportName = reportDataList.get(0).getReport().getName();
            assertThat(reportName).isEqualTo("Часовой отчет за 09:00 20.09.2022");
            assertThat(reportDataList).hasSize(3);
            assertThat(reportDataList.get(0).getReport().getStartDt())
                    .isEqualTo(LocalDateTime.parse("2022-09-20T09:00"));

            assertThat(reportDataList.get(0).getReport().getEndDt())
                    .isEqualTo(LocalDateTime.parse("2022-09-20T10:00"));
        }
    }

    @Test
    void generateReportDataForHourReport2(){
        LocalDateTime currentDt = LocalDateTime.parse("2023-01-10T14:00:50");

        try (MockedStatic<LocalDateTime> mockedLocalDateTime = Mockito.mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS)) {
            mockedLocalDateTime.when(LocalDateTime::now).thenReturn(currentDt);

            List<ReportData> reportDataList = reportsScheduler.generateReportDataForHourReport();
            String reportName = reportDataList.get(0).getReport().getName();
            assertThat(reportName).isEqualTo("Часовой отчет за 13:00 10.01.2023");
            assertThat(reportDataList).hasSize(3);
            assertThat(reportDataList.get(0).getReport().getStartDt())
                    .isEqualTo(LocalDateTime.parse("2023-01-10T13:00"));

            assertThat(reportDataList.get(0).getReport().getEndDt())
                    .isEqualTo(LocalDateTime.parse("2023-01-10T14:00"));
        }
    }

    @Test
    void generateReportDataFor2HourReport(){
        LocalDateTime currentDt = LocalDateTime.parse("2023-01-10T14:00:50");

        try (MockedStatic<LocalDateTime> mockedLocalDateTime = Mockito.mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS)) {
            mockedLocalDateTime.when(LocalDateTime::now).thenReturn(currentDt);

            List<ReportData> reportDataList = reportsScheduler.generateReportDataFor2HourReport();
            String reportName = reportDataList.get(0).getReport().getName();
            assertThat(reportName).isEqualTo("Двухчасовой отчет за период с 12:00 по 14:00 10.01.2023");
            assertThat(reportDataList).hasSize(3);
            assertThat(reportDataList.get(0).getReport().getStartDt())
                    .isEqualTo(LocalDateTime.parse("2023-01-10T12:00"));

            assertThat(reportDataList.get(0).getReport().getEndDt())
                    .isEqualTo(LocalDateTime.parse("2023-01-10T14:00"));
        }
    }

    @Test
    void generateReportDataForDailyReport(){
        LocalDateTime currentDt = LocalDateTime.parse("2022-09-20T10:10:50");

        try (MockedStatic<LocalDateTime> mockedLocalDateTime = Mockito.mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS)) {
            mockedLocalDateTime.when(LocalDateTime::now).thenReturn(currentDt);

            List<ReportData> reportDataList = reportsScheduler.generateReportDataForDailyReport();
            Report report = reportDataList.get(0).getReport();
            assertThat(report.getName()).isEqualTo("Суточный отчет за 19.09.2022");
            assertThat(reportDataList).hasSize(3);
            assertThat(report.getStartDt())
                    .isEqualTo(LocalDateTime.parse("2022-09-19T10:00"));

            assertThat(report.getEndDt())
                    .isEqualTo(LocalDateTime.parse("2022-09-20T10:00"));
        }
    }

    @Test
    void generateReportDataForMonthReport(){
        LocalDateTime currentDt = LocalDateTime.parse("2022-09-01T12:10:50");

        try (MockedStatic<LocalDateTime> mockedLocalDateTime = Mockito.mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS)) {
            mockedLocalDateTime.when(LocalDateTime::now).thenReturn(currentDt);

            List<ReportData> reportDataList = reportsScheduler.generateReportDataForMonthReport();
            Report report = reportDataList.get(0).getReport();
            assertThat(report.getName()).isEqualTo("Месячный отчет за 08.2022");
            assertThat(reportDataList).hasSize(3);
            assertThat(report.getStartDt())
                    .isEqualTo(LocalDateTime.parse("2022-08-01T12:00"));

            assertThat(report.getEndDt())
                    .isEqualTo(LocalDateTime.parse("2022-09-01T12:00"));
        }
    }

    @Test
    void generateReportDataForYearReport(){
        LocalDateTime currentDt = LocalDateTime.parse("2022-01-01T11:10:50");

        try (MockedStatic<LocalDateTime> mockedLocalDateTime = Mockito.mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS)) {
            mockedLocalDateTime.when(LocalDateTime::now).thenReturn(currentDt);

            List<ReportData> reportDataList = reportsScheduler.generateReportDataForYearReport();
            Report report = reportDataList.get(0).getReport();
            assertThat(report.getName()).isEqualTo("Годовой отчет за 2021 год");
            assertThat(reportDataList).hasSize(3);
            assertThat(report.getStartDt())
                    .isEqualTo(LocalDateTime.parse("2021-01-01T11:00"));

            assertThat(report.getEndDt())
                    .isEqualTo(LocalDateTime.parse("2022-01-01T11:00"));
        }
    }

    @Test
    void moveShiftNum(){
        LinkedHashMap<String, String> shiftNumToTime = new LinkedHashMap<>();
        shiftNumToTime.put("1", "10:00");
        shiftNumToTime.put("2", "18:00");
        shiftNumToTime.put("3", "02:00");
        LinkedHashMap<String, String> resultMap = reportsScheduler.moveShiftTime(shiftNumToTime);
        assertThat(resultMap).containsEntry("1", "18:00")
                .containsEntry("2", "02:00")
                .containsEntry("3", "10:00")
                .hasSize(3);

        LinkedHashMap<String, String> shiftNumToTime1 = new LinkedHashMap<>();
        shiftNumToTime1.put("1", "10:00");
        shiftNumToTime1.put("2", "22:00");
        LinkedHashMap<String, String> resultMap1 = reportsScheduler.moveShiftTime(shiftNumToTime1);
        assertThat(resultMap1).containsEntry("1", "22:00")
                .containsEntry("2", "10:00")
                .hasSize(2);

        LinkedHashMap<String, String> shiftNumToTime2 = new LinkedHashMap<>();
        shiftNumToTime2.put("1", "10:00");
        LinkedHashMap<String, String> resultMap2 = reportsScheduler.moveShiftTime(shiftNumToTime2);
        assertThat(resultMap2).containsEntry("1", "10:00")
                .hasSize(1);

        LinkedHashMap<String, String> shiftNumToTime3 = new LinkedHashMap<>();
        shiftNumToTime3.put("1", "10:00");
        shiftNumToTime3.put("2", "18:00");
        shiftNumToTime3.put("3", "02:00");
        shiftNumToTime3.put("4", "05:00");
        LinkedHashMap<String, String> resultMap3 = reportsScheduler.moveShiftTime(shiftNumToTime3);
        assertThat(resultMap3).containsEntry("1", "18:00")
                .containsEntry("2", "02:00")
                .containsEntry("3", "05:00")
                .containsEntry("4", "10:00")
                .hasSize(4);
    }

    @Test
    void generateReportDataForShiftReport(){
        ReportType shiftReportType = new ReportType();
        shiftReportType.setId("shift");
        try (MockedStatic<LocalDateTime> mockedLocalDateTime = Mockito.mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS)) {
            LocalDateTime currentDtShift1 = LocalDateTime.parse("2022-09-20T22:10:50");

            mockedLocalDateTime.when(LocalDateTime::now).thenReturn(currentDtShift1);

            List<ReportData> reportDataShift1 = reportsScheduler.generateReportDataForShiftReport(shiftReportType, "1");
            assertThat(reportDataShift1).hasSize(3);

            Report reportShift1 = reportDataShift1.get(0).getReport();
            assertThat(reportShift1.getName()).isEqualTo("Сменный отчет за 1 смену 20.09.2022");

            assertThat(reportShift1.getStartDt())
                    .isEqualTo(LocalDateTime.parse("2022-09-20T10:00"));

            assertThat(reportShift1.getEndDt())
                    .isEqualTo(LocalDateTime.parse("2022-09-20T22:00"));

            LocalDateTime currentDtShift2 = LocalDateTime.parse("2022-09-21T10:10:50");

            mockedLocalDateTime.when(LocalDateTime::now).thenReturn(currentDtShift2);

            List<ReportData> reportDataShift2 = reportsScheduler.generateReportDataForShiftReport(shiftReportType, "2");
            assertThat(reportDataShift2).hasSize(3);

            Report reportShift2 = reportDataShift2.get(0).getReport();
            assertThat(reportShift2.getName()).isEqualTo("Сменный отчет за 2 смену 20.09.2022");

            assertThat(reportShift2.getStartDt())
                    .isEqualTo(LocalDateTime.parse("2022-09-20T22:00"));

            assertThat(reportShift2.getEndDt())
                    .isEqualTo(LocalDateTime.parse("2022-09-21T10:00"));
        }
    }
}
