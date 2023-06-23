package com.nppgks.reportingsystem.dto;

import com.nppgks.reportingsystem.db.operative_reports.entity.ReportData;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportDataDto {
    private double data;
    private String tag;
    private String date;
    private String reportType;


    public static ReportDataDto fromTagData(ReportData reportData) {
        ReportDataDto reportDataDto = new ReportDataDto();
        reportDataDto.setData(reportData.getData());
        reportDataDto.setTag(reportData.getTag().getAddress());
        Integer timeZone = reportData.getReport().getReportType().getTimeZone();
        LocalDateTime date = reportData.getCreationDt();
        if (timeZone != null) {
            date = date.plusHours(timeZone);
        }
        reportDataDto.setDate(date.toString());
        reportDataDto.setReportType(reportData.getReport().getReportType().getName());

        return reportDataDto;
    }
}
