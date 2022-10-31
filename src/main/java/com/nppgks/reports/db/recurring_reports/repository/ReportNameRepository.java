package com.nppgks.reports.db.recurring_reports.repository;

import com.nppgks.reports.db.recurring_reports.entity.ReportName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReportNameRepository extends JpaRepository<ReportName, Long> {

    List<ReportName> findByReportTypeIdAndCreationDtBetween(String reportTypeId, LocalDateTime dtCreationStart, LocalDateTime dtCreationEnd);

    List<ReportName> findByReportTypeId(String reportTypeId);

    List<ReportName> findByCreationDtBetween(LocalDateTime dateStart, LocalDateTime dateEnd);

    List<ReportName> findByNameLikeAndReportTypeId(String name, String reportTypeId);

}