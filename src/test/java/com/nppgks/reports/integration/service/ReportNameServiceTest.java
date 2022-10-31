package com.nppgks.reports.integration.service;

import com.nppgks.reports.db.recurring_reports.entity.ReportName;
import com.nppgks.reports.constants.ReportTypesEnum;
import com.nppgks.reports.integration.IntegrationBaseTest;
import com.nppgks.reports.integration.annotation.ServiceIT;
import com.nppgks.reports.service.db_services.ReportNameService;
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
        List<ReportName> hourReportNames = reportNameService.getReportNameByDateAndReportId(ReportTypesEnum.hour.name(), "2022-05-20");
        assertThat(hourReportNames).hasSize(3);
        List<ReportName> dailyReportNames = reportNameService.getReportNameByDateAndReportId(ReportTypesEnum.daily.name(), "2022-05-20");
        assertThat(dailyReportNames).hasSize(1);
        List<ReportName> shiftReportNames = reportNameService.getReportNameByDateAndReportId(ReportTypesEnum.shift.name(), "2021-08-20");
        assertThat(shiftReportNames).hasSize(1);
        List<ReportName> monthReportNames = reportNameService.getReportNameByDateAndReportId(ReportTypesEnum.month.name(), "2021-12-10");
        assertThat(monthReportNames).hasSize(1);
        List<ReportName> yearReportName = reportNameService.getReportNameByDateAndReportId(ReportTypesEnum.year.name(), "2022-01-01");
        Pattern pattern = Pattern.compile(".*2022.*");
        assertThat(yearReportName.get(0).getName()).matches(pattern);
    }

    @Test
    void findByDate() {
        List<ReportName> reportNames20May = reportNameService.findByDate("2022-05-20");
        assertThat(reportNames20May).hasSize(6);
        List<ReportName> reportNames21May = reportNameService.findByDate("2022-05-21");
        assertThat(reportNames21May).hasSize(3);
        List<ReportName> reportNames20Aug = reportNameService.findByDate("2022-08-20");
        assertThat(reportNames20Aug).hasSize(3);
    }
}