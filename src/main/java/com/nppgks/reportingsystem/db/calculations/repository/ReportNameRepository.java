package com.nppgks.reportingsystem.db.calculations.repository;

import com.nppgks.reportingsystem.db.calculations.entity.ReportName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository("reportNameCalcRepository")
public interface ReportNameRepository extends JpaRepository<ReportName, Long> {

    List<ReportName> findByCreationDtBetween(LocalDateTime startDt, LocalDateTime endDt);
}
