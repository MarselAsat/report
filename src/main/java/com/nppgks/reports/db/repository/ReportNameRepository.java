package com.nppgks.reports.db.repository;

import com.nppgks.reports.db.entity.ReportName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportNameRepository extends JpaRepository<ReportName, Long> {

    List<ReportName> findByReportTypeIdAndCreationDtBetween(String reportTypeId, LocalDateTime dtCreationStart, LocalDateTime dtCreationEnd);

    List<ReportName> findByReportTypeId(String reportTypeId);

    List<ReportName> findByCreationDtBetween(LocalDateTime dateStart, LocalDateTime dateEnd);

    List<ReportName> findByNameLikeAndReportTypeId(String name, String reportTypeId);

}