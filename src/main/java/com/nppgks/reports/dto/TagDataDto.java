package com.nppgks.reports.dto;

import com.nppgks.reports.entity.TagData;
import com.nppgks.reports.entity.TagName;
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
        LocalDateTime date = tagData.getDtCreation();
        if (timeZone != null) {
            date.plusHours(timeZone);
        }
        tagDataDto.setDate(date.toString());
        tagDataDto.setReportType(tagData.getReportName().getReportType().getName());

        return tagDataDto;
    }
}
