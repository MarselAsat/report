package com.nppgks.reports.dto;

import com.nppgks.reports.entity.ReportType;
import com.nppgks.reports.entity.TagName;
import com.nppgks.reports.service.ReportTypeService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
        ReportType reportType = new ReportType(tagNameDto.getReportTypeId());
        tagName.setReportType(reportType);

        return tagName;
    }
}
