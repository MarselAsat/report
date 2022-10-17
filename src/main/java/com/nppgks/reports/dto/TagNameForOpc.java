package com.nppgks.reports.dto;


import com.nppgks.reports.db.entity.ManualTagName;

public record TagNameForOpc(String name, String permanentName) {
    public static TagNameForOpc fromTagData(ManualTagName tagName) {
        return new TagNameForOpc(tagName.getName(), tagName.getPermanentName());
    }
}
