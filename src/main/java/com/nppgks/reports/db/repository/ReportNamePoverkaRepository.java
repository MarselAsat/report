package com.nppgks.reports.db.repository;

import com.nppgks.reports.db.entity.ReportNamePoverka;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportNamePoverkaRepository extends JpaRepository<ReportNamePoverka, Long> {
}
