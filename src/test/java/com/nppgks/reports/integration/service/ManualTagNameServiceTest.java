package com.nppgks.reports.integration.service;

import com.nppgks.reports.dto.ManualTagNameDto;
import com.nppgks.reports.integration.IntegrationBaseTest;
import com.nppgks.reports.integration.annotation.ServiceIT;
import com.nppgks.reports.service.db_services.ManualTagNameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ServiceIT
class ManualTagNameServiceTest extends IntegrationBaseTest {

    private final ManualTagNameService manualTagNameService;

    @Autowired
    ManualTagNameServiceTest(ManualTagNameService manualTagNameService) {
        this.manualTagNameService = manualTagNameService;
    }

    @Test
    void getAllTagNames() {
        List<ManualTagNameDto> allTagNames = manualTagNameService.getAllTagNames();
        assertThat(allTagNames).hasSizeGreaterThan(30);
    }

    @Test
    void updateTagNames(){
        ManualTagNameDto tn1 = new ManualTagNameDto();
        tn1.setId(1);
        tn1.setName("tag_Q_ij");
        tn1.setDescription("расход");

        ManualTagNameDto tn2 = new ManualTagNameDto();
        tn2.setId(2);
        tn2.setName("test_Q_ij");
        tn2.setDescription("test");

        List<ManualTagNameDto> tagNames = List.of(tn1, tn2);
        Map<Integer, Boolean> responses = manualTagNameService.updateTagNames(tagNames);
        assertTrue(responses.get(1));
        assertTrue(responses.get(2));
    }

    @Test
    void deleteTagName(){
        boolean deleted = manualTagNameService.deleteTagName(1);
        assertTrue(deleted);
    }

    @Test
    void saveTagName(){
        ManualTagNameDto tagNameDto = new ManualTagNameDto();
        tagNameDto.setPermanentName("newTagName");
        tagNameDto.setName("tag name");
        tagNameDto.setType("3622");
        Integer newId = manualTagNameService.saveTagName(tagNameDto);
        assertThat(newId).isGreaterThan(30);
    }
}