package com.nppgks.reports.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TagNameDto {
    private Long id;
    private String name;
    private String description;
    private String reportTypeId;

}
