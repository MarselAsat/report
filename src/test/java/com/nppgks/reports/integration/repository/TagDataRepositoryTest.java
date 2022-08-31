package com.nppgks.reports.integration.repository;

import com.nppgks.reports.entity.TagData;
import com.nppgks.reports.integration.RepositoryBaseTest;
import com.nppgks.reports.repository.TagDataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TagDataRepositoryTest extends RepositoryBaseTest {

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
}