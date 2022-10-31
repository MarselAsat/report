package com.nppgks.reports.db.poverka.repository;

import com.nppgks.reports.db.poverka.entity.ReportName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("reportNamePoverkaRepository")
public interface ReportNameRepository extends JpaRepository<ReportName, Long> {
}
