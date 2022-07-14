package com.nppgks.reports.dto;

import com.nppgks.reports.entity.ManualTagName;
import lombok.Data;

@Data
public class ManualTagNameDto {
    private String permanentName;
    private String name;
    private String description;

    public static ManualTagNameDto fromManualTagName(ManualTagName tagName){
        ManualTagNameDto manualTagNameDto = new ManualTagNameDto();
        manualTagNameDto.setPermanentName(tagName.getPermanentName());
        manualTagNameDto.setName(tagName.getName());
        manualTagNameDto.setDescription(tagName.getDescription());
        return manualTagNameDto;
    }

    public static ManualTagName toManualTagName(ManualTagNameDto tagNameDto){
        ManualTagName manualTagName = new ManualTagName();
        manualTagName.setPermanentName(tagNameDto.getPermanentName());
        manualTagName.setName(tagNameDto.getName());
        manualTagName.setDescription(tagNameDto.getDescription());
        return manualTagName;
    }
}
