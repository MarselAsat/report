package com.nppgks.reports.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ManualTagNameRepositoryTest {

    private final ManualTagNameRepository manualTagNameRepository;

    @Autowired
    ManualTagNameRepositoryTest(ManualTagNameRepository manualTagNameRepository) {
        this.manualTagNameRepository = manualTagNameRepository;
    }

    @Test
    void updateManualTagName() {
        int affectedRows = manualTagNameRepository.updateManualTagName(1, "pov_Q_ij", "расход");
        assertTrue(affectedRows==1);
    }
}