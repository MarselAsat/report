package com.nppgks.reports.integration.service.poverka3622;

import com.nppgks.reports.dto.poverka.PoverkaTagNameDto;
import com.nppgks.reports.integration.IntegrationBaseTest;
import com.nppgks.reports.integration.annotation.ServiceIT;
import com.nppgks.reports.service.db_services.poverka.PoverkaTagNameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ServiceIT
@Sql({"classpath:sql/poverka/poverka3622.sql"})
class PoverkaTagNameServiceTest extends IntegrationBaseTest {

    private final PoverkaTagNameService poverkaTagNameService;

    @Autowired
    PoverkaTagNameServiceTest(PoverkaTagNameService poverkaTagNameService) {
        this.poverkaTagNameService = poverkaTagNameService;
    }

    @Test
    void getAllTagNames() {
        List<PoverkaTagNameDto> allTagNames = poverkaTagNameService.getAllTagNames();
        assertThat(allTagNames).hasSizeGreaterThan(30);
    }

    @Test
    void updateTagNames(){
        PoverkaTagNameDto tn1 = new PoverkaTagNameDto();
        tn1.setId(1);
        tn1.setName("tag_Q_ij");
        tn1.setDescription("расход");

        PoverkaTagNameDto tn2 = new PoverkaTagNameDto();
        tn2.setId(2);
        tn2.setName("test_Q_ij");
        tn2.setDescription("test");

        List<PoverkaTagNameDto> tagNames = List.of(tn1, tn2);
        Map<Integer, Boolean> responses = poverkaTagNameService.updateTagNames(tagNames);
        assertTrue(responses.get(1));
        assertTrue(responses.get(2));
    }

    @Test
    void deleteTagName(){
        boolean deleted = poverkaTagNameService.deleteTagName(1);
        assertTrue(deleted);
    }

    @Test
    void saveTagName(){
        PoverkaTagNameDto tagNameDto = new PoverkaTagNameDto();
        tagNameDto.setPermanentName("newTagName");
        tagNameDto.setName("tag name");
        tagNameDto.setType("3622");
        Integer newId = poverkaTagNameService.saveTagName(tagNameDto);
        assertThat(newId).isGreaterThan(30);
    }
}