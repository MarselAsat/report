package com.nppgks.reports.integration.service.calc_method_3622;

import com.nppgks.reports.dto.calc.CalcTagNameDto;
import com.nppgks.reports.integration.IntegrationBaseTest;
import com.nppgks.reports.integration.annotation.ServiceIT;
import com.nppgks.reports.service.db_services.calculation.CalcTagNameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ServiceIT
@Sql({"classpath:sql/calculation/calc-method-3622.sql"})
class CalcTagNameServiceTest extends IntegrationBaseTest {

    private final CalcTagNameService calcTagNameService;

    @Autowired
    CalcTagNameServiceTest(CalcTagNameService calcTagNameService) {
        this.calcTagNameService = calcTagNameService;
    }

    @Test
    void getAllTagNames() {
        List<CalcTagNameDto> allTagNames = calcTagNameService.getAllTagNames();
        assertThat(allTagNames).hasSizeGreaterThan(30);
    }

    @Test
    void updateTagNames(){
        CalcTagNameDto tn1 = new CalcTagNameDto();
        tn1.setId(1);
        tn1.setName("tag_Q_ij");
        tn1.setDescription("расход");

        CalcTagNameDto tn2 = new CalcTagNameDto();
        tn2.setId(2);
        tn2.setName("test_Q_ij");
        tn2.setDescription("test");

        List<CalcTagNameDto> tagNames = List.of(tn1, tn2);
        Map<Integer, Boolean> responses = calcTagNameService.updateTagNames(tagNames);
        assertTrue(responses.get(1));
        assertTrue(responses.get(2));
    }

    @Test
    void deleteTagName(){
        boolean deleted = calcTagNameService.deleteTagName(1);
        assertTrue(deleted);
    }

    @Test
    void saveTagName(){
        CalcTagNameDto tagNameDto = new CalcTagNameDto();
        tagNameDto.setPermanentName("newTagName");
        tagNameDto.setName("tag name");
        tagNameDto.setType("3622");
        Integer newId = calcTagNameService.saveTagName(tagNameDto);
        assertThat(newId).isGreaterThan(30);
    }
}