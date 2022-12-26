package com.nppgks.reportingsystem.db.recurring_reports.repository;

import com.nppgks.reportingsystem.db.recurring_reports.entity.ReportName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReportNameRepository extends JpaRepository<ReportName, Long> {

    @Query("FROM ReportName " +
            "WHERE reportType.id=:reportTypeId " +
            "AND creationDt BETWEEN :dateStart AND :dateEnd " +
            "ORDER BY startDt DESC")
    List<ReportName> findByReportTypeAndDateRange(String reportTypeId, LocalDateTime dateStart, LocalDateTime dateEnd);

    @Query("FROM ReportName " +
            "WHERE reportType.id=:reportTypeId " +
            "ORDER BY startDt DESC")
    List<ReportName> findByReportType(String reportTypeId);

    @Query("FROM ReportName " +
            "WHERE creationDt BETWEEN :dateStart AND :dateEnd " +
            "ORDER BY startDt DESC")
    List<ReportName> findByDateRange(LocalDateTime dateStart, LocalDateTime dateEnd);

    @Query("FROM ReportName " +
            "WHERE reportType.id=:reportTypeId " +
            "AND name LIKE :name " +
            "ORDER BY startDt DESC")
    List<ReportName> findByNameLikeAndReportType(String name, String reportTypeId);

}