package com.nppgks.reportingsystem.integration.repository.operative_reports;

import com.nppgks.reportingsystem.db.operative_reports.entity.Report;
import com.nppgks.reportingsystem.constants.ReportTypesEnum;
import com.nppgks.reportingsystem.integration.IntegrationBaseTest;
import com.nppgks.reportingsystem.integration.annotation.RepositoryIT;
import com.nppgks.reportingsystem.db.operative_reports.repository.ReportRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryIT
@Sql({"classpath:sql/operative_reports/various_reports.sql"})
class ReportRepositoryTest extends IntegrationBaseTest {

    private final ReportRepository reportRepository;

    @Autowired
    ReportRepositoryTest(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Test
    void findByReportTypeIdAndDtCreationBetween() {
        LocalDateTime start = LocalDateTime.of(2022, 5, 20, 13, 0);
        LocalDateTime end = LocalDateTime.of(2022, 5, 20, 14, 0);
        List<Report> reports = reportRepository
                .findByReportTypeAndDateRange(ReportTypesEnum.hour.name(), start, end);
        assertThat(reports).hasSize(2);
    }

    @Test
    void findByReportTypeId() {
        List<Report> reports = reportRepository
                .findByReportType(ReportTypesEnum.hour.name());
        assertThat(reports).hasSize(9);
    }

    @Test
    void findByDtCreationBetween() {
        LocalDateTime start = LocalDateTime.of(2022, 5, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2022, 5, 31, 0, 0);
        List<Report> reports = reportRepository
                .findByDateRange(start, end);
        assertThat(reports).hasSize(14);
    }

    @Test
    void findByReportTypeIdAndNameIsLike(){
        List<Report> reports = reportRepository.findByNameLikeAndReportType("%20.08.2022%", ReportTypesEnum.shift.name());
        assertThat(reports).hasSize(2);
    }
}