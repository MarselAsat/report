package com.nppgks.reportingsystem.integration.scheduled_components;

import com.nppgks.reportingsystem.db.recurring_reports.entity.ReportName;
import com.nppgks.reportingsystem.db.recurring_reports.entity.ReportType;
import com.nppgks.reportingsystem.db.recurring_reports.entity.TagData;
import com.nppgks.reportingsystem.integration.IntegrationBaseTest;
import com.nppgks.reportingsystem.integration.annotation.ScheduledIT;
import com.nppgks.reportingsystem.opc.OpcRequests;
import com.nppgks.reportingsystem.reportgeneration.recurring.ReportsScheduler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@Transactional
@ExtendWith(MockitoExtension.class)
@ScheduledIT
@Sql({"classpath:sql/recurring_reports/various_reports.sql"})
public class ReportsSchedulerIT extends IntegrationBaseTest {

    @InjectMocks
    private final ReportsScheduler reportsScheduler;
    @SpyBean
    private OpcRequests opcRequests;

    @Autowired
    public ReportsSchedulerIT(ReportsScheduler reportsScheduler) {
        this.reportsScheduler = reportsScheduler;
    }

    @BeforeEach
    void init(){
        doReturn(Map.of("WinCC_OA.report_redu.save", "234", "WinCC_OA.report_redu.main", "890", "WinCC_OA.CRC.Calc_crc", "237"))
                .when(opcRequests).getTagDataFromOpc(Mockito.anyList());
    }
    @Test
    void generateTagDataForHourReport(){
        LocalDateTime currentDt = LocalDateTime.parse("2022-09-20T10:10:50");

        try (MockedStatic<LocalDateTime> mockedLocalDateTime = Mockito.mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS)) {
            mockedLocalDateTime.when(LocalDateTime::now).thenReturn(currentDt);

            List<TagData> tagDataList = reportsScheduler.generateTagDataForHourReport();
            String reportName = tagDataList.get(0).getReportName().getName();
            assertThat(reportName).isEqualTo("Часовой отчет за 09:00 20.09.2022");
            assertThat(tagDataList).hasSize(3);
            assertThat(tagDataList.get(0).getReportName().getStartDt())
                    .isEqualTo(LocalDateTime.parse("2022-09-20T09:00"));

            assertThat(tagDataList.get(0).getReportName().getEndDt())
                    .isEqualTo(LocalDateTime.parse("2022-09-20T10:00"));
        }
    }

    @Test
    void generateTagDataForHourReport2(){
        LocalDateTime currentDt = LocalDateTime.parse("2023-01-10T14:00:50");

        try (MockedStatic<LocalDateTime> mockedLocalDateTime = Mockito.mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS)) {
            mockedLocalDateTime.when(LocalDateTime::now).thenReturn(currentDt);

            List<TagData> tagDataList = reportsScheduler.generateTagDataForHourReport();
            String reportName = tagDataList.get(0).getReportName().getName();
            assertThat(reportName).isEqualTo("Часовой отчет за 13:00 10.01.2023");
            assertThat(tagDataList).hasSize(3);
            assertThat(tagDataList.get(0).getReportName().getStartDt())
                    .isEqualTo(LocalDateTime.parse("2023-01-10T13:00"));

            assertThat(tagDataList.get(0).getReportName().getEndDt())
                    .isEqualTo(LocalDateTime.parse("2023-01-10T14:00"));
        }
    }

    @Test
    void generateTagDataForDailyReport(){
        LocalDateTime currentDt = LocalDateTime.parse("2022-09-20T10:10:50");

        try (MockedStatic<LocalDateTime> mockedLocalDateTime = Mockito.mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS)) {
            mockedLocalDateTime.when(LocalDateTime::now).thenReturn(currentDt);

            List<TagData> tagDataList = reportsScheduler.generateTagDataForDailyReport();
            ReportName reportName = tagDataList.get(0).getReportName();
            assertThat(reportName.getName()).isEqualTo("Суточный отчет за 19.09.2022");
            assertThat(tagDataList).hasSize(3);
            assertThat(reportName.getStartDt())
                    .isEqualTo(LocalDateTime.parse("2022-09-19T10:00"));

            assertThat(reportName.getEndDt())
                    .isEqualTo(LocalDateTime.parse("2022-09-20T10:00"));
        }
    }

    @Test
    void generateTagDataForMonthReport(){
        LocalDateTime currentDt = LocalDateTime.parse("2022-09-01T12:10:50");

        try (MockedStatic<LocalDateTime> mockedLocalDateTime = Mockito.mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS)) {
            mockedLocalDateTime.when(LocalDateTime::now).thenReturn(currentDt);

            List<TagData> tagDataList = reportsScheduler.generateTagDataForMonthReport();
            ReportName reportName = tagDataList.get(0).getReportName();
            assertThat(reportName.getName()).isEqualTo("Месячный отчет за 08.2022");
            assertThat(tagDataList).hasSize(3);
            assertThat(reportName.getStartDt())
                    .isEqualTo(LocalDateTime.parse("2022-08-01T12:00"));

            assertThat(reportName.getEndDt())
                    .isEqualTo(LocalDateTime.parse("2022-09-01T12:00"));
        }
    }

    @Test
    void generateTagDataForYearReport(){
        LocalDateTime currentDt = LocalDateTime.parse("2022-01-01T11:10:50");

        try (MockedStatic<LocalDateTime> mockedLocalDateTime = Mockito.mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS)) {
            mockedLocalDateTime.when(LocalDateTime::now).thenReturn(currentDt);

            List<TagData> tagDataList = reportsScheduler.generateTagDataForYearReport();
            ReportName reportName = tagDataList.get(0).getReportName();
            assertThat(reportName.getName()).isEqualTo("Годовой отчет за 2021 год");
            assertThat(tagDataList).hasSize(3);
            assertThat(reportName.getStartDt())
                    .isEqualTo(LocalDateTime.parse("2021-01-01T11:00"));

            assertThat(reportName.getEndDt())
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
    void generateTagDataForShiftReport(){
        ReportType shiftReportType = new ReportType();
        shiftReportType.setId("shift");
        try (MockedStatic<LocalDateTime> mockedLocalDateTime = Mockito.mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS)) {
            LocalDateTime currentDtShift1 = LocalDateTime.parse("2022-09-20T22:10:50");

            mockedLocalDateTime.when(LocalDateTime::now).thenReturn(currentDtShift1);

            List<TagData> tagDataShift1 = reportsScheduler.generateTagDataForShiftReport(shiftReportType, "1");
            assertThat(tagDataShift1).hasSize(3);

            ReportName reportNameShift1 = tagDataShift1.get(0).getReportName();
            assertThat(reportNameShift1.getName()).isEqualTo("Сменный отчет за 1 смену 20.09.2022");

            assertThat(reportNameShift1.getStartDt())
                    .isEqualTo(LocalDateTime.parse("2022-09-20T10:00"));

            assertThat(reportNameShift1.getEndDt())
                    .isEqualTo(LocalDateTime.parse("2022-09-20T22:00"));

            LocalDateTime currentDtShift2 = LocalDateTime.parse("2022-09-21T10:10:50");

            mockedLocalDateTime.when(LocalDateTime::now).thenReturn(currentDtShift2);

            List<TagData> tagDataShift2 = reportsScheduler.generateTagDataForShiftReport(shiftReportType, "2");
            assertThat(tagDataShift2).hasSize(3);

            ReportName reportNameShift2 = tagDataShift2.get(0).getReportName();
            assertThat(reportNameShift2.getName()).isEqualTo("Сменный отчет за 2 смену 20.09.2022");

            assertThat(reportNameShift2.getStartDt())
                    .isEqualTo(LocalDateTime.parse("2022-09-20T22:00"));

            assertThat(reportNameShift2.getEndDt())
                    .isEqualTo(LocalDateTime.parse("2022-09-21T10:00"));
        }
    }
}
