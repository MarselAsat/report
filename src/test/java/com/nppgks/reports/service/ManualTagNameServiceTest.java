package com.nppgks.reports.service;

import com.nppgks.reports.dto.ManualTagNameDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ManualTagNameServiceTest {

    private final ManualTagNameService manualTagNameService;

    @Autowired
    ManualTagNameServiceTest(ManualTagNameService manualTagNameService) {
        this.manualTagNameService = manualTagNameService;
    }


    @Test
    void getAllTagNames() {
        List<ManualTagNameDto> allTagNames = manualTagNameService.getAllTagNames();
        assertThat(allTagNames).hasSize(18);
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
        assertThat(responses.get(1)).isTrue();
        assertThat(responses.get(2)).isTrue();
    }
}