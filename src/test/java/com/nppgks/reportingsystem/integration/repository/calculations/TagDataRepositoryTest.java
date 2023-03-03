package com.nppgks.reportingsystem.integration.repository.calculations;

import com.nppgks.reportingsystem.db.calculations.entity.ReportName;
import com.nppgks.reportingsystem.db.calculations.entity.TagData;
import com.nppgks.reportingsystem.db.calculations.repository.TagNameRepository;
import com.nppgks.reportingsystem.db.calculations.repository.TagDataRepository;
import com.nppgks.reportingsystem.integration.IntegrationBaseTest;
import com.nppgks.reportingsystem.integration.annotation.RepositoryIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RepositoryIT
class TagDataRepositoryTest extends IntegrationBaseTest {
    private final TagDataRepository tagDataRepository;
    private final TagNameRepository tagNameRepository;

    @Autowired
    TagDataRepositoryTest(TagDataRepository tagDataRepository, TagNameRepository tagNameRepository) {
        this.tagDataRepository = tagDataRepository;
        this.tagNameRepository = tagNameRepository;
    }

    @Test
    void batchSave(){
        ReportName reportName = new ReportName(1L,
                "Поверка 3622 за 2022-07-14",
                LocalDateTime.parse("2022-07-14T12:00:00"),
                "MI_3622");

        String arr2Dim = "[[1, 2, 3], [4, 5, 6], [7, 8, 9]]";
        String arr1Dim = "[1, 2, 3]";
        String value = "1";
        List<TagData> list = new ArrayList<>();

        tagNameRepository.findById(1).ifPresent(tag -> {
                    TagData row = new TagData(null, arr2Dim, tag, reportName);
                    list.add(row);
                }
        );
        tagNameRepository.findById(2).ifPresent(tag -> {
                    TagData row = new TagData(null, arr2Dim, tag, reportName);
                    list.add(row);
                }
        );
        tagNameRepository.findById(3).ifPresent(tag -> {
                    TagData row = new TagData(null, arr2Dim, tag, reportName);
                    list.add(row);
                }
        );
        tagNameRepository.findById(4).ifPresent(tag -> {
                    TagData row = new TagData(null, arr2Dim, tag, reportName);
                    list.add(row);
                }
        );

        tagDataRepository.saveAll(list);
    }

    @Test
    @Sql({"classpath:sql/calculation/tagDataRep.sql"})
    void findByReportNameId(){
        List<TagData> tagDataList = tagDataRepository.findByReportNameId(1L);
        System.out.println();
    }
}