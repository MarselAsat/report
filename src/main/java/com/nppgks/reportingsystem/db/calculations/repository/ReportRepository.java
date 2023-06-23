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
            ORDER BY creationDt desc
            """)
    List<Report> findByDateRange(LocalDateTime startDt, LocalDateTime endDt);

    @Query("""
            FROM calc_report
            ORDER BY creationDt desc
            """)
    List<Report> findBy();
}
