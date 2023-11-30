package com.nppgks.reportingsystem.integration.repository.scheduled_reports;

import com.nppgks.reportingsystem.db.scheduled_reports.entity.Tag;
import com.nppgks.reportingsystem.integration.IntegrationBaseTest;
import com.nppgks.reportingsystem.integration.annotation.RepositoryIT;
import com.nppgks.reportingsystem.db.scheduled_reports.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryIT
@Sql({"classpath:sql/scheduled_reports/various_reports.sql"})
class TagRepositoryTest extends IntegrationBaseTest {

    private final TagRepository tagRepository;

    @Autowired
    TagRepositoryTest(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Test
    void findByName() {
        Tag tag = tagRepository.findByAddress("hour_mass_il1");
        assertThat(tag.getAddress()).isEqualTo("hour_mass_il1");
        assertThat(tag.getDescription()).isEqualTo("масса за час ил1");
    }
}