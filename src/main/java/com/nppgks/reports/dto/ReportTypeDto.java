package com.nppgks.reports.dto;

import com.nppgks.reports.entity.ReportType;
import lombok.Data;
import lombok.Setter;

@Data
public class ReportTypeDto {
    private Integer id;
    private String name;

    public static ReportTypeDto fromReportType(ReportType reportType){
        ReportTypeDto reportTypeDto = new ReportTypeDto();
        reportTypeDto.setName(reportType.getName());
        reportTypeDto.setId(reportType.getId());

        return reportTypeDto;
    }

}
