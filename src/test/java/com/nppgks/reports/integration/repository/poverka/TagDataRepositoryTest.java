package com.nppgks.reports.integration.repository.poverka;

import com.nppgks.reports.constants.DataType;
import com.nppgks.reports.db.poverka.entity.ReportName;
import com.nppgks.reports.db.poverka.entity.TagData;
import com.nppgks.reports.db.poverka.repository.TagNameRepository;
import com.nppgks.reports.db.poverka.repository.TagDataRepository;
import com.nppgks.reports.integration.IntegrationBaseTest;
import com.nppgks.reports.integration.annotation.RepositoryIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
                    TagData row = new TagData(null, arr2Dim, DataType.array2D.name(), tag, reportName);
                    list.add(row);
                }
        );
        tagNameRepository.findById(2).ifPresent(tag -> {
                    TagData row = new TagData(null, arr2Dim, DataType.array2D.name(), tag, reportName);
                    list.add(row);
                }
        );
        tagNameRepository.findById(3).ifPresent(tag -> {
                    TagData row = new TagData(null, arr2Dim, DataType.array2D.name(), tag, reportName);
                    list.add(row);
                }
        );
        tagNameRepository.findById(4).ifPresent(tag -> {
                    TagData row = new TagData(null, arr2Dim, DataType.array2D.name(), tag, reportName);
                    list.add(row);
                }
        );

        tagDataRepository.saveAll(list);
    }
}