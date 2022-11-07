package com.nppgks.reports.integration.repository.calculations;

import com.nppgks.reports.constants.CalcMethod;
import com.nppgks.reports.dto.calc.CalcTagNameForOpc;
import com.nppgks.reports.integration.IntegrationBaseTest;
import com.nppgks.reports.integration.annotation.RepositoryIT;
import com.nppgks.reports.db.calculations.repository.TagNameRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RepositoryIT
@Sql({"classpath:sql/calculation/calc-method-3622.sql"})
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
        List<CalcTagNameForOpc> tagNames = tagNameRepository.findAllByInitialAndType(true, CalcMethod.MI_3622.name());
        Assertions.assertThat(tagNames).hasSize(45);
    }
}