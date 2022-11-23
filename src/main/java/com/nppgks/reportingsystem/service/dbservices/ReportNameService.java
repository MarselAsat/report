package com.nppgks.reportingsystem.service.dbservices;

import com.nppgks.reportingsystem.db.recurring_reports.entity.ReportName;

import java.time.LocalDate;
import java.util.List;

public interface ReportNameService {

    List<ReportName> getReportNameByDateAndReportId(String reportTypeId, LocalDate dateCreation);

    ReportName getById(Long reportNameId);

    List<ReportName> findAll();

    List<ReportName> findByReportTypeId(String reportTypeId);

    boolean saveReportName(ReportName reportName);

    List<ReportName> findByDate(String date);
}