package com.nppgks.reports.integration.repository;

import com.nppgks.reports.entity.ReportName;
import com.nppgks.reports.integration.IntegrationBaseTest;
import com.nppgks.reports.integration.annotation.RepositoryIT;
import com.nppgks.reports.repository.ReportNameRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

@RepositoryIT
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
                .findByReportTypeIdAndDtCreationBetween(1, start, end);
        Assertions.assertThat(reportNames).hasSize(2);
    }

    @Test
    void findByReportTypeId() {
        List<ReportName> reportNames = reportNameRepository
                .findByReportTypeId(1);
        Assertions.assertThat(reportNames).hasSize(3);
    }

    @Test
    void findByDtCreationBetween() {
        LocalDateTime start = LocalDateTime.of(2022, 5, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2022, 5, 31, 0, 0);
        List<ReportName> reportNames = reportNameRepository
                .findByDtCreationBetween(start, end);
        Assertions.assertThat(reportNames).hasSize(7);
    }
}