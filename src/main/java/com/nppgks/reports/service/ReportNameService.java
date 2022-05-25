package com.nppgks.reports.service;

import com.nppgks.reports.entity.ReportName;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportNameService {

    public List<ReportName> getReportNameByDateAndReportId(Long reportTypeId, LocalDateTime dtCreation);
}
