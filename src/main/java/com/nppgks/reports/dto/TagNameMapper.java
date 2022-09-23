package com.nppgks.reports.dto;

import com.nppgks.reports.db.entity.ReportType;
import com.nppgks.reports.db.entity.TagName;
import com.nppgks.reports.service.db_services.ReportTypeService;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class TagNameMapper {

    private ReportTypeService reportTypeService;

    public TagNameMapper(ReportTypeService reportTypeService){
        this.reportTypeService = reportTypeService;
    }

    public TagName toTagName(TagNameDto tagNameDto){
        TagName tagName = new TagName();
        tagName.setId(tagNameDto.getId());
        tagName.setName(tagNameDto.getName());
        tagName.setDescription(tagNameDto.getDescription());
        ReportType reportType = reportTypeService.getReportTypeById(tagNameDto.getReportTypeId());
        tagName.setReportType(reportType);

        return tagName;
    }
    public TagNameDto toTagNameDto(TagName tagName){
        String reportTypeId = tagName.getReportType()!=null?tagName.getReportType().getId(): null;
        return new TagNameDto(
                tagName.getId(),
                tagName.getName(),
                tagName.getDescription(),
                reportTypeId);
    }
}
