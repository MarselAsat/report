package com.nppgks.reports.service;

import com.nppgks.reports.entity.ReportName;
import java.util.List;
import java.util.Optional;

public interface ReportNameService {

    List<ReportName> getReportNameByDateAndReportId(Integer reportTypeId, String dtCreationStart);

    Optional<ReportName> getById(Long reportNameId);

    List<ReportName> findAll();

    List<ReportName> findByReportTypeId(Integer reportTypeId);

    boolean saveReportName(ReportName reportName);

    List<ReportName> findByDate(String date);
}