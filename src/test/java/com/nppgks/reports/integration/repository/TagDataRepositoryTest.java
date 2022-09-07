package com.nppgks.reports.integration.repository;

import com.nppgks.reports.entity.IReportViewTagData;
import com.nppgks.reports.entity.ReportViewTagData;
import com.nppgks.reports.entity.TagData;
import com.nppgks.reports.integration.IntegrationBaseTest;
import com.nppgks.reports.integration.annotation.RepositoryIT;
import com.nppgks.reports.repository.TagDataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryIT
class TagDataRepositoryTest extends IntegrationBaseTest {

    private final TagDataRepository tagDataRepository;

    @Autowired
    TagDataRepositoryTest(TagDataRepository tagDataRepository) {
        this.tagDataRepository = tagDataRepository;
    }

    @Test
    void findByReportName_Id() {
        List<TagData> tagData = tagDataRepository.findByReportName_Id(1L);
        assertThat(tagData).hasSize(3);
    }

    @Sql("classpath:sql/tag-data.sql")
    @Test
    void getTagDataView(){
        List<IReportViewTagData> resultQuery = tagDataRepository.getTagDataView(1L);
        List<ReportViewTagData> reportViewTagDataList = resultQuery.stream()
                .map(ReportViewTagData::fromIReportViewTagData)
                .toList();
        assertThat(reportViewTagDataList).hasSize(11);
    }
}