package com.nppgks.reportingsystem.db.calculations.repository;

import com.nppgks.reportingsystem.db.calculations.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository("reportCalcRepository")
public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query("""
            FROM calc_report
            WHERE creationDt BETWEEN :startDt AND :endDt
            AND calcMethod = :reportType
            ORDER BY creationDt desc
            """)
    List<Report> findByDateRangeAndReportType(LocalDateTime startDt, LocalDateTime endDt, String reportType);

    @Query("""
            FROM calc_report
            WHERE calcMethod = :reportType
            ORDER BY creationDt desc
            """)
    List<Report> findByReportType(String reportType);
}
