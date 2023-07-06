package com.nppgks.reportingsystem.service.dbservices;

import com.nppgks.reportingsystem.db.scheduled_reports.entity.Report;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {

    List<Report> getReportsByDateAndReportId(String reportTypeId, LocalDate dateCreation);

    Report getById(Long reportId);

    List<Report> findByReportTypeId(String reportTypeId);

    boolean saveReport(Report report);
}