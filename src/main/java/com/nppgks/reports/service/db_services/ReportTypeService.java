package com.nppgks.reports.service.db_services;

import com.nppgks.reports.dto.ReportTypeDto;
import com.nppgks.reports.db.entity.ReportType;

import java.util.List;
import java.util.Optional;


public interface ReportTypeService {
    List<ReportTypeDto> getAllReportTypes();
    Optional<ReportType> getReportTypeById(String id);
}
