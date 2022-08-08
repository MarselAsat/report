package com.nppgks.reports.integration.repository;

import com.nppgks.reports.dto.TagNameForOpc;
import com.nppgks.reports.repository.ManualTagNameRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class ManualTagNameRepositoryIT {
    private final ManualTagNameRepository manualTagNameRepository;

    @Autowired
    public ManualTagNameRepositoryIT(ManualTagNameRepository manualTagNameRepository) {
        this.manualTagNameRepository = manualTagNameRepository;
    }

    @Test
    public void findAllByInitialAndType(){
        List<TagNameForOpc> tagNames = manualTagNameRepository.findAllByInitialAndType(true, "3622");
        Assertions.assertThat(tagNames).hasSize(10);
    }
}
