package com.nppgks.reportingsystem.dto.manual;

import com.nppgks.reportingsystem.db.manual_reports.entity.ReportType;
import lombok.Data;

@Data
public class ManualReportTypeDto {
    private String id;
    private String name;
    private String description;
    private Boolean active;

    public static ManualReportTypeDto fromReportType(ReportType reportType) {
        ManualReportTypeDto reportTypeDto = new ManualReportTypeDto();
        reportTypeDto.setName(reportType.getName());
        reportTypeDto.setId(reportType.getId());
        reportTypeDto.setDescription(reportType.getDescription());
        reportTypeDto.setActive(reportType.getActive());

        return reportTypeDto;
    }
    public static ReportType toReportType(ManualReportTypeDto reportTypeDto) {
        ReportType reportType = new ReportType();
        reportType.setName(reportTypeDto.getName());
        reportType.setId(reportTypeDto.getId());
        reportType.setDescription(reportTypeDto.getDescription());
        reportType.setActive(reportTypeDto.getActive());

        return reportType;
    }
}
