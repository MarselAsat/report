package com.nppgks.reports.dto.poverka;

import com.nppgks.reports.db.poverka.entity.TagName;
import lombok.Data;

@Data
public class PoverkaTagNameDto {
    private Integer id;
    private String permanentName;
    private String name;
    private String description;
    private String type;

    public static PoverkaTagNameDto fromTagName(TagName tagName){
        PoverkaTagNameDto poverkaTagNameDto = new PoverkaTagNameDto();
        poverkaTagNameDto.setId(tagName.getId());
        poverkaTagNameDto.setPermanentName(tagName.getPermanentName());
        poverkaTagNameDto.setName(tagName.getName());
        poverkaTagNameDto.setDescription(tagName.getDescription());
        poverkaTagNameDto.setType(tagName.getType());
        return poverkaTagNameDto;
    }

    public static TagName toTagName(PoverkaTagNameDto tagNameDto){
        TagName tagName = new TagName();
        tagName.setId(tagNameDto.getId());
        tagName.setPermanentName(tagNameDto.getPermanentName());
        tagName.setName(tagNameDto.getName());
        tagName.setDescription(tagNameDto.getDescription());
        tagName.setType(tagNameDto.getType());
        return tagName;
    }
}
