package com.nppgks.reports.dto;

import com.nppgks.reports.entity.TagData;
import com.nppgks.reports.entity.TagName;
import lombok.Data;

@Data
public class TagNameForOpc {
    private String name;

    public static TagNameForOpc fromTagData(TagName tagName){
        TagNameForOpc tagNameForOpc = new TagNameForOpc();
        tagNameForOpc.setName(tagName.getName());
        return tagNameForOpc;
    }
}
