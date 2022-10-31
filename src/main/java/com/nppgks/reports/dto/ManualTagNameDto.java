package com.nppgks.reports.dto;

import com.nppgks.reports.db.poverka.entity.TagName;
import lombok.Data;

@Data
public class ManualTagNameDto {
    private Integer id;
    private String permanentName;
    private String name;
    private String description;
    private String type;

    public static ManualTagNameDto fromManualTagName(TagName tagName){
        ManualTagNameDto manualTagNameDto = new ManualTagNameDto();
        manualTagNameDto.setId(tagName.getId());
        manualTagNameDto.setPermanentName(tagName.getPermanentName());
        manualTagNameDto.setName(tagName.getName());
        manualTagNameDto.setDescription(tagName.getDescription());
        manualTagNameDto.setType(tagName.getType());
        return manualTagNameDto;
    }

    public static TagName toManualTagName(ManualTagNameDto tagNameDto){
        TagName tagName = new TagName();
        tagName.setId(tagNameDto.getId());
        tagName.setPermanentName(tagNameDto.getPermanentName());
        tagName.setName(tagNameDto.getName());
        tagName.setDescription(tagNameDto.getDescription());
        tagName.setType(tagNameDto.getType());
        return tagName;
    }
}
