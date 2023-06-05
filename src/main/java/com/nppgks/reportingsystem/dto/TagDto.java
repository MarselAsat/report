package com.nppgks.reportingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagDto {
    private Long id;
    private String address;
    private String description;
    private String reportTypeName;
    private String meteringNodeName;
    private String rowNameAndReportType;
}
