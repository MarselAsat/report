package com.nppgks.reports.integration.scheduled_components;

import com.nppgks.reports.db.entity.ReportName;
import com.nppgks.reports.db.entity.ReportType;
import com.nppgks.reports.db.entity.TagData;
import com.nppgks.reports.integration.IntegrationBaseTest;
import com.nppgks.reports.integration.annotation.ScheduledIT;
import com.nppgks.reports.opc.OpcRequests;
import com.nppgks.reports.scheduled_components.ScheduledReports;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@Transactional
@ExtendWith(MockitoExtension.class)
@ScheduledIT
public class ScheduledReportsIT extends IntegrationBaseTest {

    @InjectMocks
    private final ScheduledReports scheduledReports;
    @SpyBean
    private OpcRequests opcRequests;

    @Autowired
    public ScheduledReportsIT(ScheduledReports scheduledReports) {
        this.scheduledReports = scheduledReports;
    }


    @BeforeEach
    void init(){
        doReturn(Map.of("WinCC_OA.report_redu.save", "234", "WinCC_OA.report_redu.main", "890", "WinCC_OA.CRC.Calc_crc", "237"))
                .when(opcRequests).getTagDataFromOpc(Mockito.anyList());
    }
    @Test
    //@Commit
    void generateTagDataForHourReport(){
        LocalDateTime currentDt = LocalDateTime.parse("2022-09-20T10:10:50");

        try (MockedStatic<LocalDateTime> mockedLocalDateTime = Mockito.mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS)) {
            mockedLocalDateTime.when(LocalDateTime::now).thenReturn(currentDt);

            List<TagData> tagDataList = scheduledReports.generateTagDataEveryHour();
            String reportName = tagDataList.get(0).getReportName().getName();
            assertThat(reportName).isEqualTo("Часовой отчет за 09:00 2022-09-20");
            assertThat(tagDataList).hasSize(3);
            assertThat(tagDataList.get(0).getReportName().getStartDt())
                    .isEqualTo(LocalDateTime.parse("2022-09-20T09:00"));

            assertThat(tagDataList.get(0).getReportName().getEndDt())
                    .isEqualTo(LocalDateTime.parse("2022-09-20T10:00"));
        }
    }

    @Test
    void generateTagDataForDailyReport(){
        LocalDateTime currentDt = LocalDateTime.parse("2022-09-20T10:10:50");

        try (MockedStatic<LocalDateTime> mockedLocalDateTime = Mockito.mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS)) {
            mockedLocalDateTime.when(LocalDateTime::now).thenReturn(currentDt);

            List<TagData> tagDataList = scheduledReports.generateTagDataForDailyReport();
            String reportName = tagDataList.get(0).getReportName().getName();
            assertThat(reportName).isEqualTo("Суточный отчет за 2022-09-19");
            assertThat(tagDataList).hasSize(3);
            assertThat(tagDataList.get(0).getReportName().getStartDt())
                    .isEqualTo(LocalDateTime.parse("2022-09-19T10:00"));

            assertThat(tagDataList.get(0).getReportName().getEndDt())
                    .isEqualTo(LocalDateTime.parse("2022-09-20T10:00"));
        }
    }

    @Test
    void generateTagDataForShiftReport(){
        ReportType shiftReportType = new ReportType();
        shiftReportType.setId("shift");
        try (MockedStatic<LocalDateTime> mockedLocalDateTime = Mockito.mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS)) {
            LocalDateTime currentDtShift1 = LocalDateTime.parse("2022-09-20T22:10:50");

            mockedLocalDateTime.when(LocalDateTime::now).thenReturn(currentDtShift1);

            List<TagData> tagDataShift1 = scheduledReports.generateTagDataForShiftReport(shiftReportType, "1");
            assertThat(tagDataShift1).hasSize(3);

            ReportName reportNameShift1 = tagDataShift1.get(0).getReportName();
            assertThat(reportNameShift1.getName()).isEqualTo("Сменный отчет за 1 смену 2022-09-20");

            assertThat(reportNameShift1.getStartDt())
                    .isEqualTo(LocalDateTime.parse("2022-09-20T10:00"));

            assertThat(reportNameShift1.getEndDt())
                    .isEqualTo(LocalDateTime.parse("2022-09-20T22:00"));

            LocalDateTime currentDtShift2 = LocalDateTime.parse("2022-09-21T10:10:50");

            mockedLocalDateTime.when(LocalDateTime::now).thenReturn(currentDtShift2);

            List<TagData> tagDataShift2 = scheduledReports.generateTagDataForShiftReport(shiftReportType, "2");
            assertThat(tagDataShift2).hasSize(3);

            ReportName reportNameShift2 = tagDataShift2.get(0).getReportName();
            assertThat(reportNameShift2.getName()).isEqualTo("Сменный отчет за 2 смену 2022-09-20");

            assertThat(reportNameShift2.getStartDt())
                    .isEqualTo(LocalDateTime.parse("2022-09-20T22:00"));

            assertThat(reportNameShift2.getEndDt())
                    .isEqualTo(LocalDateTime.parse("2022-09-21T10:00"));
        }
    }
}
