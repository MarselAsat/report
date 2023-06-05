package com.nppgks.reportingsystem.dto.calc;

import com.nppgks.reportingsystem.db.calculations.entity.Tag;
import lombok.Data;

@Data
public class CalcTagDto {
    private Integer id;
    private String permanentName;
    private String address;
    private String description;
    private Boolean initial;
    private String calcMethod;

    public static CalcTagDto fromTag(Tag tag) {
        CalcTagDto calcTagDto = new CalcTagDto();
        calcTagDto.setId(tag.getId());
        calcTagDto.setPermanentName(tag.getPermanentName());
        calcTagDto.setAddress(tag.getAddress());
        calcTagDto.setDescription(tag.getDescription());
        calcTagDto.setCalcMethod(tag.getCalcMethod());
        calcTagDto.setInitial(tag.getInitial());
        return calcTagDto;
    }

    public static Tag toTag(CalcTagDto tagDto) {
        Tag tag = new Tag();
        tag.setId(tagDto.getId());
        tag.setPermanentName(tagDto.getPermanentName());
        tag.setAddress(tagDto.getAddress());
        tag.setDescription(tagDto.getDescription());
        tag.setCalcMethod(tagDto.getCalcMethod());
        tag.setInitial(tagDto.getInitial());
        return tag;
    }
}
