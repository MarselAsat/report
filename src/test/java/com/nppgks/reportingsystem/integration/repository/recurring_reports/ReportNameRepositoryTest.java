package com.nppgks.reportingsystem.integration.repository.recurring_reports;

import com.nppgks.reportingsystem.db.recurring_reports.entity.ReportName;
import com.nppgks.reportingsystem.constants.ReportTypesEnum;
import com.nppgks.reportingsystem.integration.IntegrationBaseTest;
import com.nppgks.reportingsystem.integration.annotation.RepositoryIT;
import com.nppgks.reportingsystem.db.recurring_reports.repository.ReportNameRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryIT
@Sql({"classpath:sql/recurring_reports/various_reports.sql"})
class ReportNameRepositoryTest extends IntegrationBaseTest {

    private final ReportNameRepository reportNameRepository;

    @Autowired
    ReportNameRepositoryTest(ReportNameRepository reportNameRepository) {
        this.reportNameRepository = reportNameRepository;
    }

    @Test
    void findByReportTypeIdAndDtCreationBetween() {
        LocalDateTime start = LocalDateTime.of(2022, 5, 20, 13, 0);
        LocalDateTime end = LocalDateTime.of(2022, 5, 20, 14, 0);
        List<ReportName> reportNames = reportNameRepository
                .findByReportTypeAndDateRange(ReportTypesEnum.hour.name(), start, end);
        assertThat(reportNames).hasSize(2);
    }

    @Test
    void findByReportTypeId() {
        List<ReportName> reportNames = reportNameRepository
                .findByReportType(ReportTypesEnum.hour.name());
        assertThat(reportNames).hasSize(9);
    }

    @Test
    void findByDtCreationBetween() {
        LocalDateTime start = LocalDateTime.of(2022, 5, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2022, 5, 31, 0, 0);
        List<ReportName> reportNames = reportNameRepository
                .findByDateRange(start, end);
        assertThat(reportNames).hasSize(6);
    }

    @Test
    void findByReportTypeIdAndNameIsLike(){
        List<ReportName> reportNames = reportNameRepository.findByNameLikeAndReportType("%20.08.2022%", ReportTypesEnum.shift.name());
        assertThat(reportNames).hasSize(2);
    }
}