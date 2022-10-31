package com.nppgks.reports.db.recurring_reports.repository;

import com.nppgks.reports.db.recurring_reports.entity.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportTypeRepository extends JpaRepository<ReportType, String> {

}
