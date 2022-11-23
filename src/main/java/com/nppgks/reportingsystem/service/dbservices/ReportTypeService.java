package com.nppgks.reportingsystem.service.dbservices;

import com.nppgks.reportingsystem.dto.ReportTypeDto;
import com.nppgks.reportingsystem.db.recurring_reports.entity.ReportType;

import java.util.List;


public interface ReportTypeService {
    List<ReportTypeDto> getAllReportTypes();
    ReportType getReportTypeById(String id);
}
