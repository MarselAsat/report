package com.nppgks.reports.integration.repository.poverka;

import com.nppgks.reports.constants.PoverkaType;
import com.nppgks.reports.dto.PoverkaTagNameForOpc;
import com.nppgks.reports.integration.IntegrationBaseTest;
import com.nppgks.reports.integration.annotation.RepositoryIT;
import com.nppgks.reports.db.poverka.repository.TagNameRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RepositoryIT
@Sql({"classpath:sql/poverka/poverka3622.sql"})
class TagNameRepositoryTest extends IntegrationBaseTest {

    private final TagNameRepository tagNameRepository;

    @Autowired
    TagNameRepositoryTest(TagNameRepository tagNameRepository) {
        this.tagNameRepository = tagNameRepository;
    }

    @Test
    void updateTagName() {
        int affectedRows = tagNameRepository.updateTagName(1, "pov_Q_ij", "расход23");
        assertEquals(1, affectedRows);
    }

    @Test
    void updateTagNameWithLongDescription() {
        String longDescription = "fvwerfkbegjber;uibgfdcjs bjbf w fhywebfiyvsh fywveyvbdkwjhdbyw fwebiyfdgbweiufgywgedhj fvwerfkbegjber;uibgfdcjs bjbf w fhywebfiyvsh fywveyvbdkwjhdbyw fwebiyfdgbweiufgywgedhj fvwerfkbegjber;uibgfdcjs bjbf w fhywebfiyvsh fywveyvbdkwjhdbyw fwebiyfdgbweiufgywgedhj fvwerfkbegjber;uibgfdcjs bjbf w fhywebfiyvsh fywveyvbdkwjhdbyw fwebiyfdgbweiufgywgedhj ";
        assertThrows(Exception.class,
                () -> tagNameRepository.updateTagName(1, "pov_Q_ij", longDescription));
    }

    @Test
    public void findAllByInitialAndType(){
        List<PoverkaTagNameForOpc> tagNames = tagNameRepository.findAllByInitialAndType(true, PoverkaType.MI_3622.name());
        Assertions.assertThat(tagNames).hasSize(45);
    }
}