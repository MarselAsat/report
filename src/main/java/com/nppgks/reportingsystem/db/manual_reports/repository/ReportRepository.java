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
            AND reportType = :reportType
            ORDER BY creationDt desc
            """)
    List<Report> findByDateRangeAndReportType(LocalDateTime startDt, LocalDateTime endDt, String reportType);

    @Query("""
            FROM manual_report
            WHERE reportType = :reportType
            ORDER BY creationDt desc
            """)
    List<Report> findByReportType(String reportType);
}
