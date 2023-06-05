package com.nppgks.reportingsystem.db.operative_reports.repository;

import com.nppgks.reportingsystem.db.operative_reports.entity.ReportType;
import com.nppgks.reportingsystem.db.operative_reports.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findByAddress(String address);

    List<Tag> findByOrderByAddress();

    List<Tag> findAllByReportType(ReportType reportType);
}
