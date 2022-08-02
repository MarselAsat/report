package com.nppgks.reports.repository;

import com.nppgks.reports.dto.TagNameDto;
import com.nppgks.reports.entity.ReportType;
import com.nppgks.reports.entity.TagName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagNameRepository extends JpaRepository<TagName, Long> {

    TagName findByName(String name);
    List<TagNameDto> findAllByReportTypeId(Integer id);

    List<TagNameDto> findBy();

    List<TagName> findAllByReportType(ReportType reportType);
}
