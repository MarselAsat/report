package com.nppgks.reports.dto;


import com.nppgks.reports.db.poverka.entity.TagName;

public record PoverkaTagNameForOpc(Integer id, String name, String permanentName) {
    public static PoverkaTagNameForOpc fromTagName(TagName tagName) {
        return new PoverkaTagNameForOpc(tagName.getId(), tagName.getName(), tagName.getPermanentName());
    }

    public static TagName toTagName(PoverkaTagNameForOpc tagName) {
        return new TagName(tagName.id, tagName.permanentName, tagName.name(), null, null, null);
    }
}
