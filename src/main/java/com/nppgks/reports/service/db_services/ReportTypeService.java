package com.nppgks.reports.service.db_services;

import com.nppgks.reports.dto.ReportTypeDto;
import com.nppgks.reports.db.entity.ReportType;

import java.util.List;


public interface ReportTypeService {
    List<ReportTypeDto> getAllReportTypes();
    ReportType getReportTypeById(String id);
}
