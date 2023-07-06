package com.nppgks.reportingsystem.integration.service.dbservices.scheduled_reports;

import com.nppgks.reportingsystem.db.scheduled_reports.entity.Report;
import com.nppgks.reportingsystem.constants.ReportTypesEnum;
import com.nppgks.reportingsystem.integration.IntegrationBaseTest;
import com.nppgks.reportingsystem.integration.annotation.ServiceIT;
import com.nppgks.reportingsystem.service.dbservices.ReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceIT
@Sql({"classpath:sql/scheduled_reports/various_reports.sql"})
class ReportServiceTest extends IntegrationBaseTest {

    private final ReportService reportService;

    @Autowired
    ReportServiceTest(ReportService reportService) {
        this.reportService = reportService;
    }

    @Test
    void getReportByDateAndReportId() {
        List<Report> hourReports1 = reportService.getReportsByDateAndReportId(ReportTypesEnum.hour.name(), LocalDate.parse("2022-05-20"));
        assertThat(hourReports1).hasSize(4);
        List<Report> hourReports2 = reportService.getReportsByDateAndReportId(ReportTypesEnum.hour.name(), LocalDate.parse("2022-06-01"));
        assertThat(hourReports2).hasSize(2);
        List<Report> hourReports3 = reportService.getReportsByDateAndReportId(ReportTypesEnum.hour.name(), LocalDate.parse("2023-02-28"));
        assertThat(hourReports3).hasSize(1);

        List<Report> twohourReports1 = reportService.getReportsByDateAndReportId(ReportTypesEnum.twohour.name(), LocalDate.parse("2022-05-20"));
        assertThat(twohourReports1).hasSize(3);

        List<Report> dailyReports = reportService.getReportsByDateAndReportId(ReportTypesEnum.daily.name(), LocalDate.parse("2022-05-20"));
        assertThat(dailyReports).hasSize(1);
        List<Report> shiftReports = reportService.getReportsByDateAndReportId(ReportTypesEnum.shift.name(), LocalDate.parse("2021-08-20"));
        assertThat(shiftReports).hasSize(1);
        List<Report> monthReports = reportService.getReportsByDateAndReportId(ReportTypesEnum.month.name(), LocalDate.parse("2021-12-10"));
        assertThat(monthReports).hasSize(1);
        List<Report> yearReport = reportService.getReportsByDateAndReportId(ReportTypesEnum.year.name(), LocalDate.parse("2022-01-01"));
        Pattern pattern = Pattern.compile(".*2022.*");
        assertThat(yearReport.get(0).getName()).matches(pattern);
    }
}