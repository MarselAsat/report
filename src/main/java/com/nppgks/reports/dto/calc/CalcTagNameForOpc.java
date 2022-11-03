package com.nppgks.reports.dto.calc;


import com.nppgks.reports.db.calculations.entity.TagName;

public record CalcTagNameForOpc(Integer id, String name, String permanentName) {
    public static CalcTagNameForOpc fromTagName(TagName tagName) {
        return new CalcTagNameForOpc(tagName.getId(), tagName.getName(), tagName.getPermanentName());
    }

    public static TagName toTagName(CalcTagNameForOpc tagName) {
        return new TagName(tagName.id, tagName.permanentName, tagName.name(), null, null, null);
    }
}
