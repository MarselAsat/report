package com.nppgks.reports.dto;


import com.nppgks.reports.db.entity.ManualTagName;

public record TagNameForOpc(Integer id, String name, String permanentName) {
    public static TagNameForOpc fromManualTagName(ManualTagName tagName) {
        return new TagNameForOpc(tagName.getId(), tagName.getName(), tagName.getPermanentName());
    }

    public static ManualTagName toManualTagName(TagNameForOpc tagName) {
        return new ManualTagName(tagName.id, tagName.permanentName, tagName.name(), null, null, null);
    }
}
