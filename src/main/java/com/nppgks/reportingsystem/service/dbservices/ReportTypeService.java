package com.nppgks.reportingsystem.service.dbservices;

import com.nppgks.reportingsystem.dto.ReportTypeDto;
import com.nppgks.reportingsystem.db.recurring_reports.entity.ReportType;

import java.util.List;
import java.util.Map;


public interface ReportTypeService {
    List<ReportTypeDto> getAllReportTypes();
    ReportType getReportTypeById(String id);

    void partialUpdateReportType(String id, Map<String, String> updates);

    List<ReportType> getAllActiveReportTypes();
}
