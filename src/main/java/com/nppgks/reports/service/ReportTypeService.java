package com.nppgks.reports.service;

import com.nppgks.reports.dto.ReportTypeDto;
import com.nppgks.reports.entity.ReportType;

import java.util.List;
import java.util.Optional;


public interface ReportTypeService {
    List<ReportTypeDto> getAllReportTypes();
    Optional<ReportType> getReportTypeById(String id);
}
