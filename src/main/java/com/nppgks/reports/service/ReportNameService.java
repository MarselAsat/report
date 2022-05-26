package com.nppgks.reports.service;

import com.nppgks.reports.entity.ReportName;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportNameService {

    List<ReportName> getReportNameByDateAndReportId(Long reportTypeId, String dtCreationStart);

    List<ReportName> findAll();

    List<ReportName> findByReportTypeId(Long reportTypeId);
}