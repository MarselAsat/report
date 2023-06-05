package com.nppgks.reportingsystem.integration.repository.calculations;

import com.nppgks.reportingsystem.db.calculations.entity.Report;
import com.nppgks.reportingsystem.db.calculations.entity.ReportData;
import com.nppgks.reportingsystem.db.calculations.repository.TagRepository;
import com.nppgks.reportingsystem.db.calculations.repository.ReportDataRepository;
import com.nppgks.reportingsystem.integration.IntegrationBaseTest;
import com.nppgks.reportingsystem.integration.annotation.RepositoryIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RepositoryIT
class ReportDataRepositoryTest extends IntegrationBaseTest {
    private final ReportDataRepository reportDataRepository;
    private final TagRepository tagRepository;

    @Autowired
    ReportDataRepositoryTest(ReportDataRepository reportDataRepository, TagRepository tagRepository) {
        this.reportDataRepository = reportDataRepository;
        this.tagRepository = tagRepository;
    }

    @Test
    void batchSave(){
        Report report = new Report(1L,
                "Поверка 3622 за 2022-07-14",
                LocalDateTime.parse("2022-07-14T12:00:00"),
                "MI_3622");

        String arr2Dim = "[[1, 2, 3], [4, 5, 6], [7, 8, 9]]";
        String arr1Dim = "[1, 2, 3]";
        String value = "1";
        List<ReportData> list = new ArrayList<>();

        tagRepository.findById(1).ifPresent(tag -> {
                    ReportData row = new ReportData(null, arr2Dim, tag, report);
                    list.add(row);
                }
        );
        tagRepository.findById(2).ifPresent(tag -> {
                    ReportData row = new ReportData(null, arr2Dim, tag, report);
                    list.add(row);
                }
        );
        tagRepository.findById(3).ifPresent(tag -> {
                    ReportData row = new ReportData(null, arr2Dim, tag, report);
                    list.add(row);
                }
        );
        tagRepository.findById(4).ifPresent(tag -> {
                    ReportData row = new ReportData(null, arr2Dim, tag, report);
                    list.add(row);
                }
        );

        reportDataRepository.saveAll(list);
    }

    @Test
    @Sql({"classpath:sql/calculation/reportData.sql"})
    void findByReportId() {
        List<ReportData> reportDataList = reportDataRepository.findByReportId(1L);
        System.out.println();
    }
}