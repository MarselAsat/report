package com.nppgks.reports.dto;

import com.nppgks.reports.entity.ManualTagName;
import com.nppgks.reports.entity.TagData;
import com.nppgks.reports.entity.TagName;
import lombok.Data;
import lombok.Value;

@Value
public class TagNameForOpc {
    private String name;
    private String permanentName;

    public static TagNameForOpc fromTagData(ManualTagName tagName){
        TagNameForOpc tagNameForOpc = new TagNameForOpc(tagName.getName(), tagName.getPermanentName());
        return tagNameForOpc;
    }
}
