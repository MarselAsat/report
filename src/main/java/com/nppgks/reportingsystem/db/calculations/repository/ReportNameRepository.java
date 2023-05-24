package com.nppgks.reportingsystem.db.calculations.repository;

import com.nppgks.reportingsystem.db.calculations.entity.ReportName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository("reportNameCalcRepository")
public interface ReportNameRepository extends JpaRepository<ReportName, Long> {

    @Query("FROM calc_report_name " +
            "WHERE creationDt BETWEEN :startDt AND :endDt " +
            "ORDER BY creationDt desc")
    List<ReportName> findByDateRange(LocalDateTime startDt, LocalDateTime endDt);

    @Query("FROM calc_report_name " +
            "ORDER BY creationDt desc")
    List<ReportName> findBy();
}
