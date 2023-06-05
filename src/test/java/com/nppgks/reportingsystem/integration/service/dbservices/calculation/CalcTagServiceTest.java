package com.nppgks.reportingsystem.integration.service.dbservices.calculation;

import com.nppgks.reportingsystem.dto.calc.CalcTagDto;
import com.nppgks.reportingsystem.integration.IntegrationBaseTest;
import com.nppgks.reportingsystem.integration.annotation.ServiceIT;
import com.nppgks.reportingsystem.service.dbservices.calculation.CalcTagService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ServiceIT
@Sql({"classpath:sql/calculation/calc-MI3622.sql"})
class CalcTagServiceTest extends IntegrationBaseTest {

    private final CalcTagService calcTagService;

    @Autowired
    CalcTagServiceTest(CalcTagService calcTagService) {
        this.calcTagService = calcTagService;
    }

    @Test
    void getAllTags() {
        List<CalcTagDto> allTags = calcTagService.getAllTags();
        assertThat(allTags).hasSizeGreaterThan(30);
    }

    @Test
    void updateTags(){
        CalcTagDto tag1 = new CalcTagDto();
        tag1.setId(1);
        tag1.setAddress("tag_Q_ij");
        tag1.setDescription("расход");

        CalcTagDto tag2 = new CalcTagDto();
        tag2.setId(2);
        tag2.setAddress("test_Q_ij");
        tag2.setDescription("test");

        List<CalcTagDto> tags = List.of(tag1, tag2);
        Map<Integer, Boolean> responses = calcTagService.updateTags(tags);
        assertTrue(responses.get(1));
        assertTrue(responses.get(2));
    }

    @Test
    void deleteTag(){
        boolean deleted = calcTagService.deleteTag(1);
        assertTrue(deleted);
    }

    @Test
    void saveTag(){
        CalcTagDto tagDto = new CalcTagDto();
        tagDto.setPermanentName("tagName");
        tagDto.setAddress("tagAddress");
        tagDto.setCalcMethod("3622");
        Integer newId = calcTagService.saveTag(tagDto);
        assertThat(newId).isGreaterThan(30);
    }
}