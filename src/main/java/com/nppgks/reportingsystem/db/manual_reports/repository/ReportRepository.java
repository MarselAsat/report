package com.nppgks.reportingsystem.db.manual_reports.repository;

import com.nppgks.reportingsystem.db.manual_reports.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository("reportManualRepository")
public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query("""
            FROM manual_report
            WHERE creationDt BETWEEN :startDt AND :endDt
            AND reportType.id = :reportTypeId
            ORDER BY creationDt desc
            """)
    List<Report> findByDateRangeAndReportTypeId(LocalDateTime startDt, LocalDateTime endDt, String reportTypeId);

    @Query("""
            FROM manual_report
            WHERE reportType.id = :reportTypeId
            ORDER BY creationDt desc
            """)
    List<Report> findByReportTypeId(String reportTypeId);
}
