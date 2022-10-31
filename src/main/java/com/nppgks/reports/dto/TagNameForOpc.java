package com.nppgks.reports.dto;


import com.nppgks.reports.db.poverka.entity.TagName;

public record TagNameForOpc(Integer id, String name, String permanentName) {
    public static TagNameForOpc fromManualTagName(TagName tagName) {
        return new TagNameForOpc(tagName.getId(), tagName.getName(), tagName.getPermanentName());
    }

    public static TagName toManualTagName(TagNameForOpc tagName) {
        return new TagName(tagName.id, tagName.permanentName, tagName.name(), null, null, null);
    }
}
