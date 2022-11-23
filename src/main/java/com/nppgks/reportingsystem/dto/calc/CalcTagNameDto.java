package com.nppgks.reportingsystem.dto.calc;

import com.nppgks.reportingsystem.db.calculations.entity.TagName;
import lombok.Data;

@Data
public class CalcTagNameDto {
    private Integer id;
    private String permanentName;
    private String name;
    private String description;
    private String type;

    public static CalcTagNameDto fromTagName(TagName tagName){
        CalcTagNameDto calcTagNameDto = new CalcTagNameDto();
        calcTagNameDto.setId(tagName.getId());
        calcTagNameDto.setPermanentName(tagName.getPermanentName());
        calcTagNameDto.setName(tagName.getName());
        calcTagNameDto.setDescription(tagName.getDescription());
        calcTagNameDto.setType(tagName.getType());
        return calcTagNameDto;
    }

    public static TagName toTagName(CalcTagNameDto tagNameDto){
        TagName tagName = new TagName();
        tagName.setId(tagNameDto.getId());
        tagName.setPermanentName(tagNameDto.getPermanentName());
        tagName.setName(tagNameDto.getName());
        tagName.setDescription(tagNameDto.getDescription());
        tagName.setType(tagNameDto.getType());
        return tagName;
    }
}
