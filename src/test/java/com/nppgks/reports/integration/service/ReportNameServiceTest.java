package com.nppgks.reports.integration.service;

import com.nppgks.reports.entity.ReportName;
import com.nppgks.reports.integration.IntegrationBaseTest;
import com.nppgks.reports.integration.annotation.ServiceIT;
import com.nppgks.reports.service.ReportNameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceIT
class ReportNameServiceTest extends IntegrationBaseTest {

    private final ReportNameService reportNameService;

    @Autowired
    ReportNameServiceTest(ReportNameService reportNameService) {
        this.reportNameService = reportNameService;
    }

    @Test
    void getReportNameByDateAndReportId() {
        List<ReportName> hourReportNames = reportNameService.getReportNameByDateAndReportId(1, "2022-05-20");
        assertThat(hourReportNames).hasSize(3);
        List<ReportName> dailyReportNames = reportNameService.getReportNameByDateAndReportId(2, "2022-05-20");
        assertThat(dailyReportNames).hasSize(1);
        List<ReportName> shiftReportNames = reportNameService.getReportNameByDateAndReportId(3, "2021-08-20");
        assertThat(shiftReportNames).hasSize(1);
        List<ReportName> monthReportNames = reportNameService.getReportNameByDateAndReportId(4, "2021-12-10");
        assertThat(monthReportNames).hasSize(1);
        List<ReportName> yearReportName = reportNameService.getReportNameByDateAndReportId(5, "2022-01-01");
        Pattern pattern = Pattern.compile(".*2022.*");
        assertThat(yearReportName.get(0).getName()).matches(pattern);
    }

    @Test
    void findByDate() {
    }
}