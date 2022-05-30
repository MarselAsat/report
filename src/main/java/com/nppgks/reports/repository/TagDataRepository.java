package com.nppgks.reports.repository;

import com.nppgks.reports.entity.TagData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagDataRepository extends JpaRepository<TagData, Long> {
//    List<TagData> findByReportTypeAndDtCreationBetween(Long reportId, LocalDateTime start, LocalDateTime stop);

    List<TagData> findByReportName_Id(Long reportNameId);

    List<TagData> findByReportName(Long reportNameId);
}
