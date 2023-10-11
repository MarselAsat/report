package com.nppgks.reportingsystem.integration.reportgeneration.calculations.mi3622;

import com.nppgks.reportingsystem.constants.ManualReportTypesEnum;
import com.nppgks.reportingsystem.db.manual_reports.entity.Report;
import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
import com.nppgks.reportingsystem.dto.manual.ManualTagDto;
import com.nppgks.reportingsystem.integration.IntegrationBaseTest;
import com.nppgks.reportingsystem.integration.annotation.ServiceIT;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.SaveOnceADayStrategy;
import com.nppgks.reportingsystem.service.dbservices.manual_reports.ManualTagService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

@ServiceIT
@Sql({"classpath:sql/manual_reports/MI3622.sql"})
class MI3622DbServiceTest extends IntegrationBaseTest {

    private final SaveOnceADayStrategy saveOnceADayStrategy;
    private final ManualTagService tagService;

    @Autowired
    MI3622DbServiceTest(SaveOnceADayStrategy saveOnceADayStrategy, ManualTagService tagService) {
        this.saveOnceADayStrategy = saveOnceADayStrategy;
        this.tagService = tagService;
    }

    @Test
    void saveCalculations() {
        Report report = new Report(
                null,
                "Тестовый отчет",
                LocalDateTime.now(),
                ManualReportTypesEnum.mi3622.name());

        List<ManualTagDto> tags = tagService.getAllTags();

        List<ReportData> reportDataList = List.of(
                new ReportData(null, "10.0", ManualTagDto.toTag(tags.get(0)), report),
                new ReportData(null, "20.0", ManualTagDto.toTag(tags.get(1)), report),
                new ReportData(null, "[10.0, 20, 70]", ManualTagDto.toTag(tags.get(2)), report),
                new ReportData(null, "4", ManualTagDto.toTag(tags.get(3)), report),
                new ReportData(null, "78", ManualTagDto.toTag(tags.get(4)), report)
        );

        saveOnceADayStrategy.saveReportDataInDb(reportDataList, report);
    }
}