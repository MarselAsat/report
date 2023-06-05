package com.nppgks.reportingsystem.integration.repository.operative_reports;

import com.nppgks.reportingsystem.dto.IReportViewReportData;
import com.nppgks.reportingsystem.dto.ReportViewReportData;
import com.nppgks.reportingsystem.db.operative_reports.entity.ReportData;
import com.nppgks.reportingsystem.integration.IntegrationBaseTest;
import com.nppgks.reportingsystem.integration.annotation.RepositoryIT;
import com.nppgks.reportingsystem.db.operative_reports.repository.ReportDataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryIT
@Sql({"classpath:sql/operative_reports/various_reports.sql"})
class ReportDataRepositoryTest extends IntegrationBaseTest {

    private final ReportDataRepository reportDataRepository;

    @Autowired
    ReportDataRepositoryTest(ReportDataRepository reportDataRepository) {
        this.reportDataRepository = reportDataRepository;
    }

    @Test
    void findByReportId() {
        List<ReportData> reportData = reportDataRepository.findByReportId(1L);
        assertThat(reportData).hasSize(2);
    }

    @Test
    @Sql("classpath:sql/operative_reports/daily.sql")
    void getReportDataView() {
        List<IReportViewReportData> resultQuery = reportDataRepository.getReportDataView(1L);
        List<ReportViewReportData> reportViewReportDataList = ReportViewReportData.fromIReportViewReportData(resultQuery);
        assertThat(reportViewReportDataList).hasSize(11);
    }
}