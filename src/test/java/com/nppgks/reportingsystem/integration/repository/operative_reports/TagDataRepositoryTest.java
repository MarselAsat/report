package com.nppgks.reportingsystem.integration.repository.operative_reports;

import com.nppgks.reportingsystem.dto.IReportViewTagData;
import com.nppgks.reportingsystem.dto.ReportViewTagData;
import com.nppgks.reportingsystem.db.operative_reports.entity.TagData;
import com.nppgks.reportingsystem.integration.IntegrationBaseTest;
import com.nppgks.reportingsystem.integration.annotation.RepositoryIT;
import com.nppgks.reportingsystem.db.operative_reports.repository.TagDataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryIT
@Sql({"classpath:sql/operative_reports/various_reports.sql"})
class TagDataRepositoryTest extends IntegrationBaseTest {

    private final TagDataRepository tagDataRepository;

    @Autowired
    TagDataRepositoryTest(TagDataRepository tagDataRepository) {
        this.tagDataRepository = tagDataRepository;
    }

    @Test
    void findByReportNameId() {
        List<TagData> tagData = tagDataRepository.findByReportNameId(1L);
        assertThat(tagData).hasSize(2);
    }

    @Test
    @Sql("classpath:sql/operative_reports/daily.sql")
    void getTagDataView() {
        List<IReportViewTagData> resultQuery = tagDataRepository.getTagDataViewTest(1L);
        List<ReportViewTagData> reportViewTagDataList = ReportViewTagData.fromIReportViewTagData(resultQuery);
        assertThat(reportViewTagDataList).hasSize(11);
    }
}