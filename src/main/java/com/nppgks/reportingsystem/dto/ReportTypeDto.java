package com.nppgks.reportingsystem.dto;

import com.nppgks.reportingsystem.db.scheduled_reports.entity.ReportType;
import lombok.Data;

@Data
public class ReportTypeDto {
    private String id;
    private String name;
    private String description;
    private Boolean active;

    public static ReportTypeDto fromReportType(ReportType reportType) {
        ReportTypeDto reportTypeDto = new ReportTypeDto();
        reportTypeDto.setName(reportType.getName());
        reportTypeDto.setId(reportType.getId());
        reportTypeDto.setDescription(reportType.getDescription());
        reportTypeDto.setActive(reportType.getActive());

        return reportTypeDto;
    }
    public static ReportType toReportType(ReportTypeDto reportTypeDto) {
        ReportType reportType = new ReportType();
        reportType.setName(reportTypeDto.getName());
        reportType.setId(reportTypeDto.getId());
        reportType.setDescription(reportTypeDto.getDescription());
        reportType.setActive(reportTypeDto.getActive());

        return reportType;
    }

}
