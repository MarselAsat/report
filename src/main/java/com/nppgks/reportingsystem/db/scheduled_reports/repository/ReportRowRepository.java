package com.nppgks.reportingsystem.db.scheduled_reports.repository;

import com.nppgks.reportingsystem.db.scheduled_reports.entity.ReportType;
import com.nppgks.reportingsystem.db.scheduled_reports.entity.ReportRow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRowRepository extends JpaRepository<ReportRow, Integer> {

    Optional<ReportRow> findByNameAndReportType(String name, ReportType reportType);
}
