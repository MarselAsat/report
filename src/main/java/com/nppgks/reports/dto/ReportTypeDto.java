package com.nppgks.reports.dto;

import com.nppgks.reports.db.entity.ReportType;
import lombok.Data;

@Data
public class ReportTypeDto {
    private String id;
    private String name;

    public static ReportTypeDto fromReportType(ReportType reportType){
        ReportTypeDto reportTypeDto = new ReportTypeDto();
        reportTypeDto.setName(reportType.getName());
        reportTypeDto.setId(reportType.getId());

        return reportTypeDto;
    }

}
