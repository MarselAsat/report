package com.nppgks.reports.db.repository;

import com.nppgks.reports.db.entity.ReportType;
import com.nppgks.reports.db.entity.TagName;
import com.nppgks.reports.dto.TagNameDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagNameRepository extends JpaRepository<TagName, Long> {

    TagName findByName(String name);
    List<TagNameDto> findAllByReportTypeId(String id);

    List<TagNameDto> findBy();

    List<TagName> findAllByReportType(ReportType reportType);
}
