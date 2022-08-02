package com.nppgks.reports.dto;

import com.nppgks.reports.entity.ReportType;
import com.nppgks.reports.entity.TagData;
import com.nppgks.reports.entity.TagName;
import com.nppgks.reports.service.ReportTypeService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Value
public class TagNameDto {
    private Long id;
    private String name;
    private String description;
    private Integer reportTypeId;
}
