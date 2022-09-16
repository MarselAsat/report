package com.nppgks.reports.service.db_services;

import com.nppgks.reports.db.entity.ReportName;
import java.util.List;

public interface ReportNameService {

    List<ReportName> getReportNameByDateAndReportId(String reportTypeId, String dtCreationStart);

    ReportName getById(Long reportNameId);

    List<ReportName> findAll();

    List<ReportName> findByReportTypeId(String reportTypeId);

    boolean saveReportName(ReportName reportName);

    List<ReportName> findByDate(String date);
}