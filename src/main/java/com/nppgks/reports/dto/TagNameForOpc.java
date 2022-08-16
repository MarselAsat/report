package com.nppgks.reports.dto;

import com.nppgks.reports.entity.ManualTagName;
import lombok.Value;

public record TagNameForOpc(String name, String permanentName) {
    public static TagNameForOpc fromTagData(ManualTagName tagName) {
        TagNameForOpc tagNameForOpc = new TagNameForOpc(tagName.getName(), tagName.getPermanentName());
        return tagNameForOpc;
    }
}
