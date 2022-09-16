package com.nppgks.reports.dto;

import lombok.Value;

@Value
public class TagNameDto {
    Long id;
    String name;
    String description;
    String reportTypeId;

}
