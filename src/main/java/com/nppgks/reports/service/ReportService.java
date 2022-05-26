package com.nppgks.reports.service;

import com.nppgks.reports.dto.ReportTypeDto;

import java.util.List;

public interface ReportService {
    List<ReportTypeDto> getAllReportTypes();
}
