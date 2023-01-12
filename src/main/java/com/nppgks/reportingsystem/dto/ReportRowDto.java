package com.nppgks.reportingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportRowDto {

    private String reportRowName;
    private String reportTypeName;

    public String combineNameAndType(){
        return "<"+reportTypeName.substring(0, 3)+"> "+reportRowName;
    }
}
