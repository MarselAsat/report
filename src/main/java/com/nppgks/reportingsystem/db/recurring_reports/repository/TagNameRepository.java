package com.nppgks.reportingsystem.db.recurring_reports.repository;

import com.nppgks.reportingsystem.db.recurring_reports.entity.ReportType;
import com.nppgks.reportingsystem.db.recurring_reports.entity.TagName;
import com.nppgks.reportingsystem.dto.TagNameDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagNameRepository extends JpaRepository<TagName, Long> {

    TagName findByName(String name);

    List<TagNameDto> findByOrderByName();

    List<TagName> findAllByReportType(ReportType reportType);
}
