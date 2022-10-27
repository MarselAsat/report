package com.nppgks.reports.integration.repository;

import com.nppgks.reports.constants.DataType;
import com.nppgks.reports.db.entity.ReportNamePoverka;
import com.nppgks.reports.db.entity.TagDataPoverka3622;
import com.nppgks.reports.db.repository.ManualTagNameRepository;
import com.nppgks.reports.db.repository.TagDataPoverka3622Repository;
import com.nppgks.reports.integration.IntegrationBaseTest;
import com.nppgks.reports.integration.annotation.RepositoryIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RepositoryIT
class TagDataPoverka3622RepositoryTest extends IntegrationBaseTest {
    private final TagDataPoverka3622Repository tagDataPoverka3622Repository;
    private final ManualTagNameRepository manualTagNameRepository;

    @Autowired
    TagDataPoverka3622RepositoryTest(TagDataPoverka3622Repository tagDataPoverka3622Repository, ManualTagNameRepository manualTagNameRepository) {
        this.tagDataPoverka3622Repository = tagDataPoverka3622Repository;
        this.manualTagNameRepository = manualTagNameRepository;
    }

    @Test
    @Commit
    void batchSave(){
        ReportNamePoverka reportName = new ReportNamePoverka(1L,
                "Поверка 3622 за 2022-07-14",
                LocalDateTime.parse("2022-07-14T12:00:00"),
                "MI_3622");

        String arr2Dim = "[[1, 2, 3], [4, 5, 6], [7, 8, 9]]";
        String arr1Dim = "[1, 2, 3]";
        String value = "1";
        List<TagDataPoverka3622> list = new ArrayList<>();

        manualTagNameRepository.findById(1).ifPresent( tag -> {
                    TagDataPoverka3622 row = new TagDataPoverka3622(null, arr2Dim, DataType.array2D.name(), tag, reportName);
                    list.add(row);
                }
        );
        manualTagNameRepository.findById(2).ifPresent( tag -> {
                    TagDataPoverka3622 row = new TagDataPoverka3622(null, arr2Dim, DataType.array2D.name(), tag, reportName);
                    list.add(row);
                }
        );
        manualTagNameRepository.findById(3).ifPresent( tag -> {
                    TagDataPoverka3622 row = new TagDataPoverka3622(null, arr2Dim, DataType.array2D.name(), tag, reportName);
                    list.add(row);
                }
        );
        manualTagNameRepository.findById(4).ifPresent( tag -> {
                    TagDataPoverka3622 row = new TagDataPoverka3622(null, arr2Dim, DataType.array2D.name(), tag, reportName);
                    list.add(row);
                }
        );

        tagDataPoverka3622Repository.saveAll(list);
    }
}