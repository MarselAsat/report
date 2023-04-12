package com.nppgks.reportingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagNameDto {
    private Long id;
    private String name;
    private String description;
    private String reportTypeName;
    private String meteringNodeName;
    private String rowNameAndReportType;
}
