package com.nppgks.reportingsystem.dto;

import com.nppgks.reportingsystem.db.operative_reports.entity.TagData;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TagDataDto {
    private double data;
    private String tagName;
    private String date;
    private String reportType;


    public static TagDataDto fromTagData(TagData tagData){
        TagDataDto tagDataDto = new TagDataDto();
        tagDataDto.setData(tagData.getData());
        tagDataDto.setTagName(tagData.getTagName().getName());
        Integer timeZone = tagData.getReportName().getReportType().getTimeZone();
        LocalDateTime date = tagData.getCreationDt();
        if (timeZone != null) {
            date = date.plusHours(timeZone);
        }
        tagDataDto.setDate(date.toString());
        tagDataDto.setReportType(tagData.getReportName().getReportType().getName());

        return tagDataDto;
    }
}
