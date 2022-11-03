package com.nppgks.reports.db.calculations.repository;

import com.nppgks.reports.db.calculations.entity.ReportName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("reportNameCalcRepository")
public interface ReportNameRepository extends JpaRepository<ReportName, Long> {
}
