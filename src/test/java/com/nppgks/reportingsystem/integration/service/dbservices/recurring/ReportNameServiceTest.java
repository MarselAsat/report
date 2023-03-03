package com.nppgks.reportingsystem.integration.service.dbservices.recurring;

import com.nppgks.reportingsystem.db.recurring_reports.entity.ReportName;
import com.nppgks.reportingsystem.constants.ReportTypesEnum;
import com.nppgks.reportingsystem.integration.IntegrationBaseTest;
import com.nppgks.reportingsystem.integration.annotation.ServiceIT;
import com.nppgks.reportingsystem.service.dbservices.ReportNameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceIT
@Sql({"classpath:sql/recurring_reports/various_reports.sql"})
class ReportNameServiceTest extends IntegrationBaseTest {

    private final ReportNameService reportNameService;

    @Autowired
    ReportNameServiceTest(ReportNameService reportNameService) {
        this.reportNameService = reportNameService;
    }

    @Test
    void getReportNameByDateAndReportId() {
        List<ReportName> hourReportNames1 = reportNameService.getReportNameByDateAndReportId(ReportTypesEnum.hour.name(), LocalDate.parse("2022-05-20"));
        assertThat(hourReportNames1).hasSize(4);
        List<ReportName> hourReportNames2 = reportNameService.getReportNameByDateAndReportId(ReportTypesEnum.hour.name(), LocalDate.parse("2022-06-01"));
        assertThat(hourReportNames2).hasSize(2);
        List<ReportName> hourReportNames3 = reportNameService.getReportNameByDateAndReportId(ReportTypesEnum.hour.name(), LocalDate.parse("2023-02-28"));
        assertThat(hourReportNames3).hasSize(1);

        List<ReportName> twohourReportNames1 = reportNameService.getReportNameByDateAndReportId(ReportTypesEnum.twohour.name(), LocalDate.parse("2022-05-20"));
        assertThat(twohourReportNames1).hasSize(3);

        List<ReportName> dailyReportNames = reportNameService.getReportNameByDateAndReportId(ReportTypesEnum.daily.name(), LocalDate.parse("2022-05-20"));
        assertThat(dailyReportNames).hasSize(1);
        List<ReportName> shiftReportNames = reportNameService.getReportNameByDateAndReportId(ReportTypesEnum.shift.name(), LocalDate.parse("2021-08-20"));
        assertThat(shiftReportNames).hasSize(1);
        List<ReportName> monthReportNames = reportNameService.getReportNameByDateAndReportId(ReportTypesEnum.month.name(), LocalDate.parse("2021-12-10"));
        assertThat(monthReportNames).hasSize(1);
        List<ReportName> yearReportName = reportNameService.getReportNameByDateAndReportId(ReportTypesEnum.year.name(), LocalDate.parse("2022-01-01"));
        Pattern pattern = Pattern.compile(".*2022.*");
        assertThat(yearReportName.get(0).getName()).matches(pattern);
    }
}