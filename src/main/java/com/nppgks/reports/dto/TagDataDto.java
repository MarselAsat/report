package com.nppgks.reports.dto;

import com.nppgks.reports.entity.TagData;
import com.nppgks.reports.entity.TagName;
import lombok.Data;

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
        int timeZone = tagData.getReportName().getReportType().getTimeZone();
        tagDataDto.setDate(tagData.getDtCreation().plusHours(timeZone).toString());
        tagDataDto.setReportType(tagData.getReportName().getReportType().getName());

        return tagDataDto;
    }
}
