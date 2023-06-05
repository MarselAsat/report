package com.nppgks.reportingsystem.service.dbservices;

import com.nppgks.reportingsystem.db.operative_reports.entity.Report;
import com.nppgks.reportingsystem.db.operative_reports.entity.ReportData;
import com.nppgks.reportingsystem.dto.ReportViewReportData;
import com.nppgks.reportingsystem.dto.ReportDataDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ReportDataService {

    List<ReportDataDto> getDataByReport(Long reportId);

    List<ReportData> findAll();

    ReportData saveReportData(ReportData reportData);

    List<ReportData> saveReportValuesForReport(Map<String, String> tagDataMap, Report report, LocalDateTime date);

    List<ReportViewReportData> getReportViewReportData(Long reportId);
}
