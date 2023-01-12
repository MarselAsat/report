package com.nppgks.reportingsystem.dto;

import com.nppgks.reportingsystem.db.recurring_reports.entity.ReportRow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportRowMapper {

    public ReportRowDto fromReportRowToReportRowDto(ReportRow reportRow){
        return new ReportRowDto(
                reportRow.getName(),
                reportRow.getReportType().getName()
        );
    }
}
