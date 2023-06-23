package com.nppgks.reportingsystem.integration.repository.calculations;

import com.nppgks.reportingsystem.constants.CalcMethod;
import com.nppgks.reportingsystem.dto.calc.CalcTagForOpc;
import com.nppgks.reportingsystem.integration.IntegrationBaseTest;
import com.nppgks.reportingsystem.integration.annotation.RepositoryIT;
import com.nppgks.reportingsystem.db.calculations.repository.TagRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RepositoryIT
@Sql({"classpath:sql/calculation/calc-MI3622.sql"})
class TagRepositoryTest extends IntegrationBaseTest {

    private final TagRepository tagRepository;

    @Autowired
    TagRepositoryTest(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Test
    void updateTag() {
        int affectedRows = tagRepository.updateTag(1, "Q_ij", "расход23");
        assertEquals(1, affectedRows);
    }

    @Test
    void updateTagWithLongDescription() {
        String longDescription = "fvwerfkbegjber;uibgfdcjs bjbf w fhywebfiyvsh fywveyvbdkwjhdbyw fwebiyfdgbweiufgywgedhj fvwerfkbegjber;uibgfdcjs bjbf w fhywebfiyvsh fywveyvbdkwjhdbyw fwebiyfdgbweiufgywgedhj fvwerfkbegjber;uibgfdcjs bjbf w fhywebfiyvsh fywveyvbdkwjhdbyw fwebiyfdgbweiufgywgedhj fvwerfkbegjber;uibgfdcjs bjbf w fhywebfiyvsh fywveyvbdkwjhdbyw fwebiyfdgbweiufgywgedhj ";
        assertThrows(Exception.class,
                () -> tagRepository.updateTag(1, "pov_Q_ij", longDescription));
    }

    @Test
    public void findAllByInitialAndType(){
        List<CalcTagForOpc> tags = tagRepository.findAllByInitialAndCalcMethod(true, CalcMethod.MI_3622.name());
        Assertions.assertThat(tags).hasSize(50);
    }
}