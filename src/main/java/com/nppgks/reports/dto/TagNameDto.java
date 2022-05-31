package com.nppgks.reports.dto;

import com.nppgks.reports.entity.ReportType;
import com.nppgks.reports.entity.TagData;
import com.nppgks.reports.entity.TagName;
import com.nppgks.reports.service.ReportTypeService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Data
@NoArgsConstructor
public class TagNameDto {
    private String name;
    private String description;
    private String reportType;

}
