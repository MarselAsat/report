package com.nppgks.reports.integration.repository;

import com.nppgks.reports.dto.TagNameForOpc;
import com.nppgks.reports.integration.IntegrationBaseTest;
import com.nppgks.reports.integration.annotation.RepositoryIT;
import com.nppgks.reports.db.repository.ManualTagNameRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RepositoryIT
class ManualTagNameRepositoryTest extends IntegrationBaseTest {

    private final ManualTagNameRepository manualTagNameRepository;

    @Autowired
    ManualTagNameRepositoryTest(ManualTagNameRepository manualTagNameRepository) {
        this.manualTagNameRepository = manualTagNameRepository;
    }

    @Test
    void updateManualTagName() {
        int affectedRows = manualTagNameRepository.updateManualTagName(1, "pov_Q_ij", "расход23");
        assertEquals(1, affectedRows);
    }

    @Test
    void updateManualTagNameWithLongDescription() {
        String longDescription = "fvwerfkbegjber;uibgfdcjs bjbf w fhywebfiyvsh fywveyvbdkwjhdbyw fwebiyfdgbweiufgywgedhj fvwerfkbegjber;uibgfdcjs bjbf w fhywebfiyvsh fywveyvbdkwjhdbyw fwebiyfdgbweiufgywgedhj fvwerfkbegjber;uibgfdcjs bjbf w fhywebfiyvsh fywveyvbdkwjhdbyw fwebiyfdgbweiufgywgedhj fvwerfkbegjber;uibgfdcjs bjbf w fhywebfiyvsh fywveyvbdkwjhdbyw fwebiyfdgbweiufgywgedhj ";
        assertThrows(Exception.class,
                () -> manualTagNameRepository.updateManualTagName(1, "pov_Q_ij", longDescription));
    }

    @Test
    public void findAllByInitialAndType(){
        List<TagNameForOpc> tagNames = manualTagNameRepository.findAllByInitialAndType(true, "3622");
        Assertions.assertThat(tagNames).hasSize(32);
    }
}