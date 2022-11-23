package com.nppgks.reportingsystem.integration.repository.recurring_reports;

import com.nppgks.reportingsystem.db.recurring_reports.entity.TagName;
import com.nppgks.reportingsystem.integration.IntegrationBaseTest;
import com.nppgks.reportingsystem.integration.annotation.RepositoryIT;
import com.nppgks.reportingsystem.db.recurring_reports.repository.TagNameRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryIT
@Sql({"classpath:sql/recurring_reports/various_reports.sql"})
class TagNameRepositoryTest extends IntegrationBaseTest {

    private final TagNameRepository tagNameRepository;

    @Autowired
    TagNameRepositoryTest(TagNameRepository tagNameRepository) {
        this.tagNameRepository = tagNameRepository;
    }

    @Test
    void findByName() {
        TagName tagName = tagNameRepository.findByName("hour_mass_il1");
        assertThat(tagName.getName()).isEqualTo("hour_mass_il1");
        assertThat(tagName.getDescription()).isEqualTo("масса за час ил1");
    }
}