package com.nppgks.reportingsystem.integration.repository.recurring_reports;

import com.nppgks.reportingsystem.dto.IReportViewTagData;
import com.nppgks.reportingsystem.dto.ReportViewTagData;
import com.nppgks.reportingsystem.db.recurring_reports.entity.TagData;
import com.nppgks.reportingsystem.integration.IntegrationBaseTest;
import com.nppgks.reportingsystem.integration.annotation.RepositoryIT;
import com.nppgks.reportingsystem.db.recurring_reports.repository.TagDataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryIT
@Sql({"classpath:sql/recurring_reports/various_reports.sql"})
class TagDataRepositoryTest extends IntegrationBaseTest {

    private final TagDataRepository tagDataRepository;

    @Autowired
    TagDataRepositoryTest(TagDataRepository tagDataRepository) {
        this.tagDataRepository = tagDataRepository;
    }

    @Test
    void findByReportNameId() {
        List<TagData> tagData = tagDataRepository.findByReportNameId(1L);
        assertThat(tagData).hasSize(3);
    }

    @Test
    @Sql("classpath:sql/recurring_reports/daily.sql")
    void getTagDataView() {
        List<IReportViewTagData> resultQuery = tagDataRepository.getTagDataView(1L);
        List<ReportViewTagData> reportViewTagDataList = resultQuery.stream()
                .map(ReportViewTagData::fromIReportViewTagData)
                .toList();
        assertThat(reportViewTagDataList).hasSize(11);
    }
}