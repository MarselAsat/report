package com.nppgks.reports.integration.repository;

import com.nppgks.reports.db.entity.TagName;
import com.nppgks.reports.integration.IntegrationBaseTest;
import com.nppgks.reports.integration.annotation.RepositoryIT;
import com.nppgks.reports.db.repository.TagNameRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryIT
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