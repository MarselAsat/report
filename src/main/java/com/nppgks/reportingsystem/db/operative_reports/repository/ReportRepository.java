package com.nppgks.reportingsystem.db.operative_reports.repository;

import com.nppgks.reportingsystem.db.operative_reports.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query("""
            FROM Report
            WHERE reportType.id=:reportTypeId
            AND creationDt BETWEEN :dateStart AND :dateEnd
            ORDER BY startDt DESC
            """)
    List<Report> findByReportTypeAndDateRange(String reportTypeId, LocalDateTime dateStart, LocalDateTime dateEnd);

    @Query("""
            FROM Report
            WHERE reportType.id=:reportTypeId
            ORDER BY startDt DESC
            """)
    List<Report> findByReportType(String reportTypeId);

    @Query("""
            FROM Report
            WHERE creationDt BETWEEN :dateStart AND :dateEnd
            ORDER BY startDt DESC
            """)
    List<Report> findByDateRange(LocalDateTime dateStart, LocalDateTime dateEnd);

    @Query("""
            FROM Report
            WHERE reportType.id=:reportTypeId
            AND name LIKE :name
            ORDER BY startDt DESC
            """)
    List<Report> findByNameLikeAndReportType(String name, String reportTypeId);

}