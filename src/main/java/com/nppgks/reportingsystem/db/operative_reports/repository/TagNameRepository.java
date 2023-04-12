package com.nppgks.reportingsystem.db.operative_reports.repository;

import com.nppgks.reportingsystem.db.operative_reports.entity.ReportType;
import com.nppgks.reportingsystem.db.operative_reports.entity.TagName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagNameRepository extends JpaRepository<TagName, Long> {

    TagName findByName(String name);

    List<TagName> findByOrderByName();

    List<TagName> findAllByReportType(ReportType reportType);
}
