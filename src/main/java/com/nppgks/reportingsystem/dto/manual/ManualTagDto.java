package com.nppgks.reportingsystem.dto.manual;

import com.nppgks.reportingsystem.db.manual_reports.entity.ReportType;
import com.nppgks.reportingsystem.db.manual_reports.entity.Tag;
import lombok.Data;

@Data
public class ManualTagDto {
    private Integer id;
    private String permanentName;
    private String address;
    private String description;
    private Boolean initial;
    private String reportType;

    public static ManualTagDto fromTag(Tag tag) {
        ManualTagDto manualTagDto = new ManualTagDto();
        manualTagDto.setId(tag.getId());
        manualTagDto.setPermanentName(tag.getPermanentName());
        manualTagDto.setAddress(tag.getAddress());
        manualTagDto.setDescription(tag.getDescription());
        manualTagDto.setReportType(tag.getReportType().getId());
        manualTagDto.setInitial(tag.getInitial());
        return manualTagDto;
    }

    public static Tag toTag(ManualTagDto tagDto) {
        Tag tag = new Tag();
        tag.setId(tagDto.getId());
        tag.setPermanentName(tagDto.getPermanentName());
        tag.setAddress(tagDto.getAddress());
        tag.setDescription(tagDto.getDescription());
        tag.setReportType(new ReportType(tagDto.reportType, null, null, null));
        tag.setInitial(tagDto.getInitial());
        return tag;
    }
}
