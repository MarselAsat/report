package com.nppgks.reportingsystem.dto;

import lombok.Value;

@Value
public class TagNameDto {
    Long id;
    String name;
    String description;
    String reportTypeId;

}
