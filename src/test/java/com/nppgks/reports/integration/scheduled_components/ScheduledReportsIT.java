package com.nppgks.reports.integration.scheduled_components;

import com.nppgks.reports.db.entity.TagData;
import com.nppgks.reports.integration.IntegrationBaseTest;
import com.nppgks.reports.integration.annotation.ServiceIT;
import com.nppgks.reports.opc.OpcRequests;
import com.nppgks.reports.scheduled_components.ScheduledReports;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@ExtendWith(MockitoExtension.class)
@ServiceIT
public class ScheduledReportsIT extends IntegrationBaseTest {

    @InjectMocks
    private final ScheduledReports scheduledReports;
    @SpyBean
    private OpcRequests opcRequests;

    @Autowired
    public ScheduledReportsIT(ScheduledReports scheduledReports) {
        this.scheduledReports = scheduledReports;
    }

    @Test
    //@Commit
    void generateTagDataMockedOpc(){
        Mockito.doReturn(Map.of("WinCC_OA.report_redu.save", "234", "WinCC_OA.report_redu.main", "890", "WinCC_OA.CRC.Calc_crc", "237"))
                        .when(opcRequests).getTagDataFromOpc(Mockito.anyList());
        List<TagData> tagDataList = scheduledReports.generateTagDataEveryHour();
        assertThat(tagDataList).hasSize(3);
        assertThat(tagDataList.get(0).getReportName().getStartDt())
                .isEqualTo(LocalDateTime.now()
                        .minusHours(1)
                        .truncatedTo(ChronoUnit.MINUTES)
                        .withMinute(0));

        assertThat(tagDataList.get(0).getReportName().getEndDt())
                .isEqualTo(LocalDateTime.now()
                        .truncatedTo(ChronoUnit.MINUTES)
                        .withMinute(0));
    }

    @Test
    //Commit
    void generateTagData(){
        List<TagData> tagDataList = scheduledReports.generateTagDataEveryHour();
        assertThat(tagDataList).hasSize(3);
    }
}
