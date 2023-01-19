package com.nppgks.reportingsystem.dto;

import com.nppgks.reportingsystem.db.recurring_reports.entity.ReportRow;
import com.nppgks.reportingsystem.db.recurring_reports.entity.ReportType;
import com.nppgks.reportingsystem.db.recurring_reports.repository.ReportTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportRowMapper {

    private final ReportTypeRepository reportTypeRepository;

    public ReportRowDto fromReportRowToReportRowDto(ReportRow reportRow){
        return new ReportRowDto(
                reportRow.getId(),
                reportRow.getName(),
                reportRow.getOrder(),
                reportRow.getReportType().getName()
        );
    }

    public ReportRow fromReportRowDtoToReportRow(ReportRowDto reportRowDto){
        ReportType reportType = reportTypeRepository.findByName(reportRowDto.getReportTypeName()).orElseThrow();
        return new ReportRow(
                reportRowDto.getId(),
                reportRowDto.getName(),
                reportRowDto.getOrder(),
                reportType
        );
    }
}
