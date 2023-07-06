package com.nppgks.reportingsystem.integration.service.dbservices.manual_reports;

import com.nppgks.reportingsystem.dto.manual.ManualTagDto;
import com.nppgks.reportingsystem.integration.IntegrationBaseTest;
import com.nppgks.reportingsystem.integration.annotation.ServiceIT;
import com.nppgks.reportingsystem.service.dbservices.manual_reports.ManualTagService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ServiceIT
@Sql({"classpath:sql/manual_reports/MI3622.sql"})
class ManualTagServiceTest extends IntegrationBaseTest {

    private final ManualTagService manualTagService;

    @Autowired
    ManualTagServiceTest(ManualTagService manualTagService) {
        this.manualTagService = manualTagService;
    }

    @Test
    void getAllTags() {
        List<ManualTagDto> allTags = manualTagService.getAllTags();
        assertThat(allTags).hasSizeGreaterThan(30);
    }

    @Test
    void updateTags(){
        ManualTagDto tag1 = new ManualTagDto();
        tag1.setId(1);
        tag1.setAddress("tag_Q_ij");
        tag1.setDescription("расход");

        ManualTagDto tag2 = new ManualTagDto();
        tag2.setId(2);
        tag2.setAddress("test_Q_ij");
        tag2.setDescription("test");

        List<ManualTagDto> tags = List.of(tag1, tag2);
        Map<Integer, Boolean> responses = manualTagService.updateTags(tags);
        assertTrue(responses.get(1));
        assertTrue(responses.get(2));
    }

    @Test
    void deleteTag(){
        boolean deleted = manualTagService.deleteTag(1);
        assertTrue(deleted);
    }

    @Test
    void saveTag(){
        ManualTagDto tagDto = new ManualTagDto();
        tagDto.setPermanentName("tagName");
        tagDto.setAddress("tagAddress");
        tagDto.setReportType("3622");
        Integer newId = manualTagService.saveTag(tagDto);
        assertThat(newId).isGreaterThan(30);
    }
}