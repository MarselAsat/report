package com.nppgks.reports.dto;

import com.nppgks.reports.entity.TagData;
import com.nppgks.reports.entity.TagName;
import lombok.Data;

@Data
public class TagDataDto {
    private double data;
    private TagName tagName;

    public static TagDataDto fromTagData(TagData tagData){
        TagDataDto tagDataDto = new TagDataDto();
        tagDataDto.setData(tagData.getData());
        tagDataDto.setTagName(tagData.getTagName());

        return tagDataDto;
    }
}
